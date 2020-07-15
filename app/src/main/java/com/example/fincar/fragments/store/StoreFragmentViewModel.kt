package com.example.fincar.fragments.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.models.book.SellingBook

class StoreFragmentViewModel : ViewModel() {

    private val _sellingBooksLiveData = MutableLiveData<List<SellingBook>>()

    fun getSellingBooksLiveData():LiveData<List<SellingBook>> = _sellingBooksLiveData

    fun setBooksData(books:List<SellingBook>){
        _sellingBooksLiveData.value = books
    }

}