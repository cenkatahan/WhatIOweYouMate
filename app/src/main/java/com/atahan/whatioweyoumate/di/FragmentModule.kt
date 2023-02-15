package com.atahan.whatioweyoumate.di

import dagger.Module
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