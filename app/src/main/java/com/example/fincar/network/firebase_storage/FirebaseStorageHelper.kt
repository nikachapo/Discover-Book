package com.example.fincar.network.firebase_storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseStorageHelper {

    const val DIR_USER_PICTURES = "user_profiles"
    const val DIR_BOOKS_PICTURES = "books_pictures"

    private val pictureReference by lazy {
        FirebaseStorage.getInstance().reference
            .child(DIR_USER_PICTURES)
            .child("${System.currentTimeMillis()}")
    }

    private val booksPictureReference by lazy {
        FirebaseStorage.getInstance().reference
            .child(DIR_BOOKS_PICTURES)
            .child("${System.currentTimeMillis()}")
    }

    fun putFileToStorage(directory: String, uri: Uri, onUploadFileListener: UploadFileListener) {
        val dirReference =
            if (directory == DIR_BOOKS_PICTURES) {
                booksPictureReference
            } else {
                pictureReference
            }

        dirReference.putFile(uri)
            .continueWithTask {
                if (!it.isSuccessful && it.exception != null) {
                    throw it.exception!!
                }
                dirReference.downloadUrl
            }.addOnSuccessListener {
                onUploadFileListener.onSuccess(it.toString())
            }
    }
}