package com.example.fincar.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.activities.book_details.BookDetailsActivity
import com.example.fincar.activities.book_details.EXTRA_BOOK
import com.example.fincar.models.book.GoogleBook
import com.example.fincar.databinding.ItemGoogleBookBinding

class BookAdapter(
    private val context: Context?,
    var googleBooks: ArrayList<GoogleBook>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val bookItemBinding = ItemGoogleBookBinding
            .inflate(layoutInflater, parent, false)

        return BookViewHolder(bookItemBinding)
    }

    override fun getItemCount(): Int = googleBooks.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.onBind()
    }

    inner class BookViewHolder(
        private val bookItemBinding: ItemGoogleBookBinding
    ) : RecyclerView.ViewHolder(bookItemBinding.root) {

        fun onBind() {

            val book = googleBooks[adapterPosition]
            bookItemBinding.bookItem = book
            bookItemBinding.root.setOnClickListener {

                val intent = Intent(context, BookDetailsActivity::class.java)
                intent.putExtra(EXTRA_BOOK, book)

                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        bookItemBinding.bookImageVIew,
                        "imageTransition"
                    )
                val bundle = options.toBundle()
                context.startActivity(intent, bundle)

            }
        }
    }


}