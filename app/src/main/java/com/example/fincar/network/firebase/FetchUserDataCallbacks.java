package com.example.fincar.network.firebase;

import com.example.fincar.bean.UserModel;
import com.example.fincar.network.Error;

public interface FetchUserDataCallbacks extends Error {
    void onReceive(UserModel userModel);

}
