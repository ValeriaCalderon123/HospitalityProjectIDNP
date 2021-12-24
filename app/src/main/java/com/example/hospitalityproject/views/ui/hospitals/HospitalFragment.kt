package com.example.hospitalityproject.views.ui.hospitals

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.Hospital
import com.example.hospitalityproject.model.Initialization.Companion.pref
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_hospital.*
import kotlinx.android.synthetic.main.fragment_hospital.view.*
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri

import java.io.ByteArrayOutputStream
import java.io.File


class HospitalFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var spnCategoriasPorHospital: Spinner
    private lateinit var edtSeleccFechaReserva: EditText
    private lateinit var inpSeleccHoraReserva: TimePicker
    private lateinit var edtCodigoPagoReserva: EditText
    private lateinit var btnReservarCita: Button
    private lateinit var btnCargaImagenH: Button
    private val ALL_PERMISSIONS_RESULT = 107
    private val IMAGE_RESULT = 200
    private val REQUEST_IMAGE_CAPTURE = 12345

    var mBitmap: Bitmap? = null
    val permission = 1

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
        btnCargaImagenH = view.findViewById(R.id.btnCargaImagen)
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

        btnCargaImagenH.setOnClickListener{
           selectImage()
        }

        btnReservarCita.setOnClickListener {
            var isExist=0;
            db.collection("citas")
                .whereEqualTo("fecha", edtSeleccFechaReserva.text.toString())
                .whereEqualTo("hora", inpSeleccHoraReserva.hour.toString() + ":" + inpSeleccHoraReserva.minute.toString())
                .whereEqualTo("especialidad", spnCategoriasPorHospital.selectedItem.toString())
                .get()
                .addOnSuccessListener {documents->
                    var existDoc = documents.documents
                    if(existDoc.isEmpty()){
                        db.collection("citas").add(
                            hashMapOf("codigoPago" to edtCodigoPagoReserva.text.toString(),
                                "especialidad" to spnCategoriasPorHospital.selectedItem.toString(),
                                "fecha" to edtSeleccFechaReserva.text.toString(),
                                "hora" to inpSeleccHoraReserva.hour.toString() + ":" + inpSeleccHoraReserva.minute.toString(),
                                "user" to pref.getEmail())
                        )
                        val toast1 = Toast.makeText(
                            this.context,
                            "Reserva realizada exitósamente!", Toast.LENGTH_LONG
                        )
                        toast1.show()
                    }
                    else{
                        val toast1 = Toast.makeText(
                            this.context,
                            "Ya existe recerva para esta fecha", Toast.LENGTH_LONG
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

    private fun selectImage() {
        pickImageFromGallery()

    }
    fun hasPermissionInManifest(context: Context, permissionName: String): Boolean {
        val packageName = context.packageName
        try {
            val packageInfo = context.packageManager
                .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val declaredPermisisons = packageInfo.requestedPermissions
            if (declaredPermisisons != null && declaredPermisisons.size > 0) {
                for (p in declaredPermisisons) {
                    if (p == permissionName) {
                        return true
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }

        return false
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("User")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        Log.d("Mensaje", "Se subió correctamente")
                    }
                }
            }
        }
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{ _, year, month, day ->
            val selectDate = day.toString() + "/" + (month + 1) + "/" + year
            edtSeleccFechaReserva.setText(selectDate)
        })
        fragmentManager?.let { newFragment.show(it, "datePicker") }
    }

}