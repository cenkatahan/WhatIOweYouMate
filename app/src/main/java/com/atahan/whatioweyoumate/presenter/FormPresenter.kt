package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.FormContractor
import javax.inject.Inject

class FormPresenter @Inject constructor(): FormContractor.IPresenter {
    private lateinit var view: FormContractor.IView

    override fun confirmItem() {
        view.confirmFriend()
    }

    override fun navigate() {
        view.navigateBack()
    }

    fun setView(iView: FormContractor.IView) {
        this.view = iView
    }
}