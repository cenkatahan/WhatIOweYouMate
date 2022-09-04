package com.atahan.whatioweyoumate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.model.Friend
import java.util.ArrayList

class FriendAdapter(private val friends: ArrayList<Friend>) :
    RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvItemName)
        val payment: TextView = itemView.findViewById(R.id.tvItemPayment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_friend, parent, false)
        return FriendHolder(view)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        with(holder) {
            name.text = friends[position].name
            payment.text = friends[position].payment.toString()
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }
}