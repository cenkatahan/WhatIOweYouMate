package com.atahan.whatioweyoumate.repository

import com.atahan.whatioweyoumate.model.Friend

interface IRepository {
    fun add(friend: Friend)
    fun remove(friend: Friend)
    fun update(friend: Friend)
    fun getFriendBy(id: Int): Friend
    fun getFriends(): List<Friend>
    fun removeFriends()
}