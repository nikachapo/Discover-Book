package com.example.fincar.network.firebase.upload;

import androidx.annotation.NonNull;

import com.example.fincar.bean.Account;
import com.example.fincar.bean.book.SellingBook;
import com.example.fincar.network.firebase.FirebaseDbHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class Uploader extends FirebaseDbHelper {

    private DatabaseReference currentUserReference = getRootReference()
            .child(USERS_KEY)
            .child(Objects.requireNonNull(getFirebaseUser()).getUid());

    private DatabaseReference sellingBooksReference = getRootReference()
            .child(SELLING_BOOKS_KEY);

    private void uploadUserData(@NonNull Account account,
                                UploadDataCallbacks uploadDataCallbacks) {

        currentUserReference
                .setValue(account)
                .addOnSuccessListener(v ->
                        uploadDataCallbacks.onSuccess())
                .addOnFailureListener(e ->
                        uploadDataCallbacks.onError(Objects.requireNonNull(e.getMessage())));

    }


    private void uploadBookData(@NonNull SellingBook sellingBook,
                                UploadDataCallbacks uploadDataCallbacks) {

        String bookId = sellingBooksReference.push().getKey();
        assert bookId != null;
        sellingBook.setId(bookId);
        sellingBooksReference.child(bookId)
                .setValue(sellingBook)
                .addOnSuccessListener(v -> uploadDataCallbacks.onSuccess())
                .addOnFailureListener(e ->
                        uploadDataCallbacks.onError(Objects.requireNonNull(e.getMessage())));
    }

    public <T> void upload(Class<T> typeClass, T t, UploadDataCallbacks uploadDataCallbacks){

        if(typeClass.isAssignableFrom(Account.class)){

            Account account = (Account) t;
            uploadUserData(account,uploadDataCallbacks);

        }else if(typeClass.isAssignableFrom(SellingBook.class)){

            SellingBook book = (SellingBook) t;
            uploadBookData(book, uploadDataCallbacks);

        }

    }

}
