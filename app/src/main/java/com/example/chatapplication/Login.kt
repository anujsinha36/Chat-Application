package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var loginTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        loginTextView = findViewById(R.id.txt1)
        emailEditText = findViewById(R.id.edt_login)
        passwordEditText = findViewById(R.id.edt_password)
        loginButton = findViewById(R.id.btn_login)
        signUpTextView = findViewById(R.id.signUpText)
        mauth = FirebaseAuth.getInstance()

       if (mauth.currentUser != null){
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
           finish()
       }


        loginButton.setOnClickListener {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.trim().equals("")){
                Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (password.trim().equals("")){
                Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                userLogin(email, password)
            }

        }

        signUpTextView.setOnClickListener {
            val intent1 = Intent(this, SignUp::class.java)
            startActivity(intent1)
        }

    }

    private fun userLogin(email: String, password: String) {
        mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, login to main page
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                // If sign in fails, display a message to the user.
               Toast.makeText(this, "No user registered", Toast.LENGTH_SHORT).show()
            }

        }
    }
}