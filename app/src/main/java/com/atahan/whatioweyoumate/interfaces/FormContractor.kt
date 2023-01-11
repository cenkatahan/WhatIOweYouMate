package com.atahan.whatioweyoumate.interfaces

interface FormContractor {
    interface IView {
        fun addFriend()
        fun updateFriendAt(id: Int)
        fun navigateBack()
    }


    interface IPresenter {
        fun addItem()
        fun updateItemAt(id: Int)
        fun navigate()
    }
}