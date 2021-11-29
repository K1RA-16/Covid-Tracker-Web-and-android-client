package com.example.covitrack

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val state: ArrayList<String>, private val cases: ArrayList<String>, private val deceased: ArrayList<String>, private val rec: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.list, state) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list, null, true)
        val st = rowView.findViewById<TextView>(R.id.state)
        val confirmed = rowView.findViewById(R.id.confirmedCases) as TextView
        val death = rowView.findViewById(R.id.confirmedDeaths) as TextView
        val recovered = rowView.findViewById(R.id.recoveredCases) as TextView
        st.text = state[position]
        confirmed.text = cases[position].toString()
        death.text = deceased[position].toString()
        recovered.text = rec[position].toString()

        return rowView
    }
}