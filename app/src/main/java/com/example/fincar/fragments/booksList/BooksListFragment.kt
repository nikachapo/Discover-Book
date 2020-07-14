package com.example.fincar.fragments.booksList

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.R
import com.example.fincar.adapters.BookAdapter
import com.example.fincar.adapters.SellingBookAdapter
import com.example.fincar.bean.Account
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.layout_manager.ILayoutManagerFactory
import com.example.fincar.layout_manager.LayoutManagerFactory

class BooksListFragment(
    private val account: Account? = null,
    private val sellingBooksList: ArrayList<SellingBook>? = null,
    private val googleBooksList: ArrayList<GoogleBook>? = null,
    private var layoutManagerFactory: ILayoutManagerFactory?
) : BaseFragment() {

    constructor():this(layoutManagerFactory = null)

    private lateinit var recyclerView: RecyclerView

    override fun getResourceId() = R.layout.fragment_books_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = requireView().findViewById(R.id.booksListRecyclerView)

        if(layoutManagerFactory != null){
        recyclerView.layoutManager = layoutManagerFactory!!.create()
        }else{
            recyclerView.layoutManager = LayoutManagerFactory.create(context, false)
        }
        recyclerView.setHasFixedSize(true)

        @Suppress("UNCHECKED_CAST")
        if (googleBooksList != null)
            recyclerView.adapter = BookAdapter(parentFragment?.context, googleBooksList)
        else if (sellingBooksList != null) {
            recyclerView.adapter = SellingBookAdapter(parentFragment?.context, sellingBooksList, account)
        }
    }

}
