package com.example.fincar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.models.comment.Comment
import com.example.fincar.databinding.ItemCommentLayoutBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private val comments: ArrayList<Comment> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemCommentLayoutBinding
            .inflate(layoutInflater, parent, false)

        return CommentsViewHolder(binding)
    }

    fun addComment(comment: Comment){
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CommentsViewHolder(
        private val binding: ItemCommentLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            val comment = comments[adapterPosition]
            binding.comment = comment
        }
    }


}