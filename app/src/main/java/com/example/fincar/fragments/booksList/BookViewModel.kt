package com.example.fincar.fragments.booksList


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fincar.book.BookModel
import com.example.fincar.book.BookRepository

class BookViewModel(q: String?) : ViewModel() {

    private var repository: BookRepository? = null
    private val searchStringLive: MutableLiveData<String> = MutableLiveData()
    val allBooksLiveData: LiveData<List<BookModel>>

    init {
        repository = BookRepository(null)

        if (q != null) search(q)
        allBooksLiveData = Transformations
            .switchMap(searchStringLive) { input -> repository!!.getBooks(input.toString()) }
    }

    fun search(q: String?) {
        if (q != null) searchStringLive.value = q
    }


}