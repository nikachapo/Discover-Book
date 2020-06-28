package com.example.fincar.book_db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "book_table")
data class BookModel(
    @PrimaryKey
    var id: String,
    var title: String?,
    var description: String?,
    var publisher: String?,
    var publishDate: String?,
    var authors: String?,
    var categories: String?,
    var photoUrl: String?,
    var previewUrl: String?,
    var pageCount: Int?,
    var language: String?,
    var isPdfAvailable: Boolean?,
    var pdfLink: String?
):Parcelable
