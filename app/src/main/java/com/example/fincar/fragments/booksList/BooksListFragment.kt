package com.example.fincar.fragments.booksList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fincar.R
import com.example.fincar.adapters.BookAdapter
import com.example.fincar.book.BookModel
import com.example.fincar.fragments.BaseFragment

class BooksListFragment(private val booksList:ArrayList<BookModel>) : BaseFragment() {

    constructor() : this(arrayListOf())

    private lateinit var recyclerView: RecyclerView

    override fun getResourceId() = R.layout.fragment_books_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.booksListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = BookAdapter(
            parentFragment?.context,
            booksList
        )
    }



}
