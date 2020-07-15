package com.example.fincar.activities.selling_book_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.models.book.SellingBook
import com.example.fincar.models.comment.Comment
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.google.firebase.database.*
import java.text.DateFormat
import java.util.*

class SellingBookDetailsViewModel(
    private val account: com.example.fincar.models.Account?,
    private val sellingBook: SellingBook
) : ViewModel() {

    private val _comment = MutableLiveData<Comment>()

    init {
        getComments()
    }

    fun getCommentLiveData(): LiveData<Comment> = _comment

    fun addComment(commentText: String) {
        val date = DateFormat.getDateInstance().format(Calendar.getInstance().time)
        val comment = Comment(
            account!!.uId!!,
            account.imageUrl!!, "${account.firstName} ${account.lastName}",
            commentText, date
        )
        sellingBook.addComment(comment)
    }

    private fun getComments() {
        FirebaseDatabase.getInstance()
            .getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
            .child(sellingBook.id)
            .child("comments").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    _comment.value = dataSnapshot.getValue(Comment::class.java)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("Not yet implemented")
                }


            })
    }


}