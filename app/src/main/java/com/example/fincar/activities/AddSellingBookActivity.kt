package com.example.fincar.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.example.fincar.bean.Account
import com.example.fincar.bean.book.SellingBook
import com.example.fincar.extensions.loadImage
import com.example.fincar.network.firebase.account.AccountDataObserver
import com.example.fincar.network.firebase.account.FetchAccountDataCallbacks
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import com.example.fincar.network.firebase.upload.Uploader
import com.example.fincar.network.firebase_storage.FirebaseStorageHelper
import com.example.fincar.network.firebase_storage.UploadFileListener
import kotlinx.android.synthetic.main.activity_add_selling_book.*
import java.text.DateFormat
import java.util.*

class AddSellingBookActivity : AppCompatActivity() {

    private var currentUser: Account? = null
    private val uploader = Uploader()
    private lateinit var accountDataObserver: AccountDataObserver
    private var imageUri: Uri? = null
    private var imageUrl = ""
    private lateinit var currentDateString: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_selling_book)

        currentDateString =
            DateFormat.getDateInstance().format(Calendar.getInstance().time)

        //override transition for circular reveal
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)

        accountDataObserver = AccountDataObserver(fetchUserDataEventListener)

        rootLayout.visibility = View.INVISIBLE

        Tools.startTransition(rootLayout)

        lifecycle.addObserver(accountDataObserver)

        setListeners()
    }

    private fun setListeners() {
        sellingBookImageView.setOnClickListener {
            Tools.openImageChooser(this)
        }
        sellingBookUploadButton.setOnClickListener {
            uploadData()
        }
    }

    private fun uploadData() {
        if (imageUri != null) {
            FirebaseStorageHelper
                .putFileToStorage(FirebaseStorageHelper.DIR_BOOKS_PICTURES, imageUri!!,
                    object : UploadFileListener {
                        override fun onSuccess(url: String) {
                            imageUrl = url
                            uploader.upload(
                                SellingBook::class.java, getBookWithCurrentData(),
                                uploadBookEventListener
                            )
                        }

                        override fun onError(message: String) {
                            Tools.showToast(this@AddSellingBookActivity, message)
                        }

                    })
        }
    }

    private fun getBookWithCurrentData(): SellingBook? {
        var book: SellingBook? = null
        if (currentUser != null) {
            book = SellingBook(currentUser?.uId!!)
            book.title = sellingBookTitleEditText.text.toString()
            book.description = sellingBookDetailsEditText.text.toString()
            book.price = sellingBookPriceEditText.text.toString().toDouble()
            book.pageCount = sellingBookPagesEditText.text.toString().toInt()
            book.count = sellingBookAmountEditText.text.toString().toInt()
            book.publishDate = currentDateString
            book.photoUrl = imageUrl
            book.ownerProfileUrl = currentUser!!.imageUrl!!
            book.publisher = "${currentUser!!.firstName} ${currentUser!!.lastName}"
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Tools.PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            sellingBookImageView.loadImage(imageUri)
        }

    }

    override fun onBackPressed() {
        Tools.startBackRevealTransition(rootLayout, this)
    }

}
