package com.example.fincar.network.firebase.account;

import com.example.fincar.bean.Account;
import com.example.fincar.network.Error;

public interface FetchAccountDataCallbacks extends Error {
    void onReceive(Account account);
}