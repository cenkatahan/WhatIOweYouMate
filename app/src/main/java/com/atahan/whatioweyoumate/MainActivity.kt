package com.atahan.whatioweyoumate

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.databinding.ActivityMainBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogAddDebtBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogCreateGroupBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.interfaces.ILongClick
import com.atahan.whatioweyoumate.utils.SwipeToDelete
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), ILongClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: LayoutDialogCreateGroupBinding
    private lateinit var bindingRemoveDialog: LayoutDialogRemoveBinding
    private lateinit var friends: ArrayList<Friend>
    private var friendAdapter: FriendAdapter? = null
    private var totalPayment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        friends = ArrayList()
        setListeners()
        setAdapter()
    }

    override fun edit(position: Int) {
        openEditDialog(position)
    }

    override fun updateDebt(position: Int) {
        openDebtUpdateDialog()
    }

    //TODO dialogs should be a class
    private fun openEditDialog(position: Int) {
        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        bindingDialog.etName.text = Editable.Factory.getInstance().newEditable(friends[position].name)
        bindingDialog.etPayment.text = Editable.Factory.getInstance().newEditable(friends[position].payment.toString())
        val paymentBeforeChange = friends[position].payment


        with(bindingDialog) {
            btnConfirm.setOnClickListener {
                if (checkDialogEmptyFields()) {
                    return@setOnClickListener
                }
                totalPayment -= paymentBeforeChange
                val payment = etPayment.text?.toString()?.toInt()
                val name = etName.text?.toString()

                increasePayment(payment!!)
                friends[position] = Friend(name!!, payment)

                if (friends.size >= SIZE_TWO) {
                    binding.btnCalculate.isEnabled = true
                }

                friendAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }

            btnDismiss.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.apply {
            setContentView(bindingDialog.root)
            show()
        }
    }

    private fun setListeners() {
        binding.fabCreate.setOnClickListener {
            openAddDialog()
        }

        binding.btnRemove.setOnClickListener {
            openRemoveDialog()
        }

        binding.btnCalculate.setOnClickListener {
            calculateDebts()
        }
    }

    private fun openDebtUpdateDialog() {
        val bindingDebt = LayoutDialogAddDebtBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        dialog.apply {
            setContentView(bindingDebt.root)
            show()
        }
    }

    private fun setAdapter() {
        friendAdapter = FriendAdapter(friends, this)
        with(binding.recyclerview) {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = friendAdapter
        }

        friends.forEach {
            totalPayment += it.payment
        }

        binding.tvTotalPayment.text = "Total Payment: $totalPayment"

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedFriend: Friend =
                    friends[position]
                friends.removeAt(position)
                friendAdapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(binding.recyclerview, "Deleted " + deletedFriend.name, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo"
                    ) {
                        friends.add(position, deletedFriend)
                        friendAdapter?.notifyItemInserted(position)
                        totalPayment += deletedFriend.payment
                        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
                    }.show()

                totalPayment -= deletedFriend.payment
                binding.tvTotalPayment.text = "Total Payment: $totalPayment"
            }
        }).attachToRecyclerView(binding.recyclerview)
    }

    private fun openAddDialog() {
        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        with(bindingDialog) {
            btnConfirm.setOnClickListener {
                if (checkDialogEmptyFields()) {
                    return@setOnClickListener
                }

                val payment = etPayment.text?.toString()?.toInt()
                val name = etName.text?.toString()

                increasePayment(payment!!)
                friends.add(Friend(name!!, payment))

                if (friends.size >= 2) {
                    binding.btnCalculate.isEnabled = true
                }

                dialog.dismiss()
            }

            btnDismiss.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.apply {
            setContentView(bindingDialog.root)
            show()
        }
    }

    private fun increasePayment(payment: Int) {
        totalPayment += payment
        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
    }

    private fun checkDialogEmptyFields(): Boolean {
        return bindingDialog.etName.text.toString() == "" || bindingDialog.etPayment.text.toString() == ""
    }

    private fun openRemoveDialog() {
        if (friends.size <= SIZE_ZERO) {
            Toast.makeText(this, "List is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(this)

        bindingRemoveDialog.btnRemove.setOnClickListener {
            binding.btnCalculate.isEnabled = false
            clearFriends()
            removeDialog.dismiss()
        }

        bindingRemoveDialog.btnCancel.setOnClickListener {
            removeDialog.dismiss()
        }

        removeDialog.apply {
            setContentView(bindingRemoveDialog.root)
            show()
        }

    }

    private fun clearFriends() {
        if (friends.size > SIZE_ZERO) {
            friends.clear()
            totalPayment = SIZE_ZERO
        }
        with(binding){
            recyclerview.adapter = FriendAdapter(friends, this@MainActivity)
            tvTotalPayment.text = "Total Payment: 0"
            recyclerview.visibility = View.VISIBLE
            tvBill.visibility = View.GONE
        }
    }

    private fun calculateDebts() {
        with(binding){
            recyclerview.visibility = View.GONE
            tvBill.visibility = View.VISIBLE
        }

        var bill = "================BILL================\n"
        friends.forEach { friend ->
            val debtPerEach = friend.payment / friends.size
            bill += "->Everyone should pay $debtPerEach to ${friend.name}\n"
        }
        binding.tvBill.text = bill
    }

    companion object {
        private const val SIZE_ZERO = 0
        private const val SIZE_TWO = 2
    }
}