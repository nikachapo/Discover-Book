package com.example.fincar.ui.home

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fincar.book.BookModel
import com.example.fincar.book.BookRepository

private const val TAG = "BookViewModel"
class BookViewModel(q: String?) : ViewModel() {

    private val repository: BookRepository =
        BookRepository()
    private val searchStringLive: MutableLiveData<String> = MutableLiveData()
    val bookLiveData: LiveData<List<BookModel>>

    init {

        if (q != null) search(q)

        bookLiveData = Transformations
            .switchMap(searchStringLive) { input -> repository.getBooks(input.toString()) }

    }

    fun search(q: String?) {
        d(TAG,"search($q)")
        if (q != null) searchStringLive.value = q
    }
}