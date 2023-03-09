package com.atahan.whatioweyoumate.viewmodel

import com.atahan.whatioweyoumate.repo.FakeFriendRepository
import org.junit.Before

class FriendViewModelTest {

    private lateinit var friendListViewModel: FriendListViewModel

    @Before
    fun setup() {
        friendListViewModel = FriendListViewModel(FakeFriendRepository())
    }


}