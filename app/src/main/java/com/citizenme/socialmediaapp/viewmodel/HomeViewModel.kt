package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.remote.repository.PhotoRepository
import com.citizenme.socialmediaapp.remote.repository.PostRepository
import com.citizenme.socialmediaapp.core.ViewState
import com.citizenme.socialmediaapp.local.repository.LocalPhotoRepository
import com.citizenme.socialmediaapp.local.repository.LocalPostRepository
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel
import com.citizenme.socialmediaapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val photoRepository: PhotoRepository,
    private val localPostRepository: LocalPostRepository,
    private val localPhotoRepository: LocalPhotoRepository,
) : BaseViewModel() {

    val postAndPhotoModels = MutableLiveData<List<PostAndPhotoModel>>()

    fun getAllPosts() {
        if (needsRefreshFromApi()) {
            getAllPostsFromApi()
        } else {
            getAllPostsFromLocal()
        }
    }

    fun getAllPostsFromApi() {
        viewState.value = ViewState.Loading<PostAndPhotoModel>()
        viewModelScope.launch(Dispatchers.IO) {
            produceCombinationFromPostAndPhotos().collect {
                postAndPhotoModels.postValue(it)
                viewState.postValue(ViewState.Success(it))
            }
        }
    }

    private fun getAllPostsFromLocal() {

        viewModelScope.launch(Dispatchers.IO) {
            val postModelList = localPostRepository.getPosts().map { it.toPostModel() }
            val photoModelList = localPhotoRepository.getPhotos().map { it.toPhotoModel() }

            withContext(Dispatchers.Main) {
                postAndPhotoModels.value = mergePostAndPhotos(postModelList, photoModelList)
                viewState.value = ViewState.Success(postAndPhotoModels.value)
            }
        }
    }

    private fun storePostAndPhotosInLocal(postList: List<PostModel>, photoList: List<PhotoModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            val postEntityList = postList.map { it.toPostEntity() }
            val photoEntityList = photoList.map { it.toPhotoEntity() }

            localPostRepository.insertPosts(*postEntityList.toTypedArray())
            localPhotoRepository.insertPhotos(*photoEntityList.toTypedArray())
        }

        customPreferences.saveLastUpdateTime(System.nanoTime())
    }


    private fun produceCombinationFromPostAndPhotos() =
        flow {
            emit(getPostsAndPhotos())
        }.catch { exception ->
            viewState.postValue(ViewState.Error<PostAndPhotoModel>(exception.message))
        }.flowOn(Dispatchers.IO)

    private suspend fun getPostsAndPhotos() =

        coroutineScope {
            val deferredPosts = async { postRepository.getPosts() }
            val deferredPhotos = async { photoRepository.getPhotos() }

            val photoResponse = try {
                deferredPhotos.await()
            } catch (e: Exception) {
                viewState.postValue(ViewState.Error<PostAndPhotoModel>(e.message))
            }

            val postResponse = try {
                deferredPosts.await()
            } catch (e: Exception) {
                viewState.postValue(ViewState.Error<PostAndPhotoModel>(e.message))
            }

            val postList = postResponse as Response<*>
            val photoList = photoResponse as Response<*>

            launch(Dispatchers.IO) {
                storePostAndPhotosInLocal(
                    postList.body() as List<PostModel>,
                    photoList.body() as List<PhotoModel>
                )
            }

            mergePostAndPhotos(
                postList.body() as List<PostModel>,
                photoList.body() as List<PhotoModel>
            )
        }

    private fun mergePostAndPhotos(
        postList: List<PostModel>,
        photoList: List<PhotoModel>
    ): List<PostAndPhotoModel> {

        val postAndPhotoModelList = mutableListOf<PostAndPhotoModel>()

        postList.forEach { post ->
            photoList.forEach { photo ->
                if (post.postId == photo.photoId) {
                    postAndPhotoModelList.add(
                        PostAndPhotoModel(
                            postModel = post,
                            photoModel = photo
                        )
                    )
                }
            }
        }
        return postAndPhotoModelList
    }


}