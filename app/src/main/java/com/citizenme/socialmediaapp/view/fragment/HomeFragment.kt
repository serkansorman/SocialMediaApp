package com.citizenme.socialmediaapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.adapter.PostsAdapter
import com.citizenme.socialmediaapp.databinding.FragmentHomeBinding
import com.citizenme.socialmediaapp.listener.PostClickListener
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.utils.ViewState
import com.citizenme.socialmediaapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), PostClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
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
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        postsAdapter = PostsAdapter(mutableListOf(), this)
        binding.postList.adapter = postsAdapter

        homeViewModel.getAllPosts()
        observePosts()
        observeViewState()
        onErrorRefreshClick()


    }

    override fun onPostClick(postAndPhotoModel: PostAndPhotoModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(postAndPhotoModel)
        )
    }

    private fun observePosts() {
        homeViewModel.postAndPhotoModels.observe(viewLifecycleOwner, {
            postsAdapter.updatePostList(it)
        })
    }

    override fun observeViewState() {
        homeViewModel.viewState.observe(viewLifecycleOwner, {
            binding.homeLayout.isVisible = it is ViewState.Success
            binding.loading.isVisible = it is ViewState.Loading
            binding.error.layout.isVisible = it is ViewState.Error

        })
    }

    override fun onErrorRefreshClick() {
        binding.error.refreshButton.setOnClickListener {
            homeViewModel.getAllPosts()
        }
    }

    override fun onRefresh() {
        homeViewModel.getAllPosts()
        binding.swipeRefreshLayout.isRefreshing = false
    }

}