package com.example.firebasedemoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginButton: Button = findViewById<Button>(R.id.loginButton)

        // Initialize Firebase Auth
        auth = Firebase.auth
        setupMenu()

        loginButton.setOnClickListener {
            if(auth.currentUser == null){
                launchLoginActivity()
            } else {
                auth.signOut()
                setupMenu()
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            }
        }

        val planetButton: Button = findViewById<Button>(R.id.planetButton)
        planetButton.setOnClickListener {
            val intent = Intent(this, Planets::class.java)
            startActivity(intent)
        }

        val starShipButton: Button = findViewById<Button>(R.id.starShipButton)
        starShipButton.setOnClickListener {
            val intent = Intent(this, Starships::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Check if user is signed in (non-null) and update UI accordingly.
        setupMenu()
    }

    fun setupMenu() {
        val currentUser = auth.currentUser
        var visibility = Button.INVISIBLE
        val loginButton: Button = findViewById<Button>(R.id.loginButton)
        val usernameLabel = findViewById<TextView>(R.id.usernameLabel)

        if (currentUser == null) {
            loginButton.text = getString(R.string.loginButtonName)
            usernameLabel.text = getString(R.string.defaultDetails)
        } else {
            loginButton.text = getString(R.string.logoutButtonText)
            usernameLabel.text = getString(R.string.welcome_emperor, currentUser.email)
            visibility = Button.VISIBLE
        }

        val starShipButton: Button = findViewById<Button>(R.id.starShipButton)
        val planetButton: Button = findViewById<Button>(R.id.planetButton)
        starShipButton.visibility = visibility
        planetButton.visibility = visibility
    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}