package com.citizenme.socialmediaapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.adapter.CommentsAdapter
import com.citizenme.socialmediaapp.databinding.FragmentPostDetailsBinding
import com.citizenme.socialmediaapp.view.state.ViewState
import com.citizenme.socialmediaapp.viewmodel.PostDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var commentsAdapter: CommentsAdapter
    private val postDetailsViewModel by viewModels<PostDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostDetailsBinding.bind(view)
        commentsAdapter = CommentsAdapter(mutableListOf())
        binding.commentList.adapter = commentsAdapter

        getPosDetails()
        observePostDetails()
        observeComments()
        observeViewState()
        onErrorRefreshClick()
    }

    private fun getPosDetails(){
        arguments?.let {
            postDetailsViewModel.postDetails.value = PostDetailsFragmentArgs
                .fromBundle(it)
                .postDetails
        }
    }

    private fun observeComments() {
        postDetailsViewModel.commentList.observe(viewLifecycleOwner, {
            commentsAdapter.updateCommentList(it)
        })
    }

    private fun observePostDetails() {
        postDetailsViewModel.postDetails.observe(viewLifecycleOwner, {
            binding.postAndPhotoModel = it
            it.postModel?.postId?.let { id -> postDetailsViewModel.getComments(id) }
        })
    }

     override fun observeViewState() {
        postDetailsViewModel.viewState.observe(viewLifecycleOwner, {
            binding.commentList.isVisible = it is ViewState.Success
            binding.loading.isVisible = it is ViewState.Loading
            binding.error.layout.isVisible = it is ViewState.Error
        })
    }

     override fun onErrorRefreshClick() {
        binding.error.refreshButton.setOnClickListener {
            postDetailsViewModel.postDetails.value?.postModel?.let { postModel ->
                postDetailsViewModel.getCommentsFromApi(
                    postModel.postId
                )
            }
        }
    }

}