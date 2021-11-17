package com.example.hospitalityproject.presenter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalityproject.interactors.HospitalsInteractor
import com.example.hospitalityproject.interfaces.HospitalsView

class HospitalsPresenter(var hospitalsView: HospitalsView?, val hospitalsInteractor: HospitalsInteractor): HospitalsInteractor.onHospitalsFinishedListener{

    fun presenterFun(_context: Context, _mRecyclerView: RecyclerView){
        hospitalsInteractor.setup(_context, _mRecyclerView, this)
    }

    fun presenterStart(){
        hospitalsInteractor.start()
    }

    fun presenterStop(){
        hospitalsInteractor.stop()
    }

    override fun onItemClicked(id: String) {
        hospitalsView?.onItemClicked(id)
    }

    override fun onSucess() {
        hospitalsView?.show()
    }

}