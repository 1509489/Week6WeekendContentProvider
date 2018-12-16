package com.pixelart.week6weekendcontentprovider.providers

import androidx.room.Room
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.pixelart.week6weekendcontentprovider.data.database.MemoDatabase
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import com.pixelart.week6weekendcontentprovider.utils.DATABASE_NAME
import com.pixelart.week6weekendcontentprovider.utils.TABLE_NAME
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MemoProvider: ContentProvider(){
    val MEMO_URI_CODE = 10
    private lateinit var memoDatabase: MemoDatabase
    private lateinit var uriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        memoDatabase = Room.databaseBuilder(context!!, MemoDatabase::class.java, DATABASE_NAME).build()
        uriMatcher = createUriMatcher()
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)){
            MEMO_URI_CODE ->{
                values?.let {
                    Completable.fromAction {
                        memoDatabase.memoDao().insert(Memo(
                            title = it[ProviderContract.ProviderEntity.COLUMN_MEMO_TITLE] as String,
                            memoBody = it[ProviderContract.ProviderEntity.COLUMN_MEMO_BODY] as String
                        ))
                    }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{context?.contentResolver?.notifyChange(uri, null)}
                }
            }
        }
        return ProviderContract.BASE_CONTENT_URI
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = memoDatabase.memoDao().getAllMemoCursor()

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createUriMatcher(): UriMatcher{
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        val authority = ProviderContract.CONTENT_AUTHORITY

        matcher.addURI(authority, TABLE_NAME, MEMO_URI_CODE)
        return matcher
    }

}