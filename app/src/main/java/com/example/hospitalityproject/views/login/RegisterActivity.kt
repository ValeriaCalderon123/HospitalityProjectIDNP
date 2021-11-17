package com.example.hospitalityproject.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import com.example.hospitalityproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_login2.editTextEmail
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setup()
    }
    private fun setup(){
        title="Hospitality"
        buttonCancel.setOnClickListener{
            if(textEmailRegister.text.isNotEmpty()&&editTextPasswordRegister.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(textEmailRegister.text.toString(),
                    editTextPasswordRegister.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){

                        }else{
                            showAlert();
                        }
                }
            }
        }
        buttonRegister.setOnClickListener{
            if(textEmailRegister.text.isNotEmpty()&&editTextPasswordRegister.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(textEmailRegister.text.toString(),
                    editTextPasswordRegister.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        val toast1 = Toast.makeText(
                            applicationContext,
                            "Registrado Correctamente", Toast.LENGTH_SHORT
                        )

                        toast1.show()
                    }else{
                        showAlert();
                    }
                }
            }
            db.collection("users").document(textEmailRegister.text.toString()).set(
                hashMapOf("firstName" to textFirstNameRegister.text.toString(),
                          "lastName" to textLastNameRegister.text.toString(),
                          "address" to textAddressRegister.text.toString(),
                          "phone" to textPhoneRegister.text.toString())
            )
            onBackPressed()
        }
    }
    private fun showAlert(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de registro de usuario");
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
     private fun showHome(email:String, provider: ProviderType){
          //  val homeIntent= Intent(this,Ho)
     }
}