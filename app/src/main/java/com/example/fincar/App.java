package com.example.fincar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


public class App extends Application {
    private static App instance;
    public static App getInstance() {
        if (instance== null) {
            synchronized(App.class) {
                if (instance == null)
                    instance = new App();
            }
        }

        return instance;
    }

    public static Context getContext(){
        return getInstance().getApplicationContext();
    }
}
