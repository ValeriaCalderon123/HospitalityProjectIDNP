package com.example.hospitalityproject.views.ui.hospitals

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.Hospital
import com.example.hospitalityproject.model.Initialization.Companion.pref
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_hospital.view.*

class HospitalFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var spnCategoriasPorHospital: Spinner
    private lateinit var edtSeleccFechaReserva: EditText
    private lateinit var inpSeleccHoraReserva: TimePicker
    private lateinit var edtCodigoPagoReserva: EditText
    private lateinit var btnReservarCita: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hospital, container, false)
        val bundle = this.arguments

        val id = bundle?.getString("id")
/*
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
        */

        spnCategoriasPorHospital = view.findViewById(R.id.spnCategoriasPorHospital)
        edtSeleccFechaReserva = view.findViewById(R.id.edtSeleccionarFechaDeReserva)
        inpSeleccHoraReserva = view.findViewById(R.id.inpSeleccionHoraDeReserva)
        edtCodigoPagoReserva = view.findViewById(R.id.edtCodigoDePagoReserva)
        btnReservarCita = view.findViewById(R.id.btnReservarCitaEnHospital)
        val listaCategorias: ArrayList<String> = ArrayList()

        db.collection("especialidad")
            .whereEqualTo("codHospital", "$id")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    listaCategorias.add(document.get("nombre").toString())
                }
                Log.e("arrayEspecialidades", listaCategorias.toString())
                spnCategoriasPorHospital.adapter = activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_spinner_dropdown_item,
                        listaCategorias
                    )
                }
            }

        edtSeleccFechaReserva.setOnClickListener{
            showDatePickerDialog()
        }

        btnReservarCita.setOnClickListener {
            var isExist=0;
            db.collection("citas")
                .whereEqualTo("fecha", edtSeleccFechaReserva.text.toString())
                .get()
                .addOnSuccessListener {documents->
                    var existDoc = documents.documents
                    if(existDoc.isEmpty()){
                        db.collection("citas").add(
                            hashMapOf("codigoPago" to edtCodigoPagoReserva.text.toString(),
                                "especialidad" to spnCategoriasPorHospital.selectedItem.toString(),
                                "fecha" to edtSeleccFechaReserva.text.toString(),
                                "user" to pref.getEmail())
                        )
                    }
                    else{
                        val toast1 = Toast.makeText(
                            this.context,
                            "Ya existe recerva para esta fecha", Toast.LENGTH_SHORT
                        )
                        toast1.show()
                    }

                }


            Log.e("InfoSpinner", spnCategoriasPorHospital.selectedItem.toString())
            Log.e("FechaEdt", edtSeleccFechaReserva.text.toString())
            Log.e("HoraReserva", inpSeleccHoraReserva.hour.toString() + ":" + inpSeleccHoraReserva.minute.toString())
            Log.e("Pago Cita Hospital", edtCodigoPagoReserva.text.toString())
        }

        return view

    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{ _, year, month, day ->
            val selectDate = day.toString() + "/" + (month + 1) + "/" + year
            edtSeleccFechaReserva.setText(selectDate)
        })
        fragmentManager?.let { newFragment.show(it, "datePicker") }
    }

}