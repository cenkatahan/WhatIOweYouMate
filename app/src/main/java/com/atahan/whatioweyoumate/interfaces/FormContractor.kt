package com.atahan.whatioweyoumate.interfaces

interface FormContractor {
    interface IView {
        fun confirmFriend()
        fun navigateBack()
    }


    interface IPresenter {
        fun confirmItem()
        fun navigate()
    }
}