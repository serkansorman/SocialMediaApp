package com.citizenme.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.data.local.repository.LocalCommentRepository
import com.citizenme.socialmediaapp.data.remote.repository.PostRepository
import com.citizenme.socialmediaapp.mapper.toCommentEntity
import com.citizenme.socialmediaapp.mapper.toCommentModel
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.utils.CustomSharedPreferences.Companion.COMMENT_LIST_UPDATE_TIME
import com.citizenme.socialmediaapp.utils.needsRefreshFromApi
import com.citizenme.socialmediaapp.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val localCommentRepository: LocalCommentRepository,
    application: Application
) : BaseViewModel(application) {

    val commentList = MutableLiveData<List<CommentModel>>()
    val postDetails = MutableLiveData<PostAndPhotoModel>()

    fun getComments(postId: Int) {
         if (needsRefreshFromApi(customPrefs.getLastUpdateTime(
                 "${COMMENT_LIST_UPDATE_TIME}_${postId}"))) {
             getCommentsFromApi(postId)
         } else {
             getCommentsFromLocal(postId)
         }
    }

    fun getCommentsFromApi(postId: Int) {
        viewState.value = ViewState.Loading<CommentModel>()
        viewModelScope.launch(Dispatchers.IO) {
            val commentResponse = postRepository.getComments(postId)
            withContext(Dispatchers.Main) {
                if (commentResponse.isSuccessful) {
                    commentList.value = commentResponse.body()
                    viewState.value = ViewState.Success(commentResponse.body())
                    commentResponse.body()?.let { storeCommentsInLocal(it,postId) }
                } else {
                    viewState.value =
                        ViewState.Error<CommentModel>(commentResponse.errorBody().toString())
                }
            }
        }
    }

    private fun getCommentsFromLocal(postId: Int) {
        viewState.value = ViewState.Loading<CommentModel>()
        viewModelScope.launch(Dispatchers.IO) {
            val commentModelList =
                localCommentRepository.getComments(postId).map { it.toCommentModel() }

            withContext(Dispatchers.Main) {
                commentList.value = commentModelList
                viewState.value = ViewState.Success(commentModelList)
            }
        }
    }

    private fun storeCommentsInLocal(commentList: List<CommentModel>,postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val commentEntityList = commentList.map { it.toCommentEntity() }
            localCommentRepository.insertComments(*commentEntityList.toTypedArray())
        }
        customPrefs.saveLastUpdateTime(System.nanoTime(), "${COMMENT_LIST_UPDATE_TIME}_${postId}")
    }
}