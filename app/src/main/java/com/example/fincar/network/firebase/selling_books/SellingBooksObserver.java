package com.example.fincar.network.firebase.selling_books;

import androidx.annotation.NonNull;

import com.example.fincar.models.book.SellingBook;
import com.example.fincar.network.firebase.FirebaseRealtimeDbLifecycleObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public final class SellingBooksObserver extends FirebaseRealtimeDbLifecycleObserver {

    public SellingBooksObserver(FetchSellingBooksCallbacks sellingBooksCallbacks){
        keyReference = getRootReference().child(SELLING_BOOKS_KEY);
        eventListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<SellingBook> books = new ArrayList<>();
                for (DataSnapshot book : dataSnapshot.getChildren()) {
                    books.add(book.getValue(SellingBook.class));
                }
                sellingBooksCallbacks.getSellingBooks(books);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sellingBooksCallbacks.onError(databaseError.getMessage());
            }
        };
    }
}
