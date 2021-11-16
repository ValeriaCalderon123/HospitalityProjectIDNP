package com.example.hospitalityproject.adapters

import com.google.firebase.firestore.GeoPoint

data class Hospital(
    var id: String ?= null,
    var name: String ?= null,
    var address: String ?= null,
    var phone: String ?= null,
    var status: Boolean ?= null,
    var ubication: GeoPoint ?= null
)

/*

data class Hospital(
    val _id: String = "",
    val _name: String = "",
    val _address: String = "",
    val _phone: String = "",
    val _status: Boolean = false,
    val _geoPoint: GeoPoint = GeoPoint(0.0, 0.0)
) {

    private var id : String = _id
    private var name : String = _name
    private var address: String = _address
    private var phone: String = _address
    private var status: Boolean = _status
    private var geopoint : GeoPoint = _geoPoint

    fun setId(_id: String){
        this.id = _id
    }
    fun getId(): String{
        return this.id
    }
    fun getName(): String{
        return this.name
    }

    fun getAddress(): String{
        return this.address
    }

    fun getPhone(): String{
        return this.phone
    }

    fun getStatus(): Boolean{
        return this.status
    }

    fun getGeopoint(): GeoPoint {
        return this.geopoint
    }

    override fun toString(): String {
        return "Hospital(id='$id', name='$name', address='$address', status=$status, geopoint=$geopoint)"
    }

}

*/