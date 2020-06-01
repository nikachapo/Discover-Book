package com.example.fincar.book

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fincar.R
import kotlinx.android.synthetic.main.book_item.view.*

class BookAdapter(
    private val context: Context?,
    var books: ArrayList<BookModel>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.onBind()
    }


    inner class BookViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun onBind() {
            val book = books[adapterPosition]
            d("BookViewHolder",book.toString())
            Glide.with(view.context)
                .load(book.photoUrl)
                .into(view.itemBookImage)

            itemView.itemTitleText.text = book.title

            var authorsString = ""
            for (author in book.authorsList){
                authorsString += "$author  "
            }
            itemView.itemAuthorsText.text = authorsString

            itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.previewUrl))
                context?.startActivity(browserIntent)
            }
        }
    }


}