package com.example.fincar.activities

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.example.fincar.bean.Account
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.network.firebase.account.AccountDataObserver
import com.example.fincar.network.firebase.account.FetchAccountDataCallbacks
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import com.example.fincar.network.firebase.upload.Uploader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_selling_book.*

class AddSellingBookActivity : AppCompatActivity() {

    private var currentUser: Account? = null
    private val uploader = Uploader()
    private lateinit var accountDataObserver: AccountDataObserver

    //TODO clean up code here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_selling_book)

        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)

        accountDataObserver =
            AccountDataObserver(
                fetchUserDataEventListener
            )

        rootLayout.visibility = View.INVISIBLE

        val viewTreeObserver = rootLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    circularRevealActivity()
                    rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        lifecycle.addObserver(accountDataObserver)

        setListeners()
    }

    private fun setListeners() {
        sellingBookUploadButton.setOnClickListener {
            uploader.upload(SellingBook::class.java, getBookWithCurrentData(), uploadBookEventListener)
        }
    }

    private fun getBookWithCurrentData(): SellingBook {

        //TODO 4 add UserModel uid parameter
        val book = SellingBook(FirebaseAuth.getInstance().currentUser?.uid.toString())
        book.title = sellingBookTitleEditText.text.toString()
        book.description = sellingBookDetailsEditText.text.toString()
        book.price = sellingBookPriceEditText.text.toString().toDouble()
        book.pageCount = sellingBookPagesEditText.text.toString().toInt()
        book.count = sellingBookAmountEditText.text.toString().toInt()
        book.publishDate = System.currentTimeMillis().toString()

        if(currentUser != null) book.publisher = "${currentUser!!.firstName} ${currentUser!!.lastName}"

        return book
    }

    private val uploadBookEventListener = object :
        UploadDataCallbacks {
        override fun onSuccess() {
            onBackPressed()
        }

        override fun onError(message: String) {
            Tools.showToast(this@AddSellingBookActivity, message)
        }

    }

    private val fetchUserDataEventListener = object :
        FetchAccountDataCallbacks {
        override fun onReceive(account: Account?) {
            currentUser = account!!
        }

        override fun onError(message: String) {
            Tools.showToast(this@AddSellingBookActivity, message)
        }

    }

    private fun circularRevealActivity() {
        val cx: Int = rootLayout.right
        val cy: Int = rootLayout.bottom

        val finalRadius: Float =
            rootLayout.width.coerceAtLeast(rootLayout.height).toFloat()

        val circularReveal = ViewAnimationUtils.createCircularReveal(
            rootLayout,
            cx,
            cy, 0f,
            finalRadius
        )

        circularReveal.duration = 1000
        rootLayout.visibility = View.VISIBLE
        circularReveal.start()
    }

    override fun onBackPressed() {
        val cx: Int = rootLayout.width
        val cy: Int = rootLayout.bottom

        val finalRadius: Float =
            rootLayout.width.coerceAtLeast(rootLayout.height).toFloat()
        val circularReveal =
            ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0f)

        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                rootLayout.visibility = View.INVISIBLE
                finish()
            }
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        circularReveal.duration = 1000
        circularReveal.start()

    }

}
