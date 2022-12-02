package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.MainActivityContractor

class MainActivityPresenter(private val view: MainActivityContractor.IView): MainActivityContractor.IPresenter{

    override fun setListeners() {
        view.setOnCLickListeners()
    }

    override fun add() {
        view.openAddDialog()
    }

    override fun updateItemAt(position: Int) {
        view.openUpdateDialog(position)
    }

    override fun remove() {
        view.openRemoveDialog()
    }

    override fun clearList() {
        view.clearFriends()
    }

    override fun calculate() {
        view.calculateDebts()
    }


}