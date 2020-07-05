package com.example.fincar.activities.book_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.book_db.BookRepository

class BookDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository = BookRepository(application)

    fun getBookCountLiveData(id: String) = repository.getBookCountLiveData(id)

    fun delete(googleBook: GoogleBook) {
        repository.delete(googleBook)
    }

    fun insert(googleBook: GoogleBook) {
        repository.insert(googleBook)
    }

}