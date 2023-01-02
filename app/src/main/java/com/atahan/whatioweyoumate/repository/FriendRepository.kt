package com.atahan.whatioweyoumate.repository

import com.atahan.whatioweyoumate.db.FriendDao
import com.atahan.whatioweyoumate.model.Friend
import javax.inject.Inject

class FriendRepository
@Inject constructor(
    private val friendDao: FriendDao
) {

    fun add(friend: Friend) = friendDao.save(friend)
    fun remove(friend: Friend) = friendDao.delete(friend)
    fun update(friend: Friend) = friendDao.update(friend)
    fun getFriendBy(id: Int) = friendDao.getFriend(id)
    fun getFriends() = friendDao.getFriends()
    fun removeFriends() = friendDao.deleteAll()

}