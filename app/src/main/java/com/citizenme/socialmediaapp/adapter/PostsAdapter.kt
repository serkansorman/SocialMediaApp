package com.citizenme.socialmediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.databinding.ItemPostListBinding
import com.citizenme.socialmediaapp.listener.PostClickListener
import com.citizenme.socialmediaapp.model.PostAndPhotoModel

class PostsAdapter(
    private val photoAndPostList: MutableList<PostAndPhotoModel>,
    private val listener: PostClickListener
) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostListBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        holder.binding.postAndPhotoModel = photoAndPostList[position]
        holder.binding.postListener = listener
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.list_anim)
    }

    override fun getItemCount(): Int {
        return photoAndPostList.size
    }

    fun updatePostList(photoAndPostList: List<PostAndPhotoModel>) {
        this.photoAndPostList.clear()
        this.photoAndPostList.addAll(photoAndPostList)
        notifyDataSetChanged()
    }
}