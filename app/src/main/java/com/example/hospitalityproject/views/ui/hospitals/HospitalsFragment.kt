package com.example.hospitalityproject.views.ui.hospitals

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.MainActivity
import com.example.hospitalityproject.R
import com.example.hospitalityproject.adapters.FirestoreAdapterHospitals
import com.example.hospitalityproject.interactors.HospitalsInteractor
import com.example.hospitalityproject.interfaces.HospitalsView
import com.example.hospitalityproject.presenter.HospitalsPresenter
import com.example.hospitalityproject.views.MenuActivity

class HospitalsFragment : Fragment(), HospitalsView{

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirestoreAdapterHospitals
    private var mPresenter = HospitalsPresenter(this, HospitalsInteractor())


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hospitals, container, false)

        mRecyclerView = view.findViewById(R.id.recyclerViewHospitals) as RecyclerView

        presenterFun(container!!.context, mRecyclerView)

        return view
    }

    private fun presenterFun(_context: Context, _mRecyclerView: RecyclerView){
        mPresenter.presenterFun(requireContext(), _mRecyclerView)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.presenterStart()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.presenterStop()
    }

    override fun onItemClicked(id: String) {
        Toast.makeText(this.context, "$id", Toast.LENGTH_SHORT).show()

        /*
        val appCompatActivity = this.context as AppCompatActivity
        appCompatActivity.supportFragmentManager.
        beginTransaction()
            .replace(R.id.nav_host_fragment_container, HospitalFragment())
            .addToBackStack(null)
            .commit()
        */
        (activity as MenuActivity).replaceFragment2(HospitalFragment(), id)
    }


    override fun show() {
        Toast.makeText(this.context, "Hospitales disponibles", Toast.LENGTH_SHORT).show()
    }



}

/*
class HospitalsFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirestoreAdapterHospitals
    private lateinit var db: FirebaseFirestore
    private lateinit var hospitalsArrayList: ArrayList<Hospital>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hospitals, container, false)

        mRecyclerView = view.findViewById(R.id.recyclerViewHospitals) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(container!!.context)

        db = FirebaseFirestore.getInstance()

        val query = db.collection("hospitals").whereEqualTo("status", true)

        val fireStoreRecyclerOptions = FirestoreRecyclerOptions
                                                            .Builder<Hospital>()
                                                            .setQuery(query, Hospital::class.java).build()

        mAdapter = FirestoreAdapterHospitals(fireStoreRecyclerOptions)
        mAdapter.notifyDataSetChanged()
        mRecyclerView.adapter = mAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}
* */
/*
package com.example.hospitalityproject.views.ui.hospitals

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.R
import com.example.hospitalityproject.adapters.AdapterHospitals
import com.example.hospitalityproject.model.Hospital
import com.google.firebase.firestore.*

class HospitalsFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterHospitals
    private lateinit var db: FirebaseFirestore
    private lateinit var hospitalsArrayList: ArrayList<Hospital>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hospitals, container, false)

        mRecyclerView = view.findViewById(R.id.recyclerViewHospitals) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(container!!.context)

        hospitalsArrayList = arrayListOf()


        mAdapter = AdapterHospitals()
        mAdapter.AdapterHospitals(hospitalsArrayList, container.context)

        mRecyclerView.adapter = mAdapter

        eventChangeListener()

        return view
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("hospitals").whereEqualTo("status", true)
                .addSnapshotListener(object: EventListener<QuerySnapshot>{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null){
                            Log.e("Firestore",error.message.toString())
                            return
                        }
                        for(dc: DocumentChange in value?.documentChanges!!){
                            if(dc.type == DocumentChange.Type.ADDED){
                                val hospital = dc.document.toObject(Hospital::class.java)
                                hospital.setId(dc.document.id)
                                hospitalsArrayList.add(hospital)
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                })
    }

}

*
* */