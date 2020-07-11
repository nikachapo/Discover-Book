package com.example.fincar.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    var email: String?, var phone: String?,
    var firstName: String?, var lastName: String?,
    var birthDate: String?, var location: String?,
    var imageUrl: String? = null,
    var purchasedBooksCount: Int? = 0,
    var reviewsCount: Int? = 0,
    var soldBooksCount: Int? = 0
) : Parcelable{
    constructor():this("","","","","","","")
}