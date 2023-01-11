package com.atahan.whatioweyoumate.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.databinding.FragmentFriendListBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.interfaces.ILongClick
import com.atahan.whatioweyoumate.interfaces.MainActivityContractor
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.presenter.FriendListPresenter
import com.atahan.whatioweyoumate.repository.FriendRepository
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FriendListFragment : Fragment(), ILongClick, MainActivityContractor.IView {
    private lateinit var binding: FragmentFriendListBinding
    private lateinit var bindingRemoveDialog: LayoutDialogRemoveBinding
    private var totalPayment: Int = 0

    @Inject
    lateinit var presenter: FriendListPresenter

    @Inject
    lateinit var friendAdapter: FriendAdapter

    @Inject
    lateinit var repository: FriendRepository

    @Inject
    lateinit var friend: Friend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup recyclerview, adapter & stuff.

        presenter.apply {
            setView(this@FriendListFragment)
            setListeners()
        }

        setAdapter()

        //TODO remove later.
        binding.tvTotalPayment.setOnClickListener {
            this.activity?.finish()
            startActivity(this.activity?.intent)
        }

        repository.getFriends().forEach {
            totalPayment += it.payment
        }
        //TODO review
//        binding.tvTotalPayment.text = repository.getFriends().map {
//            it.payment
//        }.sum().toString()
    }

    override fun edit(position: Int) {
        //navigate form fragment
        presenter.updateItemAt(position)
    }

    override fun updateDebt(position: Int) {
        TODO("Not yet implemented")
        //change debt value
    }

    override fun setOnCLickListeners() {
        binding.fabCreate.setOnClickListener {
            presenter.add()
        }

        binding.btnRemove.setOnClickListener {
            presenter.remove()
        }

        binding.btnCalculate.setOnClickListener {
            presenter.calculate()
        }
    }

    override fun openAddDialog() {
        this.findNavController().navigate(R.id.action_friendListFragment_to_formFragment2)
    }

    override fun openUpdateDialog(position: Int) {
        //navigate form fragment with argument.
    }

    override fun openRemoveDialog() {
        if (repository.getFriends().size <= SIZE_ZERO) {
            Toast.makeText(context, "List is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(requireContext())

        bindingRemoveDialog.btnRemove.setOnClickListener {
            binding.btnCalculate.isEnabled = false
            presenter.clearList()
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

    override fun clearFriends() {
        if (repository.getFriends().size > SIZE_ZERO) {
            repository.removeFriends()
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

    override fun calculateDebts() {
        with(binding) {
            recyclerview.visibility = View.GONE
            tvBill.visibility = View.VISIBLE
        }

        var bill = "================BILL================\n"
        repository.getFriends().forEach { friend ->
            val debtPerEach = friend.payment / repository.getFriends().size
            bill += "->Everyone should pay $debtPerEach to ${friend.name}\n"
        }
        binding.tvBill.text = bill
    }


    private fun setAdapter() {
        with(binding.recyclerview) {
            friendAdapter.differ.submitList(repository.getFriends())
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@FriendListFragment.friendAdapter
            friendAdapter.setLongClickListener(this@FriendListFragment)
        }

        repository.getFriends().forEach {
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
                repository.remove(deletedFriend)
                friendAdapter.differ.submitList(repository.getFriends())

                Snackbar.make(
                    binding.recyclerview,
                    "Deleted " + deletedFriend.name,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo"
                    ) {
                        repository.add(deletedFriend)
                        friendAdapter.differ.submitList(repository.getFriends())
                        totalPayment += deletedFriend.payment
                        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
                    }
                    .show()

                totalPayment -= deletedFriend.payment
                binding.tvTotalPayment.text = "Total Payment: $totalPayment"
            }
        }).attachToRecyclerView(binding.recyclerview)
    }

    private fun increasePayment(payment: Int) {
        totalPayment += payment
        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
    }


    companion object {
        private const val SIZE_ZERO = 0
        private const val SIZE_TWO = 2
    }
}