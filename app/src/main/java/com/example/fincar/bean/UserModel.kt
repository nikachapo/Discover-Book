package com.example.fincar.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var email: String?, var phone: String?,
    var firstName: String?, var lastName: String?,
    var birthDate: String?, var location: String?, var imageUrl: String? = null
) : Parcelable{
    constructor():this("","","","","","","")
}