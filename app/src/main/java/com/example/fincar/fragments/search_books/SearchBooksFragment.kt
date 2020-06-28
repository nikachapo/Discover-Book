package com.example.fincar.fragments.search_books

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.R
import com.example.fincar.book_db.BookModel
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.fragments.booksList.BookViewModel
import com.example.fincar.fragments.booksList.BookViewModelFactory
import com.example.fincar.fragments.booksList.BooksListFragment
import kotlinx.android.synthetic.main.fragment_search_books.view.*
import kotlinx.android.synthetic.main.fragment_search_books.view.mainTitle

class SearchBooksFragment() : BaseFragment() {

    private lateinit var viewModel: BookViewModel

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var progressBar:ProgressBar
    private lateinit var mainTitle:TextView
    private lateinit var mainIcon:ImageView

    private var lastSearchedQuery = ""

    override fun getResourceId() = R.layout.fragment_search_books

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) lastSearchedQuery = savedInstanceState.getString(EXTRA_TITLE, "")
        initViewModel()
        initViews(view)
    }

    private fun initViewModel() {
            val factory = BookViewModelFactory(lastSearchedQuery)
            viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

            viewModel.allBooksLiveData.observe(viewLifecycleOwner,
                Observer { books ->
                    childFragmentManager.beginTransaction()
                        .replace(
                            R.id.booksListContainer,
                            BooksListFragment(books as ArrayList<BookModel>)
                        )
                        .commit()
                    progressBar.visibility = View.GONE
                    if(mainTitle.visibility == View.VISIBLE){
                        mainIcon.visibility = View.GONE
                        mainTitle.visibility = View.GONE
                    }
                })

    }

    private fun initViews(view: View) {
        searchEditText = view.searchEditText
        searchButton = view.searchButton
        progressBar = view.progressBar
        mainTitle = view.mainTitle
        mainIcon = view.mainIcon

        searchButton.setOnClickListener {
            if(searchEditText.visibility == View.VISIBLE){
                searchEditText.animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)
                searchEditText.visibility = View.INVISIBLE
                val searchText = searchEditText.text.toString().trim()
                if (searchText.isEmpty()) {
                    Toast.makeText(context, "Enter search text", Toast.LENGTH_LONG).show()
                } else {
                    progressBar.visibility = View.VISIBLE
                    lastSearchedQuery = searchText
                    searchBookWithQuery(searchText)
                }
            }else{
                searchEditText.animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
                searchEditText.visibility = View.VISIBLE
            }
        }

    }

    private fun searchBookWithQuery(queryString: String) {
        viewModel.search(queryString)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lastSearchedQuery.isNotEmpty()) outState.putString(EXTRA_TITLE, lastSearchedQuery)
    }

    companion object {
        private const val EXTRA_TITLE = "extra-title"
    }
}
