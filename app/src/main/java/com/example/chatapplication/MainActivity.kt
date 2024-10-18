package com.example.chatapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : MenuItems() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var rv1 : RecyclerView
    private lateinit var chatsAdapter: ChatsAdapter
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Online Users"


        var chatList = mutableListOf<Users>()
        rv1 = findViewById(R.id.rv1)
        chatsAdapter = ChatsAdapter(this, chatList)
        rv1.layoutManager = LinearLayoutManager(this)
        rv1.adapter = chatsAdapter


        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for(dataSnapshot in snapshot.children){
                    val currentUser = dataSnapshot.getValue(Users::class.java)

                    if (mAuth.currentUser?.uid != currentUser?.uid){
                        chatList.add(currentUser!!)
                    }
                }
                chatsAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





}


    }