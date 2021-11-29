package com.example.covitrack

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.RequestQueue.RequestFilter
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var ls:ListView
    private lateinit var states: ArrayList<String>
    private lateinit var cases : ArrayList<String>
    private lateinit var deaths : ArrayList<String>
    private lateinit var recovered : ArrayList<String>
    private lateinit var abbrev :ArrayList<String>
    private lateinit var totalCases : TextView
    private lateinit var totalDeaths : TextView
    private lateinit var totalRecovered : TextView
    private lateinit var queue:RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ls = findViewById<ListView>(R.id.ls)

        states = arrayListOf()
        cases = arrayListOf()
        deaths = arrayListOf()
        recovered = arrayListOf()
        abbrev = arrayListOf()
        totalCases = findViewById(R.id.totalConf)
        totalDeaths = findViewById(R.id.totalDec)
        totalRecovered = findViewById(R.id.totalRec)
        getData() // calling api

    }
        private fun getData(){
            // Instantiate the RequestQueue.
            val stateAbv = mutableMapOf(
                "AN" to "Andaman and Nicobar Islands",
                "AP" to "Andhra Pradesh",
                "AR" to "Arunachal Pradesh",
                "AS" to "Assam",
                "BR" to "Bihar",
                "CH" to "Chandigarh",
                "CT" to "Chhattisgarh",
                "DL" to "Delhi",
                "DN" to "Dadra and Nagar Haveli",
                "GA" to "Goa",
                "GJ" to "Gujarat",
                "HP" to "Himachal Pradesh",
                "HR" to "Haryana",
                "JH" to "Jharkhand",
                "JK" to "Jammu and Kashmir",
                "KA" to "Karnataka",
                "KL" to "Kerala",
                "LA" to "Ladakh",
                "LD" to "Lakshadweep",
                "MH" to "Maharashtra",
                "ML" to "Meghalaya",
                "MN" to "Manipur",
                "MP" to "Madhya Pradesh",
                "MZ" to "Mizoram",
                "NL" to "Nagaland",
                "OR" to "Odisha",
                "PB" to "Punjab",
                "PY" to "Pondicherry",
                "RJ" to "Rajasthan",
                "SK" to "Sikkim",
                "TG" to "Telangana",
                "TN" to "Tamil Nadu",
                "TR" to "Tripura",
                "UP" to "Uttar Pradesh",
                "UT" to "Uttarakhand",
                "WB" to "West Bengal"
            )
             queue = Volley.newRequestQueue(this)
            val url = "https://data.covid19india.org/v4/min/data.min.json"
            // Request a string response from the provided URL.
            val sr = StringRequest(Request.Method.GET, url, Response.Listener { response ->
                val jsonObj = JSONObject(response)
                   // val data: JSONObject = jsonObj.getJSONObject("AN")
                    val keys: Iterator<*> = jsonObj.keys()

                    while (keys.hasNext()) {

                        // loop to get the dynamic key
                        val state = keys.next() as String

                        val confirmed = jsonObj.getJSONObject(state).getJSONObject("total").getString("confirmed") as String
                        //cases  updated
                        val deceased = jsonObj.getJSONObject(state).getJSONObject("total").getString("deceased") as String
                        //no. of deaths
                        val rec = jsonObj.getJSONObject(state).getJSONObject("total").getString("recovered") as String
                         // recovered patients
                        if(state == "TT")
                        {
                            totalCases.text = confirmed
                            totalDeaths.text = deceased
                            totalRecovered.text = rec
                        }
                        else {
                            states.add(stateAbv[state].toString())
                            abbrev.add(state)
                            cases.add(confirmed)
                            recovered.add(rec)
                            deaths.add(deceased)
                        }
                    }
                inflate()
                },
                { getString(R.string.apiFailed) })

// Add the request to the RequestQueue.
            queue.add(sr)

        }
    private fun inflate(){
      //  Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()

        val myListAdapter = MyListAdapter(this,states, cases, deaths, recovered)
        ls.adapter = myListAdapter

        ls.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = abbrev[position]
            val intent = Intent(this, Details::class.java).apply {
                putExtra("state",itemAtPos)
            }
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        queue.cancelAll(RequestFilter { true })
        super.onBackPressed()
    }
    }