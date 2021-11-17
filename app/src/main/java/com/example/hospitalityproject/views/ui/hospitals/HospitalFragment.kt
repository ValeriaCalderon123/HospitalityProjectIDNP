package com.example.hospitalityproject.views.ui.hospitals

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hospitalityproject.R
import com.example.hospitalityproject.adapters.Hospital
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_hospital.view.*

class HospitalFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hospital, container, false)
        val bundle = this.arguments

        val id = bundle?.getString("id")

//        if(bundle != null)
//            view.textViewHospitalName.text = id

        var hospital = Hospital(null, null, null, null, null, null)

        db.collection("hospitals")
            .document("$id")
            .get()
            .addOnSuccessListener { document->
                if (document != null) {
                    Log.d("Test003", "DocumentSnapshot data: ${document.data}")
                    hospital = Hospital(
                        document.id,
                        document.data?.get("name") as String,
                        document.data!!["address"] as String,
                        document.data!!["phone"] as String,
                        document.data!!["status"] as Boolean,
                        document.data!!["ubication"] as GeoPoint
                    )
                    Log.d("Test003", hospital.toString())
                }
                else {
                    Log.d("Test003", "No se encontro el id")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test003", "Fallo con ", exception)
            }

        Handler().postDelayed(
            {
                view.textViewHospitalName.text = hospital.name
                view.textViewHospitalAddress.text = hospital.address
                view.textViewPhone.text = hospital.phone
                view.textViewUbication.text = hospital.ubication.toString()

            },
            700
        )

        return view
    }

}