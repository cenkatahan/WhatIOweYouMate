package com.atahan.whatioweyoumate.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.databinding.FragmentFormBinding
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding

    private var currentFriend: Friend? = null


    @Inject
    lateinit var repository: FriendRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        presenter.setView(this)

        currentFriend = arguments?.getParcelable("friend")

        if (currentFriend != null) {
            with(binding) {
                etName.text =
                    Editable.Factory.getInstance().newEditable(currentFriend?.name)
                etPayment.text =
                    Editable.Factory.getInstance().newEditable(currentFriend?.payment.toString())
            }
        }

        binding.btnConfirm.setOnClickListener {
            if (checkDialogEmptyFields()) {
                return@setOnClickListener
            }
//            presenter.apply {
//                confirmItem()
//                navigate()
//            }
        }

        binding.btnDismiss.setOnClickListener {
//            presenter.navigate()
        }
    }

    fun confirmFriend() {
        val payment = binding.etPayment.text?.toString()?.toInt()
        val name = binding.etName.text?.toString()

        when (currentFriend) {
            null -> {
                repository.add(
                    Friend(
                        id = 0,
                        name = name!!,
                        payment = payment!!
                    )
                )
            }
            else -> {
                repository.update(
                    Friend(
                        id = currentFriend!!.id,
                        name = name!!,
                        payment = payment!!
                    )
                )
            }
        }

    }

    fun navigateBack() {
        this.findNavController().navigate(R.id.action_formFragment2_to_friendListFragment)
    }

    private fun checkDialogEmptyFields() =
        binding.etName.text.toString() == "" || binding.etPayment.text.toString() == ""

}