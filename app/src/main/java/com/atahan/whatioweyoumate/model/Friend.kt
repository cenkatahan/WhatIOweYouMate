package com.atahan.whatioweyoumate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atahan.whatioweyoumate.common.Constants.COL_NAME
import com.atahan.whatioweyoumate.common.Constants.COL_PAYMENT
import com.atahan.whatioweyoumate.common.Constants.TABLE_FRIEND

@Entity(tableName = TABLE_FRIEND)
data class Friend(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = COL_NAME) val name: String = "",
    @ColumnInfo(name = COL_PAYMENT) val payment: Int = 0
)
