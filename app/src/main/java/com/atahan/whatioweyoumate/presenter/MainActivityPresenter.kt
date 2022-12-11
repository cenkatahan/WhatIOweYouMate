package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.MainActivityContractor
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(): MainActivityContractor.IPresenter{

    private lateinit var view: MainActivityContractor.IView

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


    fun setView(iView: MainActivityContractor.IView) {
        view = iView
    }

}