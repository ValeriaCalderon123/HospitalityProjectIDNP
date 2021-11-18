package com.example.hospitalityproject.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.example.hospitalityproject.R
import com.example.hospitalityproject.databinding.ActivityMainBinding
import com.example.hospitalityproject.model.Initialization.Companion.pref
import com.example.hospitalityproject.views.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_register.*

enum class ProviderType{
    BASIC
}
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        setup()
    }
    private fun setup(){
        title="Hospitality"
        buttonLogin.setOnClickListener {
            if(editTextEmail.editText?.text.toString().isNotEmpty()&&editTextPassword.editText?.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.editText?.text.toString(),
                    editTextPassword.editText?.text.toString()).addOnCompleteListener {
                    Log.d("Test004", "$it")
                    if(it.isSuccessful){
                        val toast1 = Toast.makeText(
                            applicationContext,
                            "Inicio de sesion correcto", Toast.LENGTH_SHORT
                        )

                        toast1.show()
                        pref.saveEmail(editTextEmail.editText?.text.toString())
                        val intent = Intent(this, MenuActivity::class.java).apply {
                        }
                        startActivity(intent)
                    }else{
                        showAlert();
                    }
                }
            }
        }
        buttonRegisterLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {

            }
            startActivity(intent)
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un errore de autenticacion de usuario");
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    /*
    private fun validateEmail():Boolean{
        val email = editTextEmail.editText?.text.toString()
        return  if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){

            false
        }else {
            true
        }
    }*/
}