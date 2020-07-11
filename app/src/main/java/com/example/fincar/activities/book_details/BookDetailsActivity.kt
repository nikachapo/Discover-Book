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
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.databinding.ActivityBookDetailsBinding

const val EXTRA_BOOK = "extra-book"

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var googleBook: GoogleBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleBook = intent.getSerializableExtra(EXTRA_BOOK) as GoogleBook

        bookDetailsViewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)

        setUpBinding()

        setUpActionBar()

        checkFavourite()

        setClickListeners()

    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_details)
        binding.book = googleBook
    }

    private fun checkFavourite() {
        bookDetailsViewModel.getBookCountLiveData(googleBook.id)?.observe(this, Observer {
            if (it > 0) binding.starButton.isChecked = true
        })
    }

    private fun setClickListeners() {
        binding.starButton.setOnClickListener {

            if (binding.starButton.isChecked) {
                binding.starButton.isChecked = false
                bookDetailsViewModel.delete(googleBook)
            } else {
                binding.starButton.isChecked = true
                binding.starButton.playAnimation()
                bookDetailsViewModel.insert(googleBook)
            }

        }

        binding.detailsBookImage.setOnClickListener {
            //open e-book link in browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googleBook.previewUrl))
            startActivity(browserIntent)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.detailsToolbar)
        binding.detailsCollapsingLayout.title = googleBook.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
