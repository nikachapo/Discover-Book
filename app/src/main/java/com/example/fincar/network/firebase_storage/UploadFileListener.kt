package com.example.fincar.network.firebase_storage

import com.example.fincar.network.Error

interface UploadFileListener : Error {
    fun onSuccess(url: String)
}