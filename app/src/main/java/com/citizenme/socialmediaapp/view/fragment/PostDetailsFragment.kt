package com.citizenme.socialmediaapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.adapter.CommentsAdapter
import com.citizenme.socialmediaapp.databinding.FragmentPostDetailsBinding
import com.citizenme.socialmediaapp.model.Comment


class PostDetailsFragment : Fragment() {

    private lateinit var binding : FragmentPostDetailsBinding
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostDetailsBinding.bind(view)

        val list = mutableListOf<Comment>()
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))
        list.add(Comment(1,1,"Serkan Sorman","serkan@gmail.com","Hello everyone!"))

        commentsAdapter = CommentsAdapter(list)
        binding.commentList.adapter = commentsAdapter
    }

}