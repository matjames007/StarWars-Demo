package com.example.firebasedemoapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val storage = FirebaseStorage.getInstance()


    /**
     * Example of Firebase Storage Read
     *
     * Loads the background image from Firebase Storage and sets it as the background of the
     * application's main layout.
     */
    fun loadBackgroundFromFirebase() {
        val storageRef = storage.reference.child("app_files/saksham-gangwar-YVgOh8w1R4s-unsplash.jpg")
        val localFile = File.createTempFile("images", "jpg")

        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
        }.addOnFailureListener {
            Log.e("MainActivity", "Error getting background image", it)
        }
    }

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

        val ufoButton: Button = findViewById<Button>(R.id.ufoButton)
        ufoButton.setOnClickListener {
            val intent = Intent(this, UFOActivity::class.java)
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
            loadBackgroundFromFirebase()
        }

        val starShipButton: Button = findViewById<Button>(R.id.starShipButton)
        val planetButton: Button = findViewById<Button>(R.id.planetButton)
        val ufoButton: Button = findViewById<Button>(R.id.ufoButton)
        starShipButton.visibility = visibility
        planetButton.visibility = visibility
        ufoButton.visibility = visibility

    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}