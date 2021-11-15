package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.api.repository.PhotoRepository
import com.citizenme.socialmediaapp.api.repository.PostRepository
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel
import com.citizenme.socialmediaapp.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val photoRepository: PhotoRepository
) : BaseViewModel() {

    val postAndPhotoModels = MutableLiveData<List<PostAndPhotoModel>>()

    fun getAllPosts() {
        viewState.postValue(ViewState.Loading<PostAndPhotoModel>())
        viewModelScope.launch(Dispatchers.IO) {
            produceCombinationFromPostAndPhotos().collect {
                postAndPhotoModels.postValue(it)
                viewState.postValue(ViewState.Success(it))
            }
        }
    }

    private fun produceCombinationFromPostAndPhotos() =
        flow {
            emit(getPostsAndPhotos())
        }.catch { exception ->
            viewState.postValue(ViewState.Error<PostAndPhotoModel>(exception.message))
        }

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