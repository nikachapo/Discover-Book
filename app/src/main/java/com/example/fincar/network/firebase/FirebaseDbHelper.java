package com.example.fincar.network.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.fincar.bean.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public final class FirebaseDbHelper implements LifecycleObserver {
    private static final String USERS_KEY = "users";

    private static FirebaseDbHelper instance;

    private DatabaseReference usersReference;
    private ValueEventListener checkUserListener;

    private DatabaseReference currentUserReference;
    private ValueEventListener userWithUidListener;

    private FirebaseDbHelper() {
        usersReference = getReference().child(USERS_KEY);
        currentUserReference = usersReference.child(getCurrentUser().getUid());
    }

    public static FirebaseDbHelper getInstance() {
        if (instance == null) {
            synchronized (FirebaseDbHelper.class) {
                if (instance == null) {
                    instance = new FirebaseDbHelper();
                }
            }
        }

        return instance;
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public DatabaseReference getReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public void checkUser(CheckUserCallbacks callbacks) {
        initCheckUserListener(callbacks);
                usersReference
                .child(getCurrentUser().getUid())
                .addValueEventListener(checkUserListener);

    }

    private void initCheckUserListener(CheckUserCallbacks callbacks) {
        if (checkUserListener == null) {
            checkUserListener = new ValueEventListener() {
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

    public void registerUser(@NonNull UserModel userModel,
                             RegistrationCallbacks registrationCallbacks) {

        currentUserReference
                .setValue(userModel)
                .addOnSuccessListener(v ->
                        registrationCallbacks.onSuccessRegistration())
                .addOnFailureListener(e ->
                        registrationCallbacks.onError(Objects.requireNonNull(e.getMessage())));

    }

    public void getCurrentUser(FetchUserDataCallbacks fetchUserDataCallbacks) {
        initUserWithUidListener(fetchUserDataCallbacks);
    }

    private void initUserWithUidListener(FetchUserDataCallbacks fetchUserDataCallbacks) {
        if (userWithUidListener == null) {
            userWithUidListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    fetchUserDataCallbacks.onReceive(userModel);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    fetchUserDataCallbacks.onError(databaseError.getMessage());
                }
            };
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void removeListeners() {
        if (usersReference != null && checkUserListener != null) {
            usersReference.removeEventListener(checkUserListener);
        }

        if (currentUserReference != null && userWithUidListener != null) {
            currentUserReference.removeEventListener(userWithUidListener);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void addListeners() {
        if (usersReference != null && checkUserListener != null) {
            usersReference.addListenerForSingleValueEvent(checkUserListener);
        }

        if (currentUserReference != null && userWithUidListener != null) {
            currentUserReference.addListenerForSingleValueEvent(userWithUidListener);
        }
    }

}
