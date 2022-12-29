package com.atahan.whatioweyoumate.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atahan.whatioweyoumate.model.Friend

@Database(entities = [Friend::class], version = 1)
abstract class FriendDatabase() : RoomDatabase() {
    abstract fun friendDao(): FriendDao
}