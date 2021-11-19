package com.example.hospitalityproject.views.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.Initialization.Companion.pref
import com.example.hospitalityproject.views.MenuActivity
import com.example.hospitalityproject.views.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data()
        /**/
    }

    private fun data(){
        db.collection("users").document(pref.getEmail()).get().addOnSuccessListener {
            textFirstNameProfile.setText(it.get("firstName") as String?)
            textLastNameProfile.setText(it.get("lastName")as String?)
            textAddressProfile.setText(it.get("address")as String?)
            textPhoneProfile.setText(it.get("phone")as String?)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_profile,container,false)
        view.buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            pref.wipe()
             val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        view.buttonSaveProfile.setOnClickListener {
            db.collection("users").document(pref.getEmail()).set(
                hashMapOf("firstName" to textFirstNameProfile.text.toString(),
                    "lastName" to textLastNameProfile.text.toString(),
                    "address" to textAddressProfile.text.toString(),
                    "phone" to textPhoneProfile.text.toString())
            )
        }

        // Inflate the layout for this fragment

        return view
    }


}