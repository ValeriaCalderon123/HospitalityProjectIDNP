package com.example.hospitalityproject.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.R
import com.google.firebase.firestore.DocumentSnapshot
/*
class AdapterHospitals: RecyclerView.Adapter<AdapterHospitals.ViewHolder>(){

    var hospitals: MutableList<Hospital> = ArrayList()
    lateinit var context: Context

    fun AdapterHospitals (_hospitals: MutableList<Hospital>, _context: Context){
        this.hospitals = _hospitals
        this.context = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_hospitales,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemTitle.text = hospitals[position].getName()
        holder.itemId.text = hospitals[position].getId()
        holder.itemPhone.text = hospitals[position].getPhone()
        holder.itemAddress.text = hospitals[position].getAddress()

        holder.buttonGps.setOnClickListener{
            gpsEvent(hospitals[position], it)
        }
    }

    private fun gpsEvent(_hospital: Hospital, it: View) {
        /*
        val bundle = Bundle()
        bundle.putString("id", _hospital.getId())
        bundle.putString("nombre", _hospital.getName())
        bundle.putString("direccion",_hospital.getAddress())
        bundle.putString("latitud",_hospital.getLatitude().toString())
        bundle.putString("longitud",_hospital.getLongitude().toString())
        bundle.putString("descripcion",_hospital.getDescription().toString())

        val editFragment = EditFragment()
        editFragment.arguments = bundle

        val appCompatActivity = it.context as AppCompatActivity
        appCompatActivity.supportFragmentManager.
        beginTransaction()
            .replace(R.id.fragment_container, editFragment)
            .addToBackStack(null)
            .commit()

       */
    }
    /*
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteEvent(_hospital: Hospital){
//        val db = DB_Helper(context)
//        db.deleteItem(_hospital.getId())
//        removeItem(_hospital)
    }

    private fun removeItem(hospital: Hospital) {
        val currPosition: Int = hospitals.indexOf(hospital)
        hospitals.remove(hospital)
        notifyItemRemoved(currPosition)
    }
    */

    override fun getItemCount(): Int {
        return hospitals.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView
        var itemId: TextView
        var itemPhone: TextView
        var itemAddress: TextView

        var buttonGps: ImageButton

        init {
            itemTitle = itemView.findViewById(R.id.hospital_name);
            itemId = itemView.findViewById(R.id.hospital_id);
            itemPhone = itemView.findViewById(R.id.hospital_phone);
            itemAddress = itemView.findViewById(R.id.hospital_address);
            buttonGps = itemView.findViewById(R.id.card_view_hospital_buttonGps)
        }
    }

}
*/
