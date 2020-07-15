package com.example.fincar.models.book

import androidx.room.PrimaryKey
import java.io.Serializable

abstract class Book(
    @PrimaryKey
    var id: String,
    var title: String?,
    var description: String?,
    var publisher: String?,
    var publishDate: String?,
    var authors: String?,
    var categories: String?,
    var photoUrl: String?,
    var pageCount: Int?) : Serializable{

    constructor() : this("","","","","","","","",0)

}