package com.example.chatapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChatScreen : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var rvChatScreen : RecyclerView
    private lateinit var edtMessage : EditText
    private lateinit var btnSendMsg : ImageView
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var dbRef : DatabaseReference

    var senderRoom = ""
    var receiverRoom = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_screen)

        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val name = intent.getStringExtra("name")
        val receiverUID = intent.getStringExtra("uID")

        val senderUID = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUID + senderUID
        receiverRoom = senderUID + receiverUID

        supportActionBar?.title = name

        dbRef = FirebaseDatabase.getInstance().getReference()
        messageList = mutableListOf()
        edtMessage = findViewById(R.id.edt_Message)
        btnSendMsg = findViewById(R.id.btn_send_msg)
        rvChatScreen = findViewById(R.id.rv_chat_screen)
        messageAdapter = MessageAdapter(this, messageList)
        rvChatScreen.layoutManager = LinearLayoutManager(this)
        rvChatScreen.adapter = messageAdapter



        dbRef.child("messages").child(senderRoom).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               messageList.clear()
                for (dataSnapshot in snapshot.children){
                    val message = dataSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        btnSendMsg.setOnClickListener {
            var message = edtMessage.text.toString()
            var  messageObj = Message(
                message = message,
                senderID = senderUID!!
            )
            dbRef.child("messages").child(senderRoom).push().setValue(messageObj).addOnCompleteListener{
                dbRef.child("messages").child(receiverRoom).push().setValue(messageObj)
            }
            edtMessage.setText("")
        }

    }
}