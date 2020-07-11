package com.example.fincar.network.firebase;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public abstract class FirebaseRealtimeDbLifecycleObserver extends FirebaseDbHelper
        implements LifecycleObserver {

    protected DatabaseReference keyReference;
    protected ValueEventListener eventListener;

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void addListeners() {
        keyReference.addValueEventListener(eventListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void removeListeners() {
        keyReference.removeEventListener(eventListener);
    }

}
