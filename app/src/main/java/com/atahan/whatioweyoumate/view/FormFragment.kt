package com.atahan.whatioweyoumate.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.common.Constants.KEY_ARGUMENT
import com.atahan.whatioweyoumate.databinding.FragmentFormBinding
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.FriendRepository
import com.atahan.whatioweyoumate.viewmodel.FormViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding

    private var currentFriend: Friend? = null

    private val viewModel: FormViewModel by viewModels()

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

        currentFriend = arguments?.getParcelable(KEY_ARGUMENT)

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
            confirmFriend()
            navigateBack()
        }

        binding.btnDismiss.setOnClickListener {
            navigateBack()
        }
    }

    private fun confirmFriend() {
        val payment = binding.etPayment.text?.toString()?.toInt()
        val name = binding.etName.text?.toString()

        when (currentFriend) {
            null -> add(
                Friend(
                    id = 0,
                    name = name!!,
                    payment = payment!!
                )
            )
            else -> update(
                Friend(
                    id = currentFriend!!.id,
                    name = name!!,
                    payment = payment!!
                )
            )
        }

    }

    private fun navigateBack() {
        this.findNavController().navigate(R.id.action_formFragment2_to_friendListFragment)
    }

    private fun checkDialogEmptyFields() =
        binding.etName.text.toString() == "" || binding.etPayment.text.toString() == ""

    private fun add(friend: Friend) = viewModel.add(friend)
    private fun update(friend: Friend) = viewModel.update(friend)
}