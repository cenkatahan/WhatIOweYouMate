package com.atahan.whatioweyoumate.di

import com.atahan.whatioweyoumate.adapter.FriendAdapter
import com.atahan.whatioweyoumate.interfaces.FriendsContractor
import com.atahan.whatioweyoumate.view.FriendListFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

//    @Provides
//    fun provideListView(): FriendsContractor.IFriendsView = FriendListFragment()

//    @Provides
//    fun provideAdapter() = FriendAdapter()

//    @Provides
//    fun provideListPresenter(view: ListContractor.IView): ListContractor.IPresenter = FriendListPresenter(view)
}