package com.citizenme.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.data.local.repository.LocalPhotoRepository
import com.citizenme.socialmediaapp.data.local.repository.LocalPostRepository
import com.citizenme.socialmediaapp.data.remote.repository.PhotoRepository
import com.citizenme.socialmediaapp.data.remote.repository.PostRepository
import com.citizenme.socialmediaapp.mapper.toPhotoEntity
import com.citizenme.socialmediaapp.mapper.toPhotoModel
import com.citizenme.socialmediaapp.mapper.toPostEntity
import com.citizenme.socialmediaapp.mapper.toPostModel
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel
import com.citizenme.socialmediaapp.utils.CustomSharedPreferences.Companion.POST_LIST_UPDATE_TIME
import com.citizenme.socialmediaapp.utils.needsRefreshFromApi
import com.citizenme.socialmediaapp.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val photoRepository: PhotoRepository,
    private val localPostRepository: LocalPostRepository,
    private val localPhotoRepository: LocalPhotoRepository,
    application: Application,
) : BaseViewModel(application) {

    val postAndPhotoModels = MutableLiveData<List<PostAndPhotoModel>>()

    fun getAllPosts() {
        if (needsRefreshFromApi(customPrefs.getLastUpdateTime(POST_LIST_UPDATE_TIME))) {
            getAllPostsFromApi()
        } else {
            getAllPostsFromLocal()
        }
    }

    fun getAllPostsFromApi() {
        viewState.value = ViewState.Loading<PostAndPhotoModel>()
        viewModelScope.launch {
            val postAndPhotos = getPostsAndPhotos()
            withContext(Dispatchers.Main) {
                postAndPhotoModels.value = postAndPhotos
                viewState.value = ViewState.Success(postAndPhotos)
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

            launch { localPostRepository.insertPosts(*postEntityList.toTypedArray()) }
            launch { localPhotoRepository.insertPhotos(*photoEntityList.toTypedArray()) }
        }

        customPrefs.saveLastUpdateTime(System.nanoTime(), POST_LIST_UPDATE_TIME)
    }

    suspend fun getPostsAndPhotos() =

        coroutineScope {
            val deferredPosts = async(Dispatchers.IO) { postRepository.getPosts() }
            val deferredPhotos = async(Dispatchers.IO) { photoRepository.getPhotos() }

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

            @Suppress("UNCHECKED_CAST")
            storePostAndPhotosInLocal(
                postList.body() as List<PostModel>,
                photoList.body() as List<PhotoModel>
            )

            @Suppress("UNCHECKED_CAST")
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