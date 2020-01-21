package com.shiva.a22_firebase_auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    //    private lateinit var mAuthentication: FirebaseAuth
    private var mAuthentication: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Obtain the Firebase Authentication instance.
        mAuthentication = FirebaseAuth.getInstance()
        // The entry point for accessing a Firebase Database. You can get an instance by calling getInstance().
        // To access a location in the database and read or write data, use getReference()
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        buttonSubmit.setOnClickListener {
            val email = editTextEmailId.text.toString()
            val password = editTextPassword.text.toString()
            loginToFirebase(email, password)
        }
    }


    fun loginToFirebase(email: String, password: String) {
        mAuthentication!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG)
                        .show()
                    // .currentUser will return the current user
                    val currentUsr = mAuthentication!!.currentUser
                    // .uid will return the User Id
                    Log.d("LOGIN", currentUsr!!.uid)
                } else {
                    Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

        // OR
/*
        mAuthentication!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            })*/
    }

    override fun onStart() {
        super.onStart()
        // Firebase automatically saved the user information by using .currentUser method
        val currentUser = mAuthentication!!.currentUser
        Log.d("LOGIN",currentUser.toString())
        if (currentUser != null) {
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
