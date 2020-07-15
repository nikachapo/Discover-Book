package com.example.fincar.fragments.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fincar.R
import com.example.fincar.fragments.booksList.BooksListFragment
import com.example.fincar.layout_manager.ILayoutManagerFactory
import com.example.fincar.layout_manager.LayoutManagerFactory
import com.example.fincar.models.book.GoogleBook

class ReadFragment : Fragment() {

    private lateinit var readViewModel: ReadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readViewModel = ViewModelProvider(this).get(ReadViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        readViewModel.getBooksWithPDF().observe(viewLifecycleOwner, Observer {
            childFragmentManager.beginTransaction()
                .replace(
                    R.id.booksWithPDFContainer,
                    BooksListFragment(
                        googleBooksList = it as ArrayList<GoogleBook>,
                        layoutManagerFactory = object : ILayoutManagerFactory {
                            override fun create(): RecyclerView.LayoutManager {
                                return GridLayoutManager(context, 2)
                            }

                        }
                    )
                )
                .commit()
        })
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

}
