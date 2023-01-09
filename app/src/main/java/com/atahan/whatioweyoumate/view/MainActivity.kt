package com.atahan.whatioweyoumate.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.databinding.ActivityMainBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogCreateGroupBinding
import com.atahan.whatioweyoumate.databinding.LayoutDialogRemoveBinding
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.presenter.FriendListPresenter
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: LayoutDialogCreateGroupBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        presenter.apply {
//            setView(this@MainActivity)
//            setListeners()
//        }

//        setAdapter()

        //TODO remove later.
//        binding.tvTotalPayment.setOnClickListener {
//            finish()
//            startActivity(intent)
//        }
    }

//    override fun setOnCLickListeners() {
//        binding.fabCreate.setOnClickListener {
//            presenter.add()
//        }
//
//        binding.btnRemove.setOnClickListener {
//            presenter.remove()
//        }
//
//        binding.btnCalculate.setOnClickListener {
//            presenter.calculate()
//        }
//    }

//    override fun edit(position: Int) {
//        presenter.updateItemAt(position)
//    }

//    override fun updateDebt(position: Int) {
//        openDebChangeDialog()
//    }

//    override fun openUpdateDialog(position: Int) {
//        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
//        val dialog = Dialog(this)

//        val currentFriend = friendAdapter.differ.currentList[position]

//        bindingDialog.etName.text =
//            Editable.Factory.getInstance().newEditable(currentFriend.name)
//        bindingDialog.etPayment.text =
//            Editable.Factory.getInstance().newEditable(currentFriend.payment.toString())
//        val paymentBeforeChange = currentFriend.payment


//        with(bindingDialog) {
//            btnConfirm.setOnClickListener {
//                if (checkDialogEmptyFields()) {
//                    return@setOnClickListener
//                }
//                totalPayment -= paymentBeforeChange
//                val payment = etPayment.text?.toString()?.toInt()
//                val name = etName.text?.toString()

//                increasePayment(payment!!)
//                repository.update(
//                    Friend(
//                        id = currentFriend.id,
//                        name = name!!,
//                        payment = payment
//                    )
//                )

//                if (repository.getFriends().size >= SIZE_TWO) {
//                    binding.btnCalculate.isEnabled = true
//                }

//                friendAdapter.apply {
//                    differ.submitList(repository.getFriends())
//                    binding.recyclerview.adapter = this
//                }

//                dialog.dismiss()
//            }

//            btnDismiss.setOnClickListener {
//                dialog.dismiss()
//            }
//        }

//        dialog.apply {
//            setContentView(bindingDialog.root)
//            show()
//        }
//    }

//    private fun openDebChangeDialog() {
//        val bindingDebt = LayoutDialogAddDebtBinding.inflate(layoutInflater)
//        val dialog = Dialog(this)
//
//        dialog.apply {
//            setContentView(bindingDebt.root)
//            show()
//        }
//    }

//    private fun setAdapter() {
//        with(binding.recyclerview) {
//            friendAdapter.differ.submitList(repository.getFriends())
//            layoutManager =
//                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
//            adapter = this@MainActivity.friendAdapter
//            friendAdapter.setLongClickListener(this@MainActivity)
//        }
//
//        repository.getFriends().forEach {
//            totalPayment += it.payment
//        }
//
//        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
//
//        ItemTouchHelper(object :
//            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                val deletedFriend: Friend = friendAdapter.differ.currentList[position]
//                repository.remove(deletedFriend)
//                friendAdapter.differ.submitList(repository.getFriends())
//
//                Snackbar.make(
//                    binding.recyclerview,
//                    "Deleted " + deletedFriend.name,
//                    Snackbar.LENGTH_LONG
//                )
//                    .setAction(
//                        "Undo"
//                    ) {
//                        repository.add(deletedFriend)
//                        friendAdapter.differ.submitList(repository.getFriends())
//                        totalPayment += deletedFriend.payment
//                        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
//                    }
//                    .show()
//
//                totalPayment -= deletedFriend.payment
//                binding.tvTotalPayment.text = "Total Payment: $totalPayment"
//            }
//        }).attachToRecyclerView(binding.recyclerview)
//    }

//    override fun openAddDialog() {
//        bindingDialog = LayoutDialogCreateGroupBinding.inflate(layoutInflater)
//        val dialog = Dialog(this)
//
//        with(bindingDialog) {
//            btnConfirm.setOnClickListener {
//                if (checkDialogEmptyFields()) {
//                    return@setOnClickListener
//                }
//
//                val payment = etPayment.text?.toString()?.toInt()
//                val name = etName.text?.toString()
//
//                increasePayment(payment!!)
//                //TODO presenter.add after refactoring dialogs to fragment.
//                repository.add(
//                    Friend(
//                        id = 0,
//                        name = name!!,
//                        payment = payment
//                    )
//                )
//
//
//                if (repository.getFriends().size >= 2) {
//                    binding.btnCalculate.isEnabled = true
//                }
//
//                dialog.dismiss()
//            }
//
//            btnDismiss.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        dialog.apply {
//            setContentView(bindingDialog.root)
//            show()
//        }
//    }

//    private fun increasePayment(payment: Int) {
//        totalPayment += payment
//        binding.tvTotalPayment.text = "Total Payment: $totalPayment"
//    }

//    private fun checkDialogEmptyFields(): Boolean {
//        return bindingDialog.etName.text.toString() == "" || bindingDialog.etPayment.text.toString() == ""
//    }

//    override fun openRemoveDialog() {
//        if (repository.getFriends().size <= SIZE_ZERO) {
//            Toast.makeText(this, "List is empty.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        bindingRemoveDialog = LayoutDialogRemoveBinding.inflate(layoutInflater)
//        val removeDialog = Dialog(this)
//
//        bindingRemoveDialog.btnRemove.setOnClickListener {
//            binding.btnCalculate.isEnabled = false
//            presenter.clearList()
//            removeDialog.dismiss()
//        }
//
//        bindingRemoveDialog.btnCancel.setOnClickListener {
//            removeDialog.dismiss()
//        }
//
//        removeDialog.apply {
//            setContentView(bindingRemoveDialog.root)
//            show()
//        }
//
//    }

//    override fun clearFriends() {
//        if (repository.getFriends().size > SIZE_ZERO) {
//            repository.removeFriends()
//            totalPayment = SIZE_ZERO
//        }
//        with(binding) {
//            recyclerview.adapter = FriendAdapter()
//            friendAdapter.setLongClickListener(this@MainActivity)
//            tvTotalPayment.text = "Total Payment: 0"
//            recyclerview.visibility = View.VISIBLE
//            tvBill.visibility = View.GONE
//        }
//    }

//    override fun calculateDebts() {
//        with(binding) {
//            recyclerview.visibility = View.GONE
//            tvBill.visibility = View.VISIBLE
//        }
//
//        var bill = "================BILL================\n"
//        repository.getFriends().forEach { friend ->
//            val debtPerEach = friend.payment / repository.getFriends().size
//            bill += "->Everyone should pay $debtPerEach to ${friend.name}\n"
//        }
//        binding.tvBill.text = bill
//    }

//    companion object {
//        private const val SIZE_ZERO = 0
//        private const val SIZE_TWO = 2
//    }
}