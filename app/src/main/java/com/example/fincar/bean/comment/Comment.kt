package com.example.fincar.bean.comment

class Comment(val ownerId: String,val ownerProfileUrl:String, val ownerUserName:String,
              val commentText: String, val date: String){

    constructor():this("","","","","")

    var id: String? = ""
}