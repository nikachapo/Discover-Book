package com.example.fincar.activities.selling_book_details

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
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
import com.example.fincar.app.Tools
import com.example.fincar.models.Account
import com.example.fincar.models.book.SellingBook
import com.example.fincar.databinding.ActivitySellingBookDetailsBinding
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SellingBookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellingBookDetailsBinding
    private lateinit var sellingBook: SellingBook
    private var account: Account? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var viewModel: SellingBookDetailsViewModel

    private lateinit var successDialog: Dialog

    private lateinit var commentsAdapter: CommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_selling_book_details)

        initViews()

        sellingBook = intent.getSerializableExtra(EXTRA_SELLING_BOOK) as SellingBook
        sellingBook.increaseSeenCount()
        binding.sellingBook = sellingBook
        account = intent.getParcelableExtra(EXTRA_ACCOUNT) as Account?

        supportActionBar?.title = sellingBook.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buildDialog()

        initViewModel()
    }

    private fun buildDialog(){
        successDialog = Tools.animationDialog(this@SellingBookDetailsActivity,
            "Congrats you bought ${sellingBook.title}", "success_animation.json",
            "Back", View.OnClickListener {
                onBackPressed()
            })
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

    private fun initViews() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.bottomSheetLayout.bottomSheerRootView)
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

        binding.bottomSheetLayout.commentBottomSheetEditText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.bottomSheetLayout.commentAddButton.isEnabled = s.toString().isNotEmpty()
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
            if ((sellingBook.count - sellingBook.soldCount) > 0) {
                account?.purchase(
                    sellingBook,
                    object : UploadDataCallbacks {
                        override fun onSuccess() {
                            successDialog.show()
                        }

                        override fun onError(message: String) {
                            Tools.showToast(this@SellingBookDetailsActivity, message)
                        }

                    })
            }else{
                Tools.showToast(this, "All books are sold")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        val rating = binding.ratingBar.rating
        if (rating != 0F) {
            sellingBook.rate(rating)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
