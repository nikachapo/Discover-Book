package com.example.fincar.network.firebase.account;

import androidx.annotation.NonNull;

import com.example.fincar.network.firebase.FirebaseRealtimeDbLifecycleObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public final class AccountChecker extends FirebaseRealtimeDbLifecycleObserver {

    public AccountChecker(CheckAccountCallbacks callbacks) {
        keyReference = getRootReference().child(USERS_KEY)
        .child(Objects.requireNonNull(getFirebaseUser().getUid()));

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callbacks.onExists();
                } else callbacks.onNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbacks.onError(databaseError.getMessage());
            }
        };
    }
}
