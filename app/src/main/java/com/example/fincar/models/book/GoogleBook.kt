package com.example.fincar.models.book

import androidx.room.Entity

@Entity(tableName = "book_table")
class GoogleBook: Book(){
    var isPdfAvailable: Boolean = false
    var readProgress: Int = 0
    var pdfLink: String? = ""
    var previewUrl: String? = ""
    var language: String? = ""
}