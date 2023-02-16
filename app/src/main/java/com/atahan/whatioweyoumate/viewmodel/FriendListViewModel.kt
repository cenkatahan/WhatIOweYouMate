package com.atahan.whatioweyoumate.viewmodel

import androidx.lifecycle.ViewModel
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FriendListViewModel
@Inject constructor(
    private val repository: FriendRepository
) : ViewModel() {

    val friendList: List<Friend>
        get() = repository.getFriends()

    fun add(friend: Friend) = repository.add(friend)

    fun removeFriends() = repository.removeFriends()

    fun removeFriend(friend: Friend) = repository.remove(friend)
}