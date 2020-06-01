package com.example.fincar.ui.booksList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BookViewModelFactory(private val q: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(q) as T
    }
}
