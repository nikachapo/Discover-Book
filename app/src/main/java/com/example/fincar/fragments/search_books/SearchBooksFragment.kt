package com.example.fincar.fragments.search_books

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.fragments.BaseFragment
import com.example.fincar.fragments.booksList.BookViewModel
import com.example.fincar.fragments.booksList.BookViewModelFactory
import com.example.fincar.fragments.booksList.BooksListFragment
import kotlinx.android.synthetic.main.fragment_search_books.view.*

class SearchBooksFragment : BaseFragment() {

    private lateinit var viewModel: BookViewModel

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var mainTitle: TextView
    private lateinit var mainIcon: ImageView
    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView
    private var lastSearchedQuery = ""

    override fun getResourceId() = R.layout.fragment_search_books

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            lastSearchedQuery = savedInstanceState.getString(EXTRA_TITLE, "")

        initViewModel()

        loadingRootLayout = activity!!.findViewById(R.id.loadingRootLayout)
        loadingAnimationView = activity!!.findViewById(R.id.loadingAnimationView)

    }



    private fun initViewModel() {
        val factory = BookViewModelFactory(lastSearchedQuery)
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        viewModel.allBooksLiveData.observe(viewLifecycleOwner,
            Observer { books ->
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.booksListContainer,
                        BooksListFragment(books as ArrayList<GoogleBook>)
                    )
                    .commit()
                loadingRootLayout.visibility = View.GONE
                loadingAnimationView.cancelAnimation()
                if (mainTitle.visibility == View.VISIBLE) {
                    mainIcon.visibility = View.GONE
                    mainTitle.visibility = View.GONE
                }
            })

    }

    private fun initViews(view: View) {
        searchEditText = view.searchEditText
        searchButton = view.searchButton
        mainTitle = view.mainTitle
        mainIcon = view.mainIcon

        searchButton.setOnClickListener {
            if (searchEditText.visibility == View.VISIBLE) {

                searchEditText.animation =
                    AnimationUtils.loadAnimation(context, R.anim.slide_out_right)
                searchEditText.visibility = View.INVISIBLE

                val searchText = searchEditText.text.toString().trim()
                if (searchText.isEmpty()) {
                    Toast.makeText(context, "Enter search text", Toast.LENGTH_LONG).show()
                } else {
                    loadingRootLayout.visibility = View.VISIBLE
                    loadingAnimationView.playAnimation()
                    lastSearchedQuery = searchText
                    searchBookWithQuery(searchText)
                }
            } else {
                searchEditText.animation =
                    AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
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
