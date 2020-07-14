package com.example.fincar.network.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class FirebaseDbHelper {
    protected static final String USERS_KEY = "users";
    public static final String SELLING_BOOKS_KEY = "selling_books";

    private final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    protected DatabaseReference getRootReference() {
        return rootReference;
    }
    protected FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }
}
