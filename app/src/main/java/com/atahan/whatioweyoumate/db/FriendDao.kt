package com.atahan.whatioweyoumate.db

import androidx.room.*
import com.atahan.whatioweyoumate.common.Constants.TABLE_FRIEND
import com.atahan.whatioweyoumate.model.Friend
import java.util.ArrayList

@Dao
interface FriendDao {
    @Insert
    fun save(friend: Friend)

    @Query("SELECT * FROM $TABLE_FRIEND WHERE id=:id")
    fun getFriend(id: Int): Friend

    @Query("SELECT * FROM $TABLE_FRIEND")
    fun getFriends(): List<Friend>

    @Delete
    fun delete(friend: Friend)

    @Query("DELETE FROM $TABLE_FRIEND")
    fun deleteAll()

    @Update
    fun update(friend: Friend)
}