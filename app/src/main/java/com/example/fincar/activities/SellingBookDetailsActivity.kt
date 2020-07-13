package com.example.fincar.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fincar.R
import com.example.fincar.adapters.EXTRA_SELLING_BOOK
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.databinding.ActivitySellingBookDetailsBinding
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

class SellingBookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellingBookDetailsBinding
    private lateinit var sellingBook: SellingBook
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_selling_book_details)

        sellingBook = intent.getSerializableExtra(EXTRA_SELLING_BOOK) as SellingBook
        binding.sellingBook = sellingBook

    }

    override fun onDestroy() {
        super.onDestroy()
        val rating =
        ((sellingBook.rating * sellingBook.ratedCount) +
                binding.ratingBar.rating) / (sellingBook.ratedCount + 1)

        val bookRef = FirebaseDatabase.getInstance().reference
            .child("selling_books")
            .child(sellingBook.id)

        bookRef.child("ratedCount")
            .setValue(sellingBook.ratedCount + 1)
        bookRef.child("rating")
            .setValue(rating)
    }
}
