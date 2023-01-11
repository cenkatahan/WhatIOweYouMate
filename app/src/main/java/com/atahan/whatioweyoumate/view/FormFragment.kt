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
import com.atahan.whatioweyoumate.interfaces.FormContractor
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.presenter.FormPresenter
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormFragment : Fragment(), FormContractor.IView {
    private lateinit var binding: FragmentFormBinding

    @Inject
    lateinit var presenter: FormPresenter

    @Inject
    lateinit var friendAdapter: FriendAdapter

    @Inject
    lateinit var repository: FriendRepository


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

        presenter.setView(this)

//        TODO if argument not null
//        val currentFriend = friendAdapter.differ.currentList[argumentID]
//        binding.etName.text =
//            Editable.Factory.getInstance().newEditable(currentFriend.name)
//        binding.etPayment.text =
//            Editable.Factory.getInstance().newEditable(currentFriend.payment.toString())

        binding.btnConfirm.setOnClickListener {
            if (checkDialogEmptyFields()) {
                return@setOnClickListener
            }

            presenter.apply {
                addItem()
                navigate()
            }
        }

        binding.btnDismiss.setOnClickListener {
            presenter.navigate()
        }
    }

    fun addItem(position: Int) {

        //TODO position parameter should be argument. So, no need a parameter.
        //TODO this code block can be in onviewcreated and checks argument.
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

    override fun addFriend() {
        val payment = binding.etPayment.text?.toString()?.toInt()
        val name = binding.etName.text?.toString()

        increasePayment(payment!!)
        repository.add(
            Friend(
                id = 0,
                name = name!!,
                payment = payment
            )
        )
    }

    override fun updateFriendAt(id: Int) {
        val currentFriend = friendAdapter.differ.currentList[id]
        val payment = binding.etPayment.text?.toString()?.toInt()
        val name = binding.etName.text?.toString()

        increasePayment(payment!!)
        repository.update(
            Friend(
                id = currentFriend.id,
                name = name!!,
                payment = payment
            )
        )
    }

    override fun navigateBack() {
        this.findNavController().navigate(R.id.action_formFragment2_to_friendListFragment)
    }

    private fun increasePayment(payment: Int) {
//        totalPayment += payment
//        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
    }

    private fun checkDialogEmptyFields() =
        binding.etName.text.toString() == "" || binding.etPayment.text.toString() == ""

}