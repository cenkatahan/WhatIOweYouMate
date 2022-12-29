package com.atahan.whatioweyoumate.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atahan.whatioweyoumate.common.Constants.TABLE_FRIEND
import com.atahan.whatioweyoumate.db.FriendDatabase
import com.atahan.whatioweyoumate.model.Friend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DdModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, FriendDatabase::class.java, TABLE_FRIEND)
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideDao(db : FriendDatabase) = db.friendDao()

    @Provides
    fun provideFriend() = Friend()
}