package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.FriendsContractor
import javax.inject.Inject

class FriendsPresenter @Inject
constructor() :
    FriendsContractor.IFriendsPresenter {

    private lateinit var view: FriendsContractor.IFriendsView

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


    fun setView(iView: FriendsContractor.IFriendsView) {
        view = iView
    }
}