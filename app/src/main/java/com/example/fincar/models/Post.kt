package com.example.fincar.models

import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.network.firebase.upload.UploadDataCallbacks
import com.google.firebase.database.FirebaseDatabase

class Post(
    val ownerId: String, val ownerUserName: String,
    val ownerPhotoUrl: String, val postTitle: String, val postBody: String,
    val date:String
) {
    constructor():this("","","","","","")
    var id = ""

    fun addPost(uploadDataCallbacks: UploadDataCallbacks){
        val postsRef = FirebaseDatabase.getInstance()
            .getReference(FirebaseDbHelper.POSTS_KEY)

        id = postsRef.push().key.toString()
        postsRef.child(id)
            .setValue(this)
            .addOnSuccessListener {
                uploadDataCallbacks.onSuccess()
            }.addOnFailureListener {
                uploadDataCallbacks.onError(it.message.toString())
            }
    }
}