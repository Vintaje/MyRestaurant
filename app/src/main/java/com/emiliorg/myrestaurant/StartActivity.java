package com.emiliorg.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    //Firebase
    public FirebaseDatabase db;
    public DatabaseReference ref;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        db = FirebaseDatabase.getInstance(ConstantsMR.FIREBASE_DB);
        auth = FirebaseAuth.getInstance();
        ref = db.getReference();


    }

}