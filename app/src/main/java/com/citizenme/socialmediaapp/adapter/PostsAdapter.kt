package com.citizenme.socialmediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.databinding.ItemPostListBinding
import com.citizenme.socialmediaapp.listener.PostClickListener
import com.citizenme.socialmediaapp.model.Post

class PostsAdapter(private val noteList: MutableList<Post>,
                    private val listener : PostClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = DataBindingUtil.inflate<ItemPostListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_post_list,
            parent,
            false
        )

        return PostViewHolder(view)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.postModel = noteList[position]
        holder.binding.postListener = listener
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}