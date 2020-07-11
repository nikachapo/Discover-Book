package com.example.fincar.fragments.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.fragments.booksList.BooksListFragment
import com.example.fincar.network.firebase.selling_books.FetchSellingBooksCallbacks
import com.example.fincar.network.firebase.selling_books.SellingBooksObserver

class StoreFragment : BaseFragment() {

    private lateinit var viewModel: StoreFragmentViewModel

    private lateinit var sellingBooksObserver: SellingBooksObserver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(StoreFragmentViewModel::class.java)
        sellingBooksObserver =
            SellingBooksObserver(
                fetchBooksEventListener
            )
        lifecycle.addObserver(sellingBooksObserver)

        viewModel.getSellingBooksLiveData().observe(viewLifecycleOwner, Observer {
            childFragmentManager.beginTransaction()
                .replace(R.id.sellingBookContainer,
                    BooksListFragment(
                        sellingBooksList = it as ArrayList<SellingBook>,
                        layoutManager = LinearLayoutManager(context)
                    )
                )
                .commitAllowingStateLoss()
        })
    }

    private val fetchBooksEventListener = object :
        FetchSellingBooksCallbacks {
        override fun onError(message: String) {
            Tools.showToast(context!!, message)
        }

        override fun getSellingBooks(books: MutableList<SellingBook>?) {
            viewModel.setBooksData(books!!)
        }
    }

    override fun getResourceId() = R.layout.fragment_books_store
}
