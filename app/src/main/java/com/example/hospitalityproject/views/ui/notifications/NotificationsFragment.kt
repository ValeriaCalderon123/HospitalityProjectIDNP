package com.example.hospitalityproject.views.ui.notifications

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.BarChart
import com.example.hospitalityproject.model.Especialidad
import com.example.hospitalityproject.model.Hospital
import com.google.firebase.firestore.FirebaseFirestore

class NotificationsFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    private lateinit var arrayIds: ArrayList<String>
    private lateinit var arrayNames: ArrayList<String>
    private lateinit var arrayCategories: ArrayList<Especialidad>
    private lateinit var hospitalsArrayList: ArrayList<Hospital>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayIds = arrayListOf()
        arrayCategories = arrayListOf()
        arrayNames = arrayListOf()
        hospitalsArrayList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        readData()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        Handler().postDelayed(
            {
                for(i in 0 until hospitalsArrayList.size){
                    arrayNames.add(hospitalsArrayList[i].name.toString())
                }
                val barChart = BarChart(container!!.context, arrayIds, arrayCategories, arrayNames)
                val relativeLayout: RelativeLayout = view.findViewById(R.id.draw)
                relativeLayout.addView(barChart)
            },
            2000
        )
        return view
    }

    private fun readData(){

        db.collection("hospitals").whereEqualTo("status", true).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    hospitalsArrayList.add(document.toObject(Hospital::class.java));
                    arrayIds.add(document.reference.id)
                }
            }

        db.collection("especialidad").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    arrayCategories.add(document.toObject(Especialidad::class.java))
                }
            }
    }

}