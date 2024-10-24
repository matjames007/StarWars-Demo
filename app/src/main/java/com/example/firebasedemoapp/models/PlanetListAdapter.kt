package com.example.firebasedemoapp.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.firebasedemoapp.R

class PlanetListAdapter(private val context: Context, private val planets: List<Planet>): BaseAdapter() {

    override fun getCount(): Int {
        return planets.size
    }

    override fun getItem(p0: Int): Any {
        return planets[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        if(p1 != null) {
            view = p1
        } else {
            view = LayoutInflater.from(this.context)
                .inflate(R.layout.planet_list_item, p2, false)
        }
        view.findViewById<TextView>(R.id.planetName).text = planets[p0].name
        view.findViewById<TextView>(R.id.planetTerrain).text = planets[p0].terrain
        view.findViewById<TextView>(R.id.planetClimate).text = planets[p0].climate
        return view
    }
}