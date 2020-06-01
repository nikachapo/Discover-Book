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
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: BookViewModel
    private var booksAdapter: BookAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private var lastSearchedQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null){
            lastSearchedQuery = savedInstanceState.getString(EXTRA_TITLE,"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(lastSearchedQuery.isNotEmpty()) searchBookWithQuery(lastSearchedQuery, true)
        initViews(view)
    }

    private fun initViews(view: View) {
        recyclerView = view.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        searchEditText = view.searchEditText

        searchButton = view.searchButton
        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString().trim()
            if (searchText.isEmpty()) {
                Toast.makeText(App.getInstance(), "Enter search text", Toast.LENGTH_LONG).show()
            } else searchBookWithQuery(searchText, false)
        }

    }


    private fun searchBookWithQuery(queryString: String, queryIsSaved: Boolean) {
        val factory =
            BookViewModelFactory(queryString)
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        if(!queryIsSaved) {
            viewModel.search(queryString)
        }

        lastSearchedQuery = queryString

        viewModel.bookLiveData.observe(viewLifecycleOwner,
            Observer { books ->
                d("Main",books.toString())
                booksAdapter = BookAdapter(context, books as ArrayList<BookModel>)
                recyclerView.adapter = booksAdapter
            })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(lastSearchedQuery.isNotEmpty()){
            outState.putString(EXTRA_TITLE, lastSearchedQuery)
        }
    }

    companion object{
        private const val EXTRA_TITLE = "extra-title"
    }
}
