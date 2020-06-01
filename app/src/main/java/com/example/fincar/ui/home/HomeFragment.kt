package com.example.fincar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.App
import com.example.fincar.R
import com.example.fincar.ui.BaseFragment
import com.example.fincar.ui.booksList.BooksListFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private var lastSearchedQuery = ""

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lastSearchedQuery = savedInstanceState.getString(EXTRA_TITLE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.getBooksListFragmentLiveData().observe(viewLifecycleOwner, Observer {
            if (lastSearchedQuery.isNotEmpty()) {
                it.isQuerySaved = true
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.booksListContainer,
                        it
                    )
                    .commit()
            }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getResourceId() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                val booksListFragment = BooksListFragment(searchText)
                booksListFragment.isQuerySaved = false
                homeViewModel.setFragment(booksListFragment)
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
