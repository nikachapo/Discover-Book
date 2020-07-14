package com.example.fincar.bean.book

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

    fun isRated() = rating != 0.0

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
}