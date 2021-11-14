package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citizenme.socialmediaapp.api.repository.PostRepository
import com.citizenme.socialmediaapp.model.CommentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(private val postRepository: PostRepository) :
    ViewModel() {

    val commentList = MutableLiveData<MutableList<CommentModel>>()

    fun getComments(postId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val commentResponse = postRepository.getComments(postId)
            withContext(Dispatchers.Main){
                if(commentResponse.isSuccessful){
                    commentList.postValue(commentResponse.body())
                }else {
                    //TODO
                }
            }
        }
    }
}