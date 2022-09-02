package com.atahan.whatioweyoumate

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.atahan.whatioweyoumate.databinding.ActivityMainBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogCreateGroupBinding
import com.atahan.whatioweyoumate.model.Person


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //    private var groupSize = 0
    private lateinit var friendGroup: ArrayList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        friendGroup = ArrayList()
        binding.btnCreate.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        val bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)

        var name = ""
        var payment = 0

        bindingDialog.btnConfirm.setOnClickListener {
            if (bindingDialog.etName.text.toString() == "") {
                return@setOnClickListener
            }

            if (bindingDialog.etPayment.text.toString() == "") {
                payment = 0
                friendGroup.add(
                    Person(
                        bindingDialog.etName.text.toString(),
                        payment
                    )
                )
                dialog.dismiss()
            } else {
                bindingDialog.etPayment.text?.let {
                    payment = it.toString().toInt()
                }
            }

            bindingDialog.etName.text?.let {
                name = it.toString()
            }


            friendGroup.add(Person(name, payment))
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
}