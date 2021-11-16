package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.remote.repository.PostRepository
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseViewModel() {

    val commentList = MutableLiveData<MutableList<CommentModel>>()
    val postDetails = MutableLiveData<PostAndPhotoModel>()

    fun getComments(postId: Int) {
        viewState.value = ViewState.Loading<CommentModel>()
        viewModelScope.launch(Dispatchers.IO) {
            val commentResponse = postRepository.getComments(postId)
            withContext(Dispatchers.Main) {
                if (commentResponse.isSuccessful) {
                    commentList.value = commentResponse.body()
                    viewState.value = ViewState.Success(commentResponse.body())
                } else {
                    viewState.value =
                        ViewState.Error<CommentModel>(commentResponse.errorBody().toString())
                }
            }
        }
    }
}