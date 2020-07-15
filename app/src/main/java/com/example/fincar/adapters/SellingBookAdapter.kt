package com.example.fincar.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.activities.registration.EXTRA_ACCOUNT
import com.example.fincar.activities.selling_book_details.SellingBookDetailsActivity
import com.example.fincar.models.Account
import com.example.fincar.models.book.SellingBook
import com.example.fincar.databinding.ItemSellingBookBinding

const val EXTRA_SELLING_BOOK = "extra_selling_book"

class SellingBookAdapter(
    private val context: Context?,
    var sellingBooks: ArrayList<SellingBook>,
    private val account: Account?
) : RecyclerView.Adapter<SellingBookAdapter.SellingBookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellingBookViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemSellingBookBinding = ItemSellingBookBinding
            .inflate(layoutInflater, parent, false)

        return SellingBookViewHolder(itemSellingBookBinding)
    }

    override fun getItemCount(): Int = sellingBooks.size

    override fun onBindViewHolder(holder: SellingBookViewHolder, position: Int) {
        holder.onBind()
    }

    inner class SellingBookViewHolder(
        private val bookItemBinding: ItemSellingBookBinding
    ) : RecyclerView.ViewHolder(bookItemBinding.root) {

        fun onBind() {

            val book = sellingBooks[adapterPosition]
            bookItemBinding.sellingBookItem = book
            bookItemBinding.root.setOnClickListener {

                val intent = Intent(context, SellingBookDetailsActivity::class.java)
                intent.putExtra(EXTRA_SELLING_BOOK, book)
                intent.putExtra(EXTRA_ACCOUNT, account)
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        bookItemBinding.sellingBookImageView,
                        "imageTransition"
                    )
                val bundle = options.toBundle()
                context.startActivity(intent, bundle)

            }
        }
    }


}