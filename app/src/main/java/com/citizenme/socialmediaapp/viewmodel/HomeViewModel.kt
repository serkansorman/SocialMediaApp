package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.api.repository.PhotoRepository
import com.citizenme.socialmediaapp.api.repository.PostRepository
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val postAndPhotoModels = MutableLiveData<List<PostAndPhotoModel>>()

    fun getAllPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            produceCombinationFromPostAndPhotos().collect {
                postAndPhotoModels.postValue(it)
            }
        }
    }

    private fun produceCombinationFromPostAndPhotos() = flow {
        emit(getPostsAndPhotos())
    }

    private suspend fun getPostsAndPhotos() =

        coroutineScope {
            val deferredPosts = async { postRepository.getPosts() }
            val deferredPhotos = async { photoRepository.getPhotos() }

            val photoResponse = try {
                deferredPhotos.await()
            } catch (e: Exception) {
                mutableListOf<PostAndPhotoModel>()
            }

            val postResponse = try {
                deferredPosts.await()
            } catch (e: Exception) {
                mutableListOf<PostAndPhotoModel>()
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