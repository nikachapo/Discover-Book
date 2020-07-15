package com.example.fincar.fragments.booksList


import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fincar.models.book.GoogleBook
import com.example.fincar.book_db.BookRepository

class BookViewModel(q: String?) : ViewModel() {

    private var repository: BookRepository? = null
    private val searchStringLive: MutableLiveData<String> = MutableLiveData()
    val allBooksLiveData: LiveData<List<GoogleBook>>

    init {
        repository = BookRepository(null)

        if (q != null) search(q)
        allBooksLiveData = Transformations
            .switchMap(searchStringLive) { input -> repository!!.getBooks(input.toString()) }
    }

    fun search(q: String?) {
        if (q != null) searchStringLive.value = q
    }

    override fun onCleared() {
        super.onCleared()
        d("Cleeeeeeeeeared","yeaaa sorry (((aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    }

}