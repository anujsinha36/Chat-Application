package com.example.chatapplication

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

public open class MenuItems : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mAuth = FirebaseAuth.getInstance()
        menuInflater.inflate(R.menu.homescreenmenu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.logout_menu ->{
                mAuth.signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }

        }
        return true
    }
}