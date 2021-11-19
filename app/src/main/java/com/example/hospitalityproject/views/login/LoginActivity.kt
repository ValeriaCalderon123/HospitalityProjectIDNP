package com.example.hospitalityproject.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import androidx.core.view.size
import com.example.hospitalityproject.R
import com.example.hospitalityproject.model.Initialization.Companion.pref
import com.example.hospitalityproject.views.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

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
        title="Log in"
        buttonLogin.setOnClickListener {
            validate() //Validar campos
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
        builder.setMessage("Se ha producido un error de autenticacion de usuario");
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //LLAMA MÉTODOS DE VALIDACIÓN
    private fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())
        if(false in result){
            return
        }
    }
    //VALIDAR EMAIL
    private fun validateEmail():Boolean{
        val email = editTextEmail.editText?.text.toString()
        return if (editTextEmail.editText?.text.toString().isEmpty()){
            editTextEmail.error = "Este campo no puede ser vacío"
            false
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.error = "Ingrese un correo válido"
            false
        }else {
            editTextEmail.error = null
            true
        }

    }
    //VALIDAR CONTRASEÑA
    private fun validatePassword():Boolean{
        val password = editTextPassword.editText?.text.toString()
        val passwordRegex = Pattern.compile(
            "^"+
                    "(?=.*[0-9])"+          //Al menos un dígito
                    "(?=.*[a-z])"+          //Al menos un caracter en minuscula
                    "(?=.*[A-Z])"+          //Al menos un caracter en mayuscula
                    "(?=.*[@#$%^&+=])"+     //Al menos un caracter especial
                    "(?=\\S+$)"+            //Sin espacios
                    ".{4,}"+                //al menos 4 caracteres
                    "$"
        )
        return if (editTextPassword.editText?.text.toString().isEmpty()){
            editTextPassword.error = "Este campo no puede ser vacío"
            false
        }else if(!passwordRegex.matcher(password).matches()){
            /*Para registrar
            if(editTextPassword.size<5){
                editTextPassword.error = "Ingrese una contraseña más larga"
            }else{
                editTextPassword.error = "Ingrese otra contraseña"
            }*/
            editTextPassword.error = "Contraseña incorrecta"
            false
        }else {
            editTextPassword.error = null
            true
        }
    }
}