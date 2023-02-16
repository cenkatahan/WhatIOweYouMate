package com.atahan.whatioweyoumate.viewmodel

import androidx.lifecycle.ViewModel
import com.atahan.whatioweyoumate.model.Friend
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val repository: FriendRepository
) : ViewModel() {
    fun add(friend: Friend) = repository.add(friend)
    fun update(friend: Friend) = repository.update(friend)
}