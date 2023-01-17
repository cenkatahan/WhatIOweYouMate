package com.atahan.whatioweyoumate.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.databinding.FragmentFriendListBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogAddDebtBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.interfaces.ILongClick
import com.atahan.whatioweyoumate.interfaces.FriendsContractor
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.presenter.FriendsPresenter
import com.atahan.whatioweyoumate.repository.FriendRepository
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FriendListFragment : Fragment(), ILongClick, FriendsContractor.IFriendsView {
    private lateinit var binding: FragmentFriendListBinding
    private lateinit var bindingRemoveDialog: LayoutDialogRemoveBinding
    private var totalPayment: Int = 0

    @Inject
    lateinit var friendAdapter: FriendAdapter

    @Inject
    lateinit var presenter: FriendsPresenter

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
        presenter.apply {
            setView(this@FriendListFragment)
        }
        setOnCLickListeners()
        setAdapter()

        repository.getFriends().forEach {
            totalPayment += it.payment
        }
    }

    override fun edit(position: Int) {
        presenter.updateItemAt(position)
    }

    override fun openAddDialog() {
        this.findNavController().navigate(R.id.action_friendListFragment_to_formFragment2)
    }

    override fun updateFriendAt(id: Int) {
        val currentFriend = friendAdapter.differ.currentList[id]
        val bundle = bundleOf("friend" to currentFriend)
        view?.findNavController()?.navigate(R.id.action_friendListFragment_to_formFragment2, bundle)
    }

    override fun removeFriends() {
        if (repository.getFriends().size <= SIZE_ZERO) {
            Toast.makeText(context, "List is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
        val removeDialog = Dialog(requireContext())

        bindingRemoveDialog.btnRemove.setOnClickListener {
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

    private fun setOnCLickListeners() {
        binding.fabCreate.setOnClickListener {
            presenter.add()
        }

        binding.btnRemove.setOnClickListener {
            presenter.remove()
        }

        binding.btnCalculate.setOnClickListener {
            if (repository.getFriends().count() < SIZE_TWO) {
                Toast.makeText(requireContext(), "At least two friend is needed.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            presenter.calculate()
        }
    }

    override fun calculateDebts() {
        view?.findNavController()?.navigate(R.id.action_friendListFragment_to_calculationResultFragment2, null)
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
                totalPayment -= deletedFriend.payment
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
                totalPayment = repository.getFriends().map { it.payment }.sum()
                binding.tvTotalPayment.text = "Total Payment: $totalPayment"
            }
        }).attachToRecyclerView(binding.recyclerview)
    }

    companion object {
        private const val SIZE_ZERO = 0
        private const val SIZE_TWO = 2
    }
}