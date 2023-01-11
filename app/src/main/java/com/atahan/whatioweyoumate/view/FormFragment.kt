package com.atahan.whatioweyoumate.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.databinding.FragmentFormBinding
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding

    @Inject
    lateinit var friendAdapter: FriendAdapter

    @Inject
    lateinit var repository: FriendRepository

    //TODO Implement Presenter.
//    @Inject
//    lateinit var presenter: FormPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            //presenter.method
            /*
            * Code
            * */

            //repository.save(item)
            if (checkDialogEmptyFields()) {
                return@setOnClickListener
            }
//                totalPayment -= paymentBeforeChange
            val payment = binding.etPayment.text?.toString()?.toInt()
            val name = binding.etName.text?.toString()

            increasePayment(payment!!)
            repository.update(
                Friend(
                    id = 0,
                    name = name!!,
                    payment = payment
                )
            )

            //TODO Check if this code is needed.
//            friendAdapter.apply {
//                differ.submitList(repository.getFriends())
//                    binding.recyclerview.adapter = this
//            }

            //navigate back
            //presenter.navigate() ??
            this.findNavController().navigate(R.id.action_formFragment2_to_friendListFragment)
        }

        binding.btnDismiss.setOnClickListener {
            //navigate back
            //presenter.method()
            this.findNavController().navigate(R.id.action_formFragment2_to_friendListFragment)
        }
    }

    fun addItem(position: Int) {

        //TODO position parameter should be argument. So, no need a parameter.
        when (position) {
            null -> {
                //presenter.addItem()
            }
            else -> {
                //presenter.updateItem()
            }
        }


        val currentFriend = friendAdapter.differ.currentList[position]

        binding.etName.text =
            Editable.Factory.getInstance().newEditable(currentFriend.name)
        binding.etPayment.text =
            Editable.Factory.getInstance().newEditable(currentFriend.payment.toString())
        val paymentBeforeChange = currentFriend.payment

        //TODO these listeners should not be here.
        with(binding) {
            btnConfirm.setOnClickListener {
                if (checkDialogEmptyFields()) {
                    return@setOnClickListener
                }
//                totalPayment -= paymentBeforeChange
                val payment = etPayment.text?.toString()?.toInt()
                val name = etName.text?.toString()

                increasePayment(payment!!)
                repository.update(
                    Friend(
                        id = currentFriend.id,
                        name = name!!,
                        payment = payment
                    )
                )

                friendAdapter.apply {
                    differ.submitList(repository.getFriends())
//                    binding.recyclerview.adapter = this
                }

            }

            btnDismiss.setOnClickListener {
                //navigate back
            }
        }
    }

    private fun increasePayment(payment: Int) {
//        totalPayment += payment
//        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
    }

    private fun checkDialogEmptyFields() =
        binding.etName.text.toString() == "" || binding.etPayment.text.toString() == ""
}