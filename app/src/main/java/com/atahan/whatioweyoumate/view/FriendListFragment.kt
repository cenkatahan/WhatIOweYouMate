package com.atahan.whatioweyoumate.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.common.Constants.KEY_ARGUMENT
import com.atahan.whatioweyoumate.databinding.FragmentFriendListBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.interfaces.ILongClick
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.viewmodel.FriendListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FriendListFragment : Fragment(), ILongClick {
    private lateinit var binding: FragmentFriendListBinding
    private lateinit var bindingRemoveDialog: LayoutDialogRemoveBinding
    private var totalPayment: Int = 0

    @Inject
    lateinit var friendAdapter: FriendAdapter

    @Inject
    lateinit var friend: Friend

    private val viewModel: FriendListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnCLickListeners()
        setAdapter()

        viewModel.friendList.forEach {
            totalPayment += it.payment
        }
    }

    override fun edit(position: Int) {
        updateFriendAt(position)
    }

    private fun setOnCLickListeners() {
        binding.fabCreate.setOnClickListener {
            navigateForm()
        }

        binding.btnRemove.setOnClickListener {
            removeFriends()
        }

        binding.btnCalculate.setOnClickListener {
            if (viewModel.friendList.count() < SIZE_TWO) {
                Toast.makeText(
                    requireContext(),
                    "At least two friend is needed.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            calculateDebts()
        }
    }

    private fun navigateForm() {
        this.findNavController().navigate(R.id.action_friendListFragment_to_formFragment2)
    }

    private fun updateFriendAt(id: Int) {
        val currentFriend = friendAdapter.differ.currentList[id]
        val bundle = bundleOf(KEY_ARGUMENT to currentFriend)
        view?.findNavController()?.navigate(R.id.action_friendListFragment_to_formFragment2, bundle)
    }

    private fun removeFriends() {
        if (viewModel.friendList.size <= SIZE_ZERO) {
            Toast.makeText(context, "List is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(requireContext())

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
        if (viewModel.friendList.size > SIZE_ZERO) {
            viewModel.removeFriends()
            totalPayment = SIZE_ZERO
        }
        with(binding) {
            recyclerview.adapter = FriendAdapter()
            friendAdapter.setLongClickListener(this@FriendListFragment)
            tvTotalPayment.text = "Total Payment: 0"
            recyclerview.visibility = View.VISIBLE
            tvBill.visibility = View.GONE
        }
    }

    private fun calculateDebts() {
        view?.findNavController()
            ?.navigate(R.id.action_friendListFragment_to_calculationResultFragment2, null)
    }


    private fun setAdapter() {
        with(binding.recyclerview) {
            friendAdapter.differ.submitList(viewModel.friendList)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@FriendListFragment.friendAdapter
            friendAdapter.setLongClickListener(this@FriendListFragment)
        }

        viewModel.friendList.forEach {
            totalPayment += it.payment
        }

        binding.tvTotalPayment.text = "Total Payment: $totalPayment"

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedFriend: Friend = friendAdapter.differ.currentList[position]
                totalPayment -= deletedFriend.payment
                viewModel.removeFriend(deletedFriend)
                friendAdapter.differ.submitList(viewModel.friendList)

                Snackbar.make(
                    binding.recyclerview,
                    "Deleted " + deletedFriend.name,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo"
                    ) {
                        viewModel.add(deletedFriend)
                        friendAdapter.differ.submitList(viewModel.friendList)
                        totalPayment += deletedFriend.payment
                        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
                    }
                    .show()
                totalPayment = viewModel.friendList.sumOf { it.payment }
                binding.tvTotalPayment.text = "Total Payment: $totalPayment"
            }
        }).attachToRecyclerView(binding.recyclerview)
    }

    companion object {
        private const val SIZE_ZERO = 0
        private const val SIZE_TWO = 2
    }
}