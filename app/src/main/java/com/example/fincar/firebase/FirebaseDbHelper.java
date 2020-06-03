package com.example.fincar.firebase;

import androidx.annotation.NonNull;

import com.example.fincar.fragments.profile.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public final class FirebaseDbHelper {
    public static final String USERS_KEY = "users";


    public interface CheckUserCallBacks {
        void onExists();
        void onNotFound();
        void onCancel(String errorMessage);
    }
    public interface RegistrationCallbacks{
        void onSuccessRegistration();
        void onRegistrationFail(String message);
    }
    public interface ReceiveUserCallbacks{
        void onReceive(UserModel userModel);
        void onError(String message);
    }

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DatabaseReference getReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static void checkUser(FirebaseUser user, CheckUserCallBacks checkUserCallBacks) {
        getReference().child(USERS_KEY)
                .child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            checkUserCallBacks.onExists();
                        }else checkUserCallBacks.onNotFound();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        checkUserCallBacks.onCancel(databaseError.getMessage());
                    }
                });

    }

    public static void registerUser(UserModel userModel, RegistrationCallbacks registrationCallbacks){
        getReference().child(USERS_KEY)
                .child(getCurrentUser().getUid())
                .setValue(userModel)
                .addOnSuccessListener(aVoid -> registrationCallbacks.onSuccessRegistration())
                .addOnFailureListener(e -> registrationCallbacks.onRegistrationFail(e.getMessage()));
    }

    public static void getUserWithUid(String uId, ReceiveUserCallbacks receiveUserCallbacks){
        getReference().child(USERS_KEY)
                .child(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        receiveUserCallbacks.onReceive(userModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        receiveUserCallbacks.onError(databaseError.getMessage());
                    }
                });
    }
}
