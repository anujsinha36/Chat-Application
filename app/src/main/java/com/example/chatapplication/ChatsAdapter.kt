package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatsAdapter(
    private val context: Context, private val chatList: MutableList<Users>
) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    class ChatsViewHolder (val view: View): RecyclerView.ViewHolder(view){
        val chatImg : ImageView = view.findViewById(R.id.chat_img)
        val userName : TextView = view.findViewById(R.id.chat_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_chats, parent, false)
        return ChatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val currentChat = chatList[position]
        holder.userName.text = currentChat.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatScreen::class.java)

            intent.putExtra("name", currentChat.name)
            intent.putExtra("uID", currentChat.uid)
            context.startActivity(intent)
        }
    }
}