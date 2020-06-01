package com.example.fincar.ui.home

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.App
import com.example.fincar.R
import com.example.fincar.book.BookAdapter
import com.example.fincar.book.BookModel
import com.example.fincar.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private var lastSearchedQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lastSearchedQuery = savedInstanceState.getString(EXTRA_TITLE, "")
        }
    }

    override fun getResourceId() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (lastSearchedQuery.isNotEmpty()){
            childFragmentManager.beginTransaction()
                .replace(R.id.booksListContainer, BooksListFragment(lastSearchedQuery,true))
                .commit()
        }
        initViews(view)
    }

    private fun initViews(view: View) {
        searchEditText = view.searchEditText
        searchButton = view.searchButton

        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString().trim()
            if (searchText.isEmpty()) {
                Toast.makeText(App.getInstance(), "Enter search text", Toast.LENGTH_LONG).show()
            } else {
                lastSearchedQuery = searchText
                childFragmentManager.beginTransaction()
                    .replace(R.id.booksListContainer, BooksListFragment(searchText,false))
                    .commit()
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lastSearchedQuery.isNotEmpty()) {
            outState.putString(EXTRA_TITLE, lastSearchedQuery)
        }
    }

    companion object {
        private const val EXTRA_TITLE = "extra-title"
    }
}
