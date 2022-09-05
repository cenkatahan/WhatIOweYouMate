package com.atahan.whatioweyoumate

import android.app.Dialog
import android.os.Bundle
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
    }

    private fun setAdapter() {
        friendAdapter = FriendAdapter(friends)
        with(binding.recyclerview) {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = friendAdapter
        }
    }

    private fun openAddDialog() {
        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        bindingDialog.btnConfirm.setOnClickListener {
            if (checkDialogEmptyFields()) {
                return@setOnClickListener
            }

            val payment = bindingDialog.etPayment.text?.toString()?.toInt()
            val name = bindingDialog.etName.text?.toString()

            friends.add(Friend(name!!, payment!!))

            if (friends.size >= 2) {
                binding.btnCalculate.isEnabled = true
            }

            dialog.dismiss()
        }

        bindingDialog.btnDismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.apply {
            setContentView(bindingDialog.root)
            show()
        }
    }

    private fun checkDialogEmptyFields(): Boolean {
        return bindingDialog.etName.text.toString() == "" || bindingDialog.etPayment.text.toString() == ""
    }

    private fun openRemoveDialog() {
        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(this)

        bindingRemoveDialog.btnRemove.setOnClickListener {
            if (friends.size > 0) {
                friends.clear()
            }
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
}