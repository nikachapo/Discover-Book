package com.example.fincar.network.firebase.account;

import com.example.fincar.network.Error;

public interface CheckAccountCallbacks extends Error {
    void onExists();
    void onNotFound();
}
