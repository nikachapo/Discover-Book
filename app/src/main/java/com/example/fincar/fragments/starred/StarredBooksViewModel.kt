package com.example.fincar.fragments.starred

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fincar.book.BookModel
import com.example.fincar.book.BookRepository

class StarredBooksViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: BookRepository? = null

    init {
        repository = BookRepository(application)
    }


    fun insert(book: BookModel) {
        repository?.insert(book)
    }


    fun delete(book: BookModel) {
        repository?.delete(book)
    }

    fun getStarredBooks(): LiveData<List<BookModel>>? {
        return repository?.getStarredBooks()
    }
}