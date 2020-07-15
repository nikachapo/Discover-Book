package com.example.fincar.activities.selling_book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.models.Account
import com.example.fincar.models.book.SellingBook

@Suppress("UNCHECKED_CAST")
open class SellingBookDetailsViewModelFactory(
    private val account: Account,
    private val sellingBook: SellingBook
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SellingBookDetailsViewModel(account, sellingBook) as T
    }
}