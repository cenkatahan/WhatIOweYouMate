package com.atahan.whatioweyoumate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.interfaces.ILongClick
import javax.inject.Inject

class FriendAdapter @Inject constructor() :
    RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    private lateinit var onILongClick: ILongClick

    private val differCallback = object : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_friend, parent, false)
        return FriendHolder(view)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        with(holder) {
            val currentFriend = differ.currentList[position]

            name.text = currentFriend.name
            payment.text = currentFriend.payment.toString()
            payment.setOnLongClickListener {
                onILongClick.updateDebt(position)
                true
            }

            itemView.setOnLongClickListener {
                onILongClick.edit(position)
                true
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setLongClickListener(longClickListener: ILongClick) {
        onILongClick = longClickListener
    }

    class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvItemName)
        val payment: TextView = itemView.findViewById(R.id.tvItemPayment)
    }
}