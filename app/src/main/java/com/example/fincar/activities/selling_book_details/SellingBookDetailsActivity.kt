package com.example.fincar.activities.selling_book_details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.R
import com.example.fincar.activities.registration.EXTRA_ACCOUNT
import com.example.fincar.adapters.CommentsAdapter
import com.example.fincar.adapters.EXTRA_SELLING_BOOK
import com.example.fincar.bean.Account
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.databinding.ActivitySellingBookDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SellingBookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellingBookDetailsBinding
    private lateinit var sellingBook: SellingBook
    private var account: Account? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var viewModel:SellingBookDetailsViewModel

    private lateinit var commentsAdapter:CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_selling_book_details)

        initViews()

        sellingBook = intent.getSerializableExtra(EXTRA_SELLING_BOOK) as SellingBook
        sellingBook.increaseSeenCount()
        binding.sellingBook = sellingBook
        account = intent.getParcelableExtra(EXTRA_ACCOUNT) as Account?

        initViewModel()
    }

    private fun initViewModel() {
        val factory = SellingBookDetailsViewModelFactory(account!!, sellingBook)
        viewModel = ViewModelProvider(this, factory).get(SellingBookDetailsViewModel::class.java)

        viewModel.getCommentLiveData().observe(this, Observer {
            commentsAdapter.addComment(it)
            binding.bottomSheetLayout.commentsRecyclerView
                .scrollToPosition(commentsAdapter.itemCount - 1)
        })
    }

    private fun initViews(){
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.bottomSheerRootView)
        commentsAdapter = CommentsAdapter()
        binding.bottomSheetLayout.commentsRecyclerView.adapter = commentsAdapter
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.bottomSheetLayout.arrowToggle.isChecked =
                    newState == BottomSheetBehavior.STATE_EXPANDED
            }

        })

        setClickListeners()

    }

    private fun setClickListeners() {
        binding.bottomSheetLayout.arrowToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.bottomSheetLayout.commentAddButton.setOnClickListener {
            viewModel.addComment(binding.bottomSheetLayout.commentBottomSheetEditText.text.toString())
        }
        binding.buyButton.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val rating = binding.ratingBar.rating
        if(rating != 0F){
            sellingBook.rate(rating)
        }
    }
}
