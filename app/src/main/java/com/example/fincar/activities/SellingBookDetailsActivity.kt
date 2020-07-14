package com.example.fincar.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.fincar.R
import com.example.fincar.adapters.EXTRA_SELLING_BOOK
import com.example.fincar.bean.Comment
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.databinding.ActivitySellingBookDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat

class SellingBookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellingBookDetailsBinding
    private lateinit var sellingBook: SellingBook


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetRootView: ConstraintLayout
    private lateinit var toggleButton: ToggleButton
    private lateinit var commentBottomSheetEditText: EditText
    private lateinit var commentAddButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_selling_book_details)

        initViews()

        sellingBook = intent.getSerializableExtra(EXTRA_SELLING_BOOK) as SellingBook
        binding.sellingBook = sellingBook

    }

    private fun initViews(){
        bottomSheetRootView = findViewById(R.id.bottomSheerRootView)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetRootView)
        toggleButton = findViewById(R.id.arrowToggle)
        commentBottomSheetEditText = findViewById(R.id.commentBottomSheetEditText)
        commentAddButton = findViewById(R.id.commentAddButton)

        commentAddButton.setOnClickListener {
            addComment()
        }
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                toggleButton.isChecked = newState == BottomSheetBehavior.STATE_EXPANDED
            }

        })

    }

    private fun addComment() {
//        val comment = Comment(sellingBook.id, )
    }

    override fun onDestroy() {
        super.onDestroy()
        val rating = binding.ratingBar.rating
        if(rating != 0F){
            sellingBook.rate(rating)
        }
    }
}
