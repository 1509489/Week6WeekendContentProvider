package com.pixelart.week6weekendcontentprovider.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pixelart.week6weekendcontentprovider.data.dao.MemoDao
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import com.pixelart.week6weekendcontentprovider.utils.DATABASE_VERSION

@Database(entities = [Memo::class], version = DATABASE_VERSION)
abstract class MemoDatabase: RoomDatabase(){

    abstract fun memoDao(): MemoDao
}