package com.example.hospitalityproject.interactors

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.MainActivity
import com.example.hospitalityproject.adapters.FirestoreAdapterHospitals
import com.example.hospitalityproject.adapters.Hospital
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class HospitalsInteractor: FirestoreAdapterHospitals.OnItemClickListener {

    private lateinit var mAdapter: FirestoreAdapterHospitals
    private lateinit var db: FirebaseFirestore
    private lateinit var pos: String
    private lateinit var listener: onHospitalsFinishedListener

    interface onHospitalsFinishedListener{
        fun onItemClicked(id: String)
        fun onSucess()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setup(_context: Context, _mRecyclerView: RecyclerView, _listener: onHospitalsFinishedListener){
        _mRecyclerView.layoutManager = LinearLayoutManager(_context)

        db = FirebaseFirestore.getInstance()

        val query = db.collection("hospitals").whereEqualTo("status", true)

        val fireStoreRecyclerOptions = FirestoreRecyclerOptions
            .Builder<Hospital>()
            .setQuery(query, Hospital::class.java).build()

        mAdapter = FirestoreAdapterHospitals(fireStoreRecyclerOptions, this)
        mAdapter.notifyDataSetChanged()
        _mRecyclerView.adapter = mAdapter

        listener = _listener
        listener.onSucess()
    }

    fun start(){
        mAdapter.startListening()
    }

    fun stop(){
        mAdapter.stopListening()
    }

    override fun onItemClick(id: String) {
        Log.d("Test002", id)
        pos = id
        listener.onItemClicked(id)
    }

}
