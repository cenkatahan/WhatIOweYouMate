package com.atahan.whatioweyoumate.interfaces

import com.atahan.whatioweyoumate.di.FragmentModule
import dagger.Component


interface FriendsContractor {

    interface IFriendsView {
        fun openAddDialog()
        fun updateFriendAt(id: Int)
        fun removeFriends()
        fun clearFriends()
        fun calculateDebts()
    }

    interface IFriendsPresenter {
        fun add()
        fun updateItemAt(position: Int)
        fun remove()
        fun clearList()
        fun calculate()
    }
}