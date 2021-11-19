package com.example.hospitalityproject.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import androidx.core.view.size
import com.example.hospitalityproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_login2.editTextEmail
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setup()
    }
    private fun setup(){
        title="Registrarse"
        buttonCancel.setOnClickListener{
            onBackPressed()
        }
        buttonRegister.setOnClickListener{
            validate() //Validar campos
            if(textEmailRegister.editText?.text.toString().isNotEmpty()&&editTextPasswordRegister.editText?.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(textEmailRegister.editText?.text.toString(),
                    editTextPasswordRegister.editText?.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){

                        val toast1 = Toast.makeText(
                            applicationContext,
                            "Registrado Correctamente", Toast.LENGTH_SHORT
                        )

                        toast1.show()

                        db.collection("users").document(textEmailRegister.editText?.text.toString()).set(
                            hashMapOf("firstName" to textFirstNameRegister.editText?.text.toString(),
                                "lastName" to textLastNameRegister.editText?.text.toString(),
                                "address" to textAddressRegister.editText?.text.toString(),
                                "phone" to textPhoneRegister.editText?.text.toString())
                        )
                        onBackPressed()
                    }else{
                        showAlert();
                    }
                }
            }
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

    //LLAMA MÉTODOS DE VALIDACIÓN
    private fun validate(){
        val result = arrayOf(validateName(), validateLastName(), validateEmail(), validatePassword())
        if(false in result){
            return
        }
    }
    //VALIDAR NAME
    private fun validateName():Boolean{
        val name = textFirstNameRegister.editText?.text.toString()
        val nameRegex = Pattern.compile(
            "^"+
                    "[ÁÉÍÓÚA-Z][a-záéíóú]+(\\s+[ÁÉÍÓÚA-Z]?[a-záéíóú]+)*"+          //Nombre
                    "$"
        )
        return if (name.isEmpty()){
            textFirstNameRegister.error = "Este campo no puede ser vacío"
            false
        }else if(!nameRegex.matcher(name).matches()){
            textFirstNameRegister.error = "Caracteres no válidos"
            false
        }else {
            textFirstNameRegister.error = null
            true
        }
    }
    //VALIDAR LASTNAME
    private fun validateLastName():Boolean{
        val lastName = textLastNameRegister.editText?.text.toString()
        val lastNameRegex = Pattern.compile(
            "^"+
                    "[ÁÉÍÓÚA-Z][a-záéíóú]+(\\s+[ÁÉÍÓÚA-Z]?[a-záéíóú]+)*"+          //Nombre
                    "$"
        )
        return if (lastName.isEmpty()){
            textLastNameRegister.error = "Este campo no puede ser vacío"
            false
        }else if(!lastNameRegex.matcher(lastName).matches()){
            textLastNameRegister.error = "Caracteres no válidos"
            false
        }else {
            textLastNameRegister.error = null
            true
        }
    }
    //VALIDAR NUMBER PHONE //NECESARIO?
    private fun validatePhone():Boolean{
        val phone = textPhoneRegister.editText?.text.toString()

        return if (phone.isEmpty()){
            textPhoneRegister.error = "Este campo no puede ser vacío"
            false
        }else {
            textPhoneRegister.error = null
            true
        }
    }
    //VALIDAR EMAIL
    private fun validateEmail():Boolean{
        val email = textEmailRegister.editText?.text.toString()
        return if (email.isEmpty()){
            textEmailRegister.error = "Este campo no puede ser vacío"
            false
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            textEmailRegister.error = "Ingrese un correo válido"
            false
        }else {
            textEmailRegister.error = null
            true
        }
    }
    //VALIDAR PASSWORD
    private fun validatePassword():Boolean{
        val password = editTextPasswordRegister.editText?.text.toString()
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
        return if (password.isEmpty()){
            editTextPasswordRegister.error = "Este campo no puede ser vacío"
            false
        }else if(!passwordRegex.matcher(password).matches()){
            //Para registrar
            if(editTextPasswordRegister.size<5 ){
                editTextPasswordRegister.error = "Ingrese una contraseña más larga"
            }else{
                editTextPasswordRegister.error = "Ingrese otra contraseña"
            }
            false
        }else {
            editTextPasswordRegister.error = null
            true
        }
    }
}