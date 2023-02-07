package com.atahan.whatioweyoumate.repository

import com.atahan.whatioweyoumate.db.FriendDao
import com.atahan.whatioweyoumate.model.Friend
import javax.inject.Inject

class FriendRepository
@Inject constructor(
    private val friendDao: FriendDao
): IRepository {

    override fun add(friend: Friend) = friendDao.save(friend)
    override fun remove(friend: Friend) = friendDao.delete(friend)
    override fun update(friend: Friend) = friendDao.update(friend)
    override fun getFriendBy(id: Int) = friendDao.getFriend(id)
    override fun getFriends() = friendDao.getFriends()
    override fun removeFriends() = friendDao.deleteAll()

}