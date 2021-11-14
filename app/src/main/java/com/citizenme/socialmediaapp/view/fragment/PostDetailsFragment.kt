package com.citizenme.socialmediaapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.adapter.CommentsAdapter
import com.citizenme.socialmediaapp.databinding.FragmentPostDetailsBinding
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.viewmodel.PostDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private lateinit var binding : FragmentPostDetailsBinding
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

        arguments?.let {
            val postDetails = PostDetailsFragmentArgs.fromBundle(it).postDetails
            binding.postAndPhotoModel = postDetails
            postDetails.postModel?.postId?.let { id -> postDetailsViewModel.getComments(id) }
        }

        observeComments()


    }

    private fun observeComments(){
        postDetailsViewModel.commentList.observe(viewLifecycleOwner,{
            commentsAdapter.updateCommentList(it)
        })
    }

}