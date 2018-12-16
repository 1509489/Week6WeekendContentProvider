package com.pixelart.week6weekendcontentprovider.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixelart.week6weekendcontentprovider.utils.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String,
    var memoBody: String
)