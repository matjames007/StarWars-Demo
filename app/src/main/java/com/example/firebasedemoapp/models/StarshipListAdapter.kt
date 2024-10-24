package com.example.firebasedemoapp.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.firebasedemoapp.R

class StarshipListAdapter(val context: Context, private val starships: List<Starship>): BaseAdapter()  {
    override fun getCount(): Int {
        return starships.size
    }

    override fun getItem(p0: Int): Any {
        return starships[p0]
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
                .inflate(R.layout.startship_list_item, p2, false)
        }

        view.findViewById<TextView>(R.id.starshipName).text = starships[p0].name
        view.findViewById<TextView>(R.id.starshipModel).text = starships[p0].model
        view.findViewById<TextView>(R.id.starshipManufacturer).text = starships[p0].manufacturer
        return view
    }
}