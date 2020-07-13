package com.example.fincar.fragments.profile

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.book_db.BookRepository

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: BookRepository? = null

    init {
        repository = BookRepository(application)
    }

    fun getStarredBooks(): LiveData<List<GoogleBook>>? {
        return repository?.getStarredBooks()
    }

    override fun onCleared() {
        super.onCleared()
        d("Cleeeeeeeeeared","yeaaa sorry (((bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")

    }
}