package com.example.fincar.models

import android.os.Parcelable
import com.example.fincar.models.book.SellingBook
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    var uId: String?,
    var email: String?, var phone: String?,
    var firstName: String?, var lastName: String?,
    var birthDate: String?, var location: String?,
    var imageUrl: String? = null,
    var purchasedBooksCount: Int? = 0,
    var reviewsCount: Int? = 0,
    var soldBooksCount: Int? = 0,
    var balance: Double? = 1000.0
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "")

    fun purchase(sellingBook: SellingBook, uploadDataCallbacks: UploadDataCallbacks) {

        if(balance!! < sellingBook.price){
            uploadDataCallbacks.onError("Not Enough Money")
            return
        }

        val usersRef = FirebaseDatabase.getInstance().reference
            .child(FirebaseDbHelper.USERS_KEY)

        val ownerRef = usersRef
            .child(sellingBook.ownerId)

        ownerRef
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    uploadDataCallbacks.onError(p0.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val ownerAccount = snapshot.getValue(Account::class.java)

                    sellingBook.onSold()

                    ownerRef.child("balance")
                        .setValue(ownerAccount?.balance!! + sellingBook.price)
                    ownerRef.child("soldBooksCount")
                        .setValue(ownerAccount.soldBooksCount!! + 1)

                    usersRef
                        .child(uId!!)
                        .child("balance")
                        .setValue(balance!! - sellingBook.price)

                    usersRef.child(uId!!)
                        .child("purchasedBooksCount")
                        .setValue(purchasedBooksCount!! + 1)

                    uploadDataCallbacks.onSuccess()
                }

            })
    }
}