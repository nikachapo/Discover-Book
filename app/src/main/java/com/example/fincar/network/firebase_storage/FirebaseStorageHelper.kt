package com.example.fincar.network.firebase_storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

object FirebaseStorageHelper {

    private val pictureReference by lazy {
        FirebaseStorage.getInstance().reference
            .child("user_profiles")
            .child("${System.currentTimeMillis()}")
    }

    fun putFileToStorage(uri: Uri, onUploadFileListener: UploadFileListener){
        pictureReference.putFile(uri)
            .continueWithTask {
                if (!it.isSuccessful && it.exception !=null) {
                    throw it.exception!!;
                }
                pictureReference.downloadUrl;
            }.addOnSuccessListener {
                onUploadFileListener.onSuccess(it.toString())
            }
    }
}