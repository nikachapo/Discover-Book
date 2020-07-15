package com.example.fincar.network.firebase.account;

import androidx.annotation.NonNull;

import com.example.fincar.models.Account;
import com.example.fincar.network.firebase.FirebaseRealtimeDbLifecycleObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public final class AccountDataObserver extends FirebaseRealtimeDbLifecycleObserver {

    public AccountDataObserver(FetchAccountDataCallbacks fetchAccountDataCallbacks) {
        keyReference = getRootReference().child(USERS_KEY)
                .child(Objects.requireNonNull(getFirebaseUser()).getUid());

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.getValue(Account.class);
                fetchAccountDataCallbacks.onReceive(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                fetchAccountDataCallbacks.onError(databaseError.getMessage());
            }
        };
    }
}
