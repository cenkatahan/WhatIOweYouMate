package com.atahan.whatioweyoumate

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atahan.whatioweyoumate.databinding.ActivityMainBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogCreateGroupBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.model.Friend


class MainActivity : AppCompatActivity() {
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

    private fun setListeners() {
        binding.btnCreate.setOnClickListener {
            openAddDialog()
        }

        binding.fabRemove.setOnClickListener {
            openRemoveDialog()
        }

        binding.btnCalculate.setOnClickListener {
            calculateDebts()
        }
    }

    private fun setAdapter() {
        friendAdapter = FriendAdapter(friends)
        with(binding.recyclerview) {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = friendAdapter
        }

        friends.forEach {
            totalPayment += it.payment
        }

        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
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
        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(this)

        bindingRemoveDialog.btnRemove.setOnClickListener {
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
        if (friends.size > 0) {
            friends.clear()
            totalPayment = 0
        }
        with(binding){
            recyclerview.adapter = FriendAdapter(friends)
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
}