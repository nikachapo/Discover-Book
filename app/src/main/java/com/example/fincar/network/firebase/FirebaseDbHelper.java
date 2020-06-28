package com.example.fincar.network.firebase;

import androidx.annotation.NonNull;

import com.example.fincar.fragments.profile.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public final class FirebaseDbHelper {
    public static final String USERS_KEY = "users";


    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DatabaseReference getReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static void checkUser(CheckUserCallbacks callbacks) {
        getReference().child(USERS_KEY)
                .child(getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            callbacks.onExists();
                        }else callbacks.onNotFound();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbacks.onError(databaseError.getMessage());
                    }
                });

    }

    public static void registerUser(UserModel userModel, RegistrationCallbacks registrationCallbacks){
        getReference().child(USERS_KEY)
                .child(getCurrentUser().getUid())
                .setValue(userModel)
                .addOnSuccessListener(v -> registrationCallbacks.onSuccessRegistration())
                .addOnFailureListener(e -> registrationCallbacks.onError(Objects.requireNonNull(e.getMessage())));
    }

    public static void getUserWithUid(String uId, FetchUserDataCallbacks fetchUserDataCallbacks){
        getReference().child(USERS_KEY)
                .child(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        fetchUserDataCallbacks.onReceive(userModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        fetchUserDataCallbacks.onError(databaseError.getMessage());
                    }
                });
    }
}
