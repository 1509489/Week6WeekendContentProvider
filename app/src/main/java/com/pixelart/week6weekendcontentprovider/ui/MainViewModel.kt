package com.pixelart.week6weekendcontentprovider.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import com.pixelart.week6weekendcontentprovider.repository.RepositoryImpl

class MainViewModel(application: Application): AndroidViewModel(application){

    private val repositoryImpl: RepositoryImpl = RepositoryImpl(application)
    private val memoList: LiveData<List<Memo>>

    init {
        memoList = repositoryImpl.getAllMemo()
    }

    fun insert(memo: Memo)
    {
        repositoryImpl.insertMemo(memo)
    }

    fun update(memo: Memo){
        repositoryImpl.updateMemo(memo)
    }

    fun delete(memo: Memo){
        repositoryImpl.deleteMemo(memo)
    }

    fun getMemos():LiveData<List<Memo>> = memoList
}