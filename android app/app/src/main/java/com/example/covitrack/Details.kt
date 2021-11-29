package com.example.covitrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Details : AppCompatActivity() {
    private lateinit var stateDet : TextView
    private lateinit var population : TextView
    private lateinit var totalCases : TextView
    private lateinit var totalDeaths : TextView
    private lateinit var totalRecovered : TextView
    private lateinit var tested :TextView
    private lateinit var first :TextView
    private lateinit var second :TextView
    private lateinit var list:ListView
    private lateinit var districts:ArrayList<String>
    private lateinit var confCases:ArrayList<String>
    private lateinit var confDeaths:ArrayList<String>
    private lateinit var confRec:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        stateDet = findViewById(R.id.stateDet)
        population = findViewById(R.id.population)
        totalCases = findViewById(R.id.confirmedCases)
        totalDeaths = findViewById(R.id.confirmedDeaths)
        totalRecovered = findViewById(R.id.recoveredCases)
        tested = findViewById(R.id.tested)
        first = findViewById(R.id.dosage1)
        second = findViewById(R.id.dosage2)
        list = findViewById(R.id.dls)
        districts = arrayListOf()
        confCases = arrayListOf()
        confDeaths = arrayListOf()
        confRec = arrayListOf()
        val state = intent.getStringExtra("state")
        getData(state)
//        val myListAdapter = MyListAdapter(this,districts, confCases, confDeaths, confRec)
//        list.adapter = myListAdapter
    }
    private fun getData(state: String?)
    {
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
        stateDet.text = stateAbv[state]
        val queue = Volley.newRequestQueue(this)
        val url = "https://data.covid19india.org/v4/min/data.min.json"
        // Request a string response from the provided URL.
        val sr = StringRequest(
            Request.Method.GET, url, Response.Listener { response ->
            val jsonObj = JSONObject(response)


                val pop = jsonObj.getJSONObject(state!!).getJSONObject("meta").getString("population") as String
                population.text = pop
                val test = jsonObj.getJSONObject(state).getJSONObject("total").getString("tested") as String
                tested.text = test
                val confirmed = jsonObj.getJSONObject(state).getJSONObject("total").getString("confirmed") as String
                totalCases.text = confirmed
                //cases  updated
                val deceased = jsonObj.getJSONObject(state).getJSONObject("total").getString("deceased") as String
                totalDeaths.text = deceased
                //no. of deaths
                val rec = jsonObj.getJSONObject(state).getJSONObject("total").getString("recovered") as String
                totalRecovered.text = rec
                val vacc1 = jsonObj.getJSONObject(state).getJSONObject("total").getString("vaccinated1") as String
                first.text = vacc1
                val vacc2 = jsonObj.getJSONObject(state).getJSONObject("total").getString("vaccinated2") as String
                second.text = vacc2

                val keys: Iterator<*> = jsonObj.getJSONObject(state).getJSONObject("districts").keys()
//                while (keys.hasNext()){
//                    Toast.makeText(this, keys.next() as String, Toast.LENGTH_SHORT).show()
//                }
//                while (keys.hasNext()) {
//                    // loop to get the dynamic key
//                    val district = keys.next() as String
//                   // val confirmedDist =  jsonObj.getJSONObject(state).getJSONObject("districts").getJSONObject(district).getJSONObject("total").getString("confirmed") as String
//                    //cases  updated
//
//                        val deceasedDist = jsonObj.getJSONObject(state).getJSONObject("districts").getJSONObject(district).getJSONObject("delta").getString("confirmed") as String
//                        Toast.makeText(this, deceasedDist, Toast.LENGTH_SHORT).show()
//                    //no. of deaths

                    //                    val recDist = jsonObjectDistrict.getJSONObject(district).getJSONObject("total").getString("recovered") as String
                    // recovered patients
//                        districts.add(district)
//                        confCases.add(confirmedDist)
//                        confRec.add(recDist)
//                        confDeaths.add(deceasedDist)

//                }
//

        },
            { getString(R.string.apiFailed) })

// Add the request to the RequestQueue.
        queue.add(sr)

    }
}