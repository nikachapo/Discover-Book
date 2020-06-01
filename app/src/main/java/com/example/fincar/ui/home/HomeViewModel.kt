package com.example.fincar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.ui.booksList.BooksListFragment

class HomeViewModel : ViewModel(){
    private var bookListFragment = MutableLiveData<BooksListFragment>()

    fun setFragment(booksListFragment: BooksListFragment){
        bookListFragment.value = booksListFragment
    }

    fun getBooksListFragmentLiveData() = bookListFragment as LiveData<BooksListFragment>
}