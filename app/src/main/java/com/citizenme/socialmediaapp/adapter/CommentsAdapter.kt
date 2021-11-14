package com.citizenme.socialmediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.citizenme.socialmediaapp.R
import com.citizenme.socialmediaapp.databinding.ItemCommentListBinding
import com.citizenme.socialmediaapp.model.CommentModel

class CommentsAdapter(private val commentList : MutableList<CommentModel>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(val binding: ItemCommentListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = DataBindingUtil.inflate<ItemCommentListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_comment_list, parent, false
        )
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.commentModel = commentList[position]
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun updateCommentList(commentList: MutableList<CommentModel>){
        this.commentList.clear()
        this.commentList.addAll(commentList)
        notifyDataSetChanged()
    }
}