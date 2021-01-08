package com.emiliorg.myrestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.emiliorg.myrestaurant.ConstantsMR;

class StartActivity : AppCompatActivity() {
    //Firebase
    var db: FirebaseDatabase? = null
    var ref: DatabaseReference? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        db = FirebaseDatabase.getInstance(ConstantsMR.FIREBASE_DB.toString());
        auth = FirebaseAuth.getInstance()
        ref = db?.getReference()
    }
}