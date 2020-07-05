package com.example.fincar.fragments.starred

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.book_db.BookRepository

class StarredBooksViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: BookRepository? = null

    init {
        repository = BookRepository(application)
    }

    fun getStarredBooks(): LiveData<List<GoogleBook>>? {
        return repository?.getStarredBooks()
    }
}