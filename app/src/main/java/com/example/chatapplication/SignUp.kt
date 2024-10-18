package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener{
            val name = binding.edtName.text.toString()
            val signUpEmail = binding.edtEmail.text.toString()
            val createdPassword = binding.edtCreatePassword.text.toString()

            userSignUp(name, signUpEmail,createdPassword)

        }

    }
    private fun userSignUp(name: String, email: String ,password: String){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful){
                //signup user to main_activity

                val user = Users(
                    name = name,
                    email = email,
                    uid = mAuth.uid!!
                )

                createUser(user)
                Toast.makeText(this, "New User created successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Some error occurred. Try Again", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun createUser(users: Users){
        Firebase.database.getReference("users").child(users.name).setValue(users)
    }

}