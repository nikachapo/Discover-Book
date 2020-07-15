package com.example.fincar.fragments.home

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.R
import com.example.fincar.adapters.CategoriesAdapter
import com.example.fincar.models.Category
import com.example.fincar.models.book.GoogleBook
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.fragments.booksList.BookViewModel
import com.example.fincar.fragments.booksList.BookViewModelFactory
import com.example.fincar.fragments.booksList.BooksListFragment
import com.example.fincar.layout_manager.ILayoutManagerFactory
import com.example.fincar.layout_manager.LayoutManagerFactory
import com.example.fincar.network.books_api.BooksApiRequest
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: BookViewModel

    override fun getResourceId() = R.layout.home_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categories = mutableListOf(
            Category("Drama", Color.RED, "https://image.flaticon.com/icons/png/512/1048/1048962.png"),
            Category("Romance", Color.GRAY, "https://pluspng.com/img-png/rose-hd-png-rose-png-hd-667.png"),
            Category("Mystery", Color.GREEN, "https://pngimage.net/wp-content/uploads/2018/06/mystery-png-5.png"),
            Category("Horror", Color.BLACK, "https://www.freeiconspng.com/uploads/horror-png-9.png"),
            Category("Thriller", Color.BLUE, "https://mtb-law.co.uk/wp-content/uploads/2016/11/Spy.png"),
            Category("Science", Color.YELLOW, "https://upload.wikimedia.org/wikipedia/commons/8/88/P_Science.png"),
            Category("Fiction", Color.CYAN, "https://pluspng.com/img-png/feather-pen-png-black-and-white-size-512.png"),
            Category("Art", Color.GREEN, "https://img.pngio.com/art-palette-web-design-icon-designer-art-png-400_400.png")
        )

        val factory = BookViewModelFactory(categories[0].name)
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        categoriesRecyclerView.adapter = CategoriesAdapter(categories,
            object :CategoriesAdapter.OnCategoryClickListener{
                override fun onClick(category: String) {
                    viewModel.search("${BooksApiRequest.CATEGORY}${category}")
                }
            })

        viewModel.allBooksLiveData.observe(viewLifecycleOwner, Observer {
            childFragmentManager.beginTransaction()
                .replace(
                    R.id.booksWithCategoriesContainer,
                    BooksListFragment(
                        googleBooksList = it as ArrayList<GoogleBook>,
                        layoutManagerFactory = object : ILayoutManagerFactory {
                            override fun create(): RecyclerView.LayoutManager {
                                return LayoutManagerFactory
                                    .create(context, LinearLayoutManager.HORIZONTAL, true)
                            }

                        }
                    )
                )
                .commit()
        })

    }

}
