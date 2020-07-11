package com.example.fincar.fragments.booksList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fincar.R
import com.example.fincar.adapters.BookAdapter
import com.example.fincar.adapters.SellingBookAdapter
import com.example.fincar.bean.book.Book
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.fragments.BaseFragment
import java.util.*
import kotlin.collections.ArrayList

class BooksListFragment(
    private val sellingBooksList: ArrayList<SellingBook>? = null,
    private val googleBooksList: ArrayList<GoogleBook>? = null,
    private val layoutManager: RecyclerView.LayoutManager?
) : BaseFragment() {

    constructor() : this(layoutManager = null)

    private lateinit var recyclerView: RecyclerView

    override fun getResourceId() = R.layout.fragment_books_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.booksListRecyclerView)
        recyclerView.layoutManager = layoutManager

        @Suppress("UNCHECKED_CAST")
        if (googleBooksList != null)
            recyclerView.adapter = BookAdapter(
                parentFragment?.context,
                googleBooksList
            )
        else if (sellingBooksList != null) {
            recyclerView.adapter = SellingBookAdapter(
                parentFragment?.context,
                sellingBooksList
            )

        }
    }

}
