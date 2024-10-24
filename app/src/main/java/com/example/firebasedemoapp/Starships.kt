package com.example.firebasedemoapp

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasedemoapp.models.Starship
import com.example.firebasedemoapp.models.StarshipListAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Starships : AppCompatActivity() {

    val db = Firebase.firestore
    val starships = mutableListOf<Starship>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starships)

        loadStarships()
    }

    fun loadStarships() {
        db.collection("starships")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Starships", "${document.id} => ${document.data}")
                    starships.add(Starship.toStarship(document.data))
                }
                val adapter = StarshipListAdapter(this, starships)
                findViewById<ListView>(R.id.starshipList).adapter = adapter
            }
            .addOnFailureListener {
                Log.d("Planets", "Error getting documents: ", it)
            }
    }
}