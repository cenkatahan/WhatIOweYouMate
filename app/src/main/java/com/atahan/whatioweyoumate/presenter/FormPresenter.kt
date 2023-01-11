package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.FormContractor
import javax.inject.Inject

class FormPresenter @Inject constructor(): FormContractor.IPresenter {
    private lateinit var view: FormContractor.IView

    override fun addItem() {
        view.addFriend()
    }

    override fun updateItemAt(id: Int) {
        view.updateFriendAt(id)
    }

    override fun navigate() {
        view.navigateBack()
    }

    fun setView(iView: FormContractor.IView) {
        this.view = iView
    }
}