package com.example.firebasedemoapp.models

import android.util.Log
import com.google.firebase.firestore.QueryDocumentSnapshot

class Starship(val name: String, val model: String, val manufacturer: String) {
    companion object {
        fun toStarship(map: MutableMap<String, Any>):Starship {

            val data: MutableMap<String, Any> = map.get("starship") as MutableMap<String, Any>


            val name = data.get("name") as String
            val model = data.get("model") as String
            val manufacturer = data.get("manufacturer") as String

            Log.d("Starship", "parsed: $name $model $manufacturer")
            return Starship(name, model, manufacturer)
        }
    }
}