package com.example.fincar.fragments.starred

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.R
import com.example.fincar.book.BookModel
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.fragments.booksList.BooksListFragment

class StarredBooksFragment() : BaseFragment() {

    private lateinit var viewModel: StarredBooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(StarredBooksViewModel::class.java)
        viewModel.getStarredBooks()?.observe(viewLifecycleOwner,
            Observer { books ->
                d("starredBooks", " booksLivedata.Observe()")
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.starredBooksListContainer,
                        BooksListFragment(books as ArrayList<BookModel>)
                    )
                    .commit()
            })
    }

    override fun getResourceId() = R.layout.fragment_starred_books
}
