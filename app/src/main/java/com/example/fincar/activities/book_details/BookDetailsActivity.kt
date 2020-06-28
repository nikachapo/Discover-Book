package com.example.fincar.activities.book_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.R
import com.example.fincar.book_db.BookModel
import com.example.fincar.databinding.ActivityBookDetailsBinding

const val EXTRA_BOOK = "extra-book"

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var book: BookModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        book = intent.getParcelableExtra(EXTRA_BOOK) as BookModel

        bookDetailsViewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)

        setUpBinding()

        setUpActionBar()

        checkFavourite()

        setListeners()

    }

    private fun setUpBinding() {
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_book_details
            )
        binding.book = book
    }

    private fun checkFavourite() {
        bookDetailsViewModel.getBookCountLiveData(book.id)?.observe(this, Observer {
            if (it > 0) binding.starButton.isChecked = true
        })
    }

    private fun setListeners() {
        binding.starButton.setOnClickListener {

            if (binding.starButton.isChecked) {
                binding.starButton.isChecked = false
                bookDetailsViewModel.delete(book)
            } else {
                binding.starButton.isChecked = true
                binding.starButton.playAnimation()
                bookDetailsViewModel.insert(book)
            }

        }

        binding.detailsBookImage.setOnClickListener {
            //open e-book link in browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.previewUrl))
            startActivity(browserIntent)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.detailsToolbar)
        binding.detailsCollapsingLayout.title = book.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
