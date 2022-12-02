package com.atahan.whatioweyoumate.interfaces

interface MainActivityContractor {
    interface IView {
        fun setOnCLickListeners()
        fun openAddDialog()
        fun openUpdateDialog(position: Int)
        fun openRemoveDialog()
        fun clearFriends()
        fun calculateDebts()
    }

    interface IPresenter {
        fun setListeners()
        fun add()
        fun updateItemAt(position: Int)
        fun remove()
        fun clearList()
        fun calculate()
    }

}