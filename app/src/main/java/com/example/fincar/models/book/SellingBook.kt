package com.example.fincar.models.book

import com.example.fincar.models.comment.Comment
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.google.firebase.database.FirebaseDatabase

class SellingBook(var ownerId: String): Book() {

    constructor(): this("")
    var ownerProfileUrl = ""
    var seenCount: Int = 0
    var price: Double = 0.0
    var count: Int = 0
    var soldCount: Int = 0
    var location: String = ""
    var rating: Double = 0.0
    var ratedCount = 0

    fun rate(numRate: Float){
        val rating =
            ((rating * ratedCount) +
                    numRate) / (ratedCount + 1)

        val bookRef = FirebaseDatabase.getInstance().reference
            .child("selling_books")
            .child(id)

        bookRef.child("ratedCount")
            .setValue(ratedCount + 1)
        bookRef.child("rating")
            .setValue(rating)
    }

    private fun getCommentsRef() = FirebaseDatabase.getInstance()
        .getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
        .child(id)
        .child("comments")

    fun increaseSeenCount(){
        seenCount++
        FirebaseDatabase.getInstance()
            .getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
            .child(id)
            .child("seenCount")
            .setValue(seenCount)
    }

    fun addComment(comment: Comment){
        val bookCommentsRef = getCommentsRef()

        comment.id = bookCommentsRef.push().key!!

        bookCommentsRef.child(comment.id!!)
            .setValue(comment)
    }

    fun onSold(){
        soldCount++
        count--
        FirebaseDatabase.getInstance()
            .getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
            .child(id)
            .child("count")
            .setValue(count)

        FirebaseDatabase.getInstance()
            .getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
            .child(id)
            .child("soldCount")
            .setValue(soldCount)
    }
}