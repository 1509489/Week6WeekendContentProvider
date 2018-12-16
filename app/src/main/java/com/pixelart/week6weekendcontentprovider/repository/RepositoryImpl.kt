package com.pixelart.week6weekendcontentprovider.repository

import androidx.lifecycle.LiveData
import androidx.room.Room
import android.content.Context
import com.pixelart.week6weekendcontentprovider.data.dao.MemoDao
import com.pixelart.week6weekendcontentprovider.data.database.MemoDatabase
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import com.pixelart.week6weekendcontentprovider.utils.DATABASE_NAME

class RepositoryImpl(context: Context): Repository{


    private val memoDao:MemoDao
    private val memoList:LiveData<List<Memo>>

    init {
        val database = Room.databaseBuilder(context, MemoDatabase::class.java, DATABASE_NAME).build()
        memoDao = database.memoDao()
        memoList = memoDao.getAllMemo()
    }

    override fun getAllMemo(): LiveData<List<Memo>> = memoDao.getAllMemo()

    override fun insertMemo(memo: Memo) {
        Thread{
            memoDao.insert(memo)
        }.start()
    }

    override fun updateMemo(memo: Memo) {
        Thread{
            memoDao.update(memo)
        }.start()
    }

    override fun deleteMemo(memo: Memo) {
        Thread{
            memoDao.delete(memo)
        }.start()
    }
}