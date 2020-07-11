package com.example.fincar.bean.book

import androidx.room.Entity

@Entity(tableName = "book_table")
class GoogleBook: Book(){
    var isPdfAvailable: Boolean = false
    var pdfLink: String? = ""
    var previewUrl: String? = ""
    var language: String? = ""
}