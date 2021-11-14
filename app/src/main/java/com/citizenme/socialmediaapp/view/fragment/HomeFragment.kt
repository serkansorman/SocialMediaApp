package com.citizenme.socialmediaapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.adapter.PostsAdapter
import com.citizenme.socialmediaapp.databinding.FragmentHomeBinding
import com.citizenme.socialmediaapp.listener.PostClickListener
import com.citizenme.socialmediaapp.model.Post


class HomeFragment : Fragment(), PostClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        val list = mutableListOf<Post>()
        list.add(Post(1,1,"My Post Title","My Post Body"))
        list.add(Post(1,1,"My Post Title","My Post Body"))
        list.add(Post(1,1,"My Post Title","My Post Body"))
        list.add(Post(1,1,"My Post Title","My Post Body"))
        list.add(Post(1,1,"My Post Title","My Post Body"))

        postsAdapter = PostsAdapter(list,this)
        binding.postList.adapter = postsAdapter

    }

    override fun onPostClick(post: Post) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(post.postId))
    }
}