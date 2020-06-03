package com.example.fincar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.fincar.book.BookModel
import com.example.fincar.book.BookRepository
import com.example.fincar.databinding.ActivityBookDetailsBinding


const val EXTRA_BOOK = "extra-book"
class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_book_details)
        val book = intent.getParcelableExtra(EXTRA_BOOK) as BookModel
        binding.book = book

        setSupportActionBar(binding.detailsToolbar)
        binding.detailsCollapsingLayout.title = book.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repository = BookRepository(application)

        repository.getBookCountLiveData(book.id)?.observe(this, Observer {
            if(it>0) binding.starButton.isChecked = true
        })

        binding.starButton.setOnClickListener{

            if(binding.starButton.isChecked){
                binding.starButton.isChecked = false
                repository.delete(book)
            }else{
                binding.starButton.isChecked = true
                binding.starButton.playAnimation()
                repository.insert(book)
            }

        }

        binding.detailsBookImage.setOnClickListener {
            //open e-book link in browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.previewUrl))
            startActivity(browserIntent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }


    override fun onBackPressed() {
        finish()
    }
}
