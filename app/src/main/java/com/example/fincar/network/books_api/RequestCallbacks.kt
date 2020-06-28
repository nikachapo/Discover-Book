package com.example.fincar.network.books_api

import com.example.fincar.network.Error

interface RequestCallBacks : Error{
    fun onSuccess(successJson: String)
}