package com.example.fincar.fragments.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var email: String?, var phone: String?,
    var firstName: String?, var lastName: String?,
    var birthDate: String?, var location: String?
) : Parcelable{
    constructor():this("","","","","","")
}