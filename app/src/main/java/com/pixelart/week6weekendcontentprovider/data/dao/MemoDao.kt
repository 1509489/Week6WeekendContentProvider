package com.pixelart.week6weekendcontentprovider.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import android.database.Cursor
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import io.reactivex.Flowable

@Dao
interface MemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Update
    fun update(memo: Memo)

    @Delete
    fun delete(memo: Memo)

    @Query("SELECT * FROM memo_table ORDER BY title ASC")
    fun getAllMemo(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo_table")
    fun getAllMemoCursor(): Cursor
}