package com.example.fincar.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.fincar.R
import com.example.fincar.book.BookAdapter

class BooksListFragment : Fragment() {
    private lateinit var viewModel: BookViewModel
    private var booksAdapter: BookAdapter? = null
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_list, container, false)
    }

}
