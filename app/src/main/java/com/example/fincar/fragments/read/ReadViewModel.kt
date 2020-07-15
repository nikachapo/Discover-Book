package com.example.fincar.fragments.read

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fincar.book_db.BookRepository
import com.example.fincar.models.book.GoogleBook

class ReadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository = BookRepository(application)

    fun delete(googleBook: GoogleBook) {
        repository.delete(googleBook)
    }

    fun update(googleBook: GoogleBook){
        repository.update(googleBook)
    }

    fun getBooksWithPDF():LiveData<List<GoogleBook>>{
        return repository.getBooksWithPDF()
    }


}