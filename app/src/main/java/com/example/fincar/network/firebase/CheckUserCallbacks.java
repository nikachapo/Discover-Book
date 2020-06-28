package com.example.fincar.network.firebase;

import com.example.fincar.network.Error;

public interface CheckUserCallbacks extends Error {
    void onExists();
    void onNotFound();
}
