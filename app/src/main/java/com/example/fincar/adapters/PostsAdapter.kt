package com.example.fincar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.databinding.ItemPostBinding
import com.example.fincar.models.Post


class PostsAdapter(private val onPostItemLongClickListener: OnPostItemLongClickListener)
    : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    interface OnPostItemLongClickListener{
        fun onClick(position: Int)
    }

    private val posts: ArrayList<Post> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemPostBinding
            .inflate(layoutInflater, parent, false)

        return PostsViewHolder(binding)
    }

    fun addPost(post: Post) {
        posts.add(0,post)
        notifyItemInserted(0)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.onBind()
    }

    inner class PostsViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            binding.root.setOnLongClickListener {
                onPostItemLongClickListener.onClick(adapterPosition)
                true
            }
            val post = posts[adapterPosition]
            binding.post = post
        }
    }
}

