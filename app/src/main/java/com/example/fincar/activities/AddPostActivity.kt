package com.example.fincar.activities

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.example.fincar.models.Account
import com.example.fincar.models.Post
import com.example.fincar.network.firebase.account.AccountDataObserver
import com.example.fincar.network.firebase.account.FetchAccountDataCallbacks
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import kotlinx.android.synthetic.main.activity_add_post.*
import java.text.DateFormat
import java.util.*

class AddPostActivity : AppCompatActivity() {
    private var account: Account? = null
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.post)


        dialog = Tools.animationDialog(this, "Posting...", "uploading.json",
            "Cancel", View.OnClickListener {
                onBackPressed()
            })

        val accountDataObserver = AccountDataObserver(fetchUserDataEventListener)
        lifecycle.addObserver(accountDataObserver)

        postButton.setOnClickListener {
            addPost()
        }

    }

    private fun addPost() {
        dialog.show()
        val date = DateFormat.getDateInstance().format(Calendar.getInstance().time)
        val postTitle = postTitleEditText.text.toString()
        val postBody = postBodyEditText.text.toString()
        if (postTitle.isNotEmpty() && postBody.isNotEmpty()){
            val post = Post(
                account?.uId!!,
                "${account!!.firstName} ${account!!.lastName}",
                account!!.imageUrl!!, postTitle, postBody, date
            )

            post.addPost(object :UploadDataCallbacks{
                override fun onSuccess() {
                    onBackPressed()
                }

                override fun onError(message: String) {
                    dialog.dismiss()
                }

            })
        }
    }

    private val fetchUserDataEventListener = object :
        FetchAccountDataCallbacks {
        override fun onReceive(account: Account?) {
            this@AddPostActivity.account = account!!
        }

        override fun onError(message: String) {
            Tools.showToast(this@AddPostActivity, message)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
