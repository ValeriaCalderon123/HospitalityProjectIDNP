package com.example.hospitalityproject.interfaces

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.adapters.FirestoreAdapterHospitals

interface HospitalsView {
    fun onItemClicked(id: String)
    fun show()

}