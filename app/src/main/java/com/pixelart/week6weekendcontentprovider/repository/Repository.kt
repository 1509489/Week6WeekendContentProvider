package com.pixelart.week6weekendcontentprovider.repository

import androidx.lifecycle.LiveData
import com.pixelart.week6weekendcontentprovider.data.entity.Memo

interface Repository {
    fun getAllMemo():LiveData<List<Memo>>
    fun insertMemo(memo: Memo)
    fun updateMemo(memo: Memo)
    fun deleteMemo(memo: Memo)
}