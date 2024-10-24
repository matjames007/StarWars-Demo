package com.example.firebasedemoapp

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasedemoapp.models.Planet
import com.example.firebasedemoapp.models.PlanetListAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Planets : AppCompatActivity() {

    val db = Firebase.firestore
    val planets = mutableListOf<Planet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)

        loadPlanets()
    }

    fun loadPlanets() {
        db.collection("planets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Planets", "${document.id} => ${document.data}")
                    planets.add(Planet.toPlanet(document.data))
                }
                val adapter = PlanetListAdapter(this, planets)
                findViewById<ListView>(R.id.planetList).adapter = adapter
            }
            .addOnFailureListener {
                Log.d("Planets", "Error getting documents: ", it)
            }
    }
}