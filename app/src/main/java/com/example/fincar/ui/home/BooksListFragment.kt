package com.example.fincar.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fincar.R
import com.example.fincar.book.BookAdapter
import com.example.fincar.book.BookModel
import com.example.fincar.ui.BaseFragment

class BooksListFragment(private val query: String, private val isQuerySaved: Boolean)
    : BaseFragment() {

    constructor():this("",false)

    private lateinit var viewModel: BookViewModel
    private var booksAdapter: BookAdapter? = null
    private lateinit var recyclerView: RecyclerView
    override fun getResourceId() = R.layout.fragment_books_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.booksListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        searchBookWithQuery(query, isQuerySaved)
    }

    private fun searchBookWithQuery(queryString: String, queryIsSaved: Boolean) {
        val factory =
            BookViewModelFactory(queryString)
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        if (!queryIsSaved) {
            viewModel.search(queryString)
        }

        viewModel.bookLiveData.observe(viewLifecycleOwner,
            Observer { books ->
                booksAdapter = BookAdapter(context, books as ArrayList<BookModel>)
                recyclerView.adapter = booksAdapter
            })
    }

}
