package com.atahan.whatioweyoumate.repo

import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.IRepository

class FakeFriendRepository: IRepository {

    private val friends = mutableListOf<Friend>()

    override fun add(friend: Friend) {
        friends.add(friend)
    }

    override fun remove(friend: Friend) {
        friends.remove(friend)
    }

    override fun update(friend: Friend) {
    }

    override fun getFriendBy(id: Int): Friend {
        return friends[id]
    }

    override fun getFriends(): List<Friend> {
        return friends
    }

    override fun removeFriends() {
        friends.clear()
    }
}