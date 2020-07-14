package com.example.fincar.bean

import com.example.fincar.network.firebase.FirebaseDbHelper
import com.google.firebase.database.FirebaseDatabase

class Comment(val postId:String,
              val ownerId: String,val ownerProfileUrl:String, val ownerUserName:String,
              val commentText: String, val date: String){

    var id: String? = ""

    fun addCommentToDB(){
        val commentsRef = FirebaseDatabase.getInstance().getReference(FirebaseDbHelper.SELLING_BOOKS_KEY)
            .child(postId)
            .child("comments")
        id = commentsRef.push().key

        commentsRef.child(id!!)
            .setValue(this)

    }
}