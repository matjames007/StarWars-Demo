package com.example.firebasedemoapp.models

import android.util.Log


class Planet(val name: String, val terrain: String, val climate: String) {
    companion object {
        fun toPlanet(data: MutableMap<String, Any>):Planet {
            Log.d("Planet", "toPlanet: $data")

            val map: MutableMap<String, Any> = data.get("planet") as MutableMap<String, Any>

            Log.d("Planet", "map: $map")

            val name = map.get("name") as String
            val terrain = map.get("terrain") as String
            val climate = map.get("climate") as String

            return Planet(name, terrain, climate)
        }

    }
}