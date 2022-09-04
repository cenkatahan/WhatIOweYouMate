package com.atahan.whatioweyoumate

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atahan.whatioweyoumate.databinding.ActivityMainBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogCreateGroupBinding
import com.atahan.whatioweyoumate.model.Friend


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: LayoutDialogCreateGroupBinding
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
            openDialog()
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

    private fun openDialog() {
        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        var name = ""
        var payment = 0

        bindingDialog.btnConfirm.setOnClickListener {
            if (checkDialogEmptyFields()) {
                return@setOnClickListener
            }

            bindingDialog.etPayment.text?.let {
                payment = it.toString().toInt()
            }

            bindingDialog.etName.text?.let {
                name = it.toString()
            }

            friends.add(Friend(name, payment))

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
}