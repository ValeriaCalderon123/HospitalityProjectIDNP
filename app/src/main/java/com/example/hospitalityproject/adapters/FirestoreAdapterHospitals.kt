package com.example.hospitalityproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class FirestoreAdapterHospitals(options: FirestoreRecyclerOptions<Hospital>, val listener: OnItemClickListener) :
    FirestoreRecyclerAdapter<Hospital, FirestoreAdapterHospitals.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_hospitales,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, hospital: Hospital) {
        holder.itemTitle.text = hospital.name
        holder.itemPhone.text = hospital.phone
        holder.itemAddress.text = hospital.address

        val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.absoluteAdapterPosition)
        hospital.id = snapshot.id
        holder.itemId.text = hospital.id


        holder.buttonGps.setOnClickListener{
            gpsEvent(hospital, it)
        }


        /*
                holder.itemTitle.text = hospital.getName()
        holder.itemPhone.text = hospital.getPhone()
        holder.itemAddress.text = hospital.getAddress()

        val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.absoluteAdapterPosition)
        hospital.setId(snapshot.id)
        holder.itemId.text = hospital.getId()

        holder.buttonGps.setOnClickListener{
            gpsEvent(hospital, it)
        }
         */
    }

    private fun gpsEvent(_hospital: Hospital, it: android.view.View) {
//        Toast.makeText(it.context, _hospital.getGeopoint().toString() + _hospital.getStatus().toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(it.context, "Latitud: ${_hospital.ubication!!.latitude.toString()} \nLongitud: ${_hospital.ubication!!.longitude.toString()} ", Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder(itemView: android.view.View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var itemTitle: TextView = itemView.findViewById(R.id.hospital_name)
        var itemId: TextView = itemView.findViewById(R.id.hospital_id)
        var itemPhone: TextView = itemView.findViewById(R.id.hospital_phone)
        var itemAddress: TextView = itemView.findViewById(R.id.hospital_address)
        var buttonGps: ImageButton = itemView.findViewById(R.id.card_view_hospital_buttonGps)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val snapshot: DocumentSnapshot = snapshots.getSnapshot(absoluteAdapterPosition)
            Log.d("Test002", p0.toString() + snapshot.id)
            if (p0 != null) {
                listener.onItemClick(snapshot.id)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(id: String)
    }

}

/*
package com.example.hospitalityproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class FirestoreAdapterHospitals(options: FirestoreRecyclerOptions<Hospital>, val listener: OnItemClickListener) :
    FirestoreRecyclerAdapter<Hospital, FirestoreAdapterHospitals.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_hospitales,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, hospital: Hospital) {
        holder.itemTitle.text = hospital.name
        holder.itemPhone.text = hospital.phone
        holder.itemAddress.text = hospital.address

        val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.absoluteAdapterPosition)
        hospital.id = snapshot.id
        holder.itemId.text = hospital.id


        holder.buttonGps.setOnClickListener{
            gpsEvent(hospital, it)
        }

    }

    private fun gpsEvent(_hospital: Hospital, it: android.view.View) {
        Toast.makeText(it.context, "Latitud: ${_hospital.ubication!!.latitude.toString()} \nLongitud: ${_hospital.ubication!!.longitude.toString()} ", Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder(itemView: android.view.View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var itemTitle: TextView = itemView.findViewById(R.id.hospital_name)
        var itemId: TextView = itemView.findViewById(R.id.hospital_id)
        var itemPhone: TextView = itemView.findViewById(R.id.hospital_phone)
        var itemAddress: TextView = itemView.findViewById(R.id.hospital_address)
        var buttonGps: ImageButton = itemView.findViewById(R.id.card_view_hospital_buttonGps)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val snapshot: DocumentSnapshot = snapshots.getSnapshot(absoluteAdapterPosition)
            Log.d("Test002", p0.toString() + snapshot.id)
            if (p0 != null) {
                listener.onItemClick(snapshot.id)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(id: String)
    }

}
*/