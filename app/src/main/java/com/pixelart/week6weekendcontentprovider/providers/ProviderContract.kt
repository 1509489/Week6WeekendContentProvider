package com.pixelart.week6weekendcontentprovider.providers

import android.net.Uri
import android.provider.BaseColumns
import com.pixelart.week6weekendcontentprovider.utils.TABLE_NAME

object ProviderContract {
    val CONTENT_AUTHORITY = "package com.pixelart.week6weekendcontentprovider.providers"
    val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")

    object ProviderEntity: BaseColumns{
        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build()
        val COLUMN_MEMO_TITLE = "title"
        val COLUMN_MEMO_BODY = "memoBody"
    }
}