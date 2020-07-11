package com.example.fincar.bean.book

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

}