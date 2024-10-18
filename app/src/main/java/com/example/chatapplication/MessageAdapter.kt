package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.ChatsAdapter.ChatsViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageAdapter(
    private val context: Context,
    private val messageList: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val sentItem = 1
    val receivedItem = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            //inflate sent UI
            val view = LayoutInflater.from(context).inflate(R.layout.sent_msg, parent, false)
            return SentMessageViewHolder(view)
        }
        else{
            //inflate receive UI
            val itemView = LayoutInflater.from(context).inflate(R.layout.recieve_msg, parent, false)
            return ReceiveMessageViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val selectedUserChat = messageList[position]

        if (holder.javaClass == SentMessageViewHolder::class.java){
            val viewHolder = holder as SentMessageViewHolder
            holder.sentMessage.text = selectedUserChat.message
        }
        else{
            val viewHolder = holder as ReceiveMessageViewHolder
            holder.receiveMessage.text = selectedUserChat.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val selectedUserChat = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(selectedUserChat.senderID)){
            return 1
        }
        else{
            return 2
        }
    }

    class SentMessageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val sentMessage : TextView = view.findViewById(R.id.txt_sent_msg)

    }
    class ReceiveMessageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val receiveMessage : TextView = view.findViewById(R.id.txt_received_msg)
    }
}