package com.sc.easycooking.recipes.impl.domain

import android.util.Log
import android.util.LruCache
import javax.inject.Inject

internal class FastAccessDataShareImpl @Inject constructor() : FastAccessDataShare {
    private val limit = 5
    private val lruCache = LruCache<String, Any>(limit)

    init {
        Log.e("VVV", "Init FastAccessDataShareImpl: ${hashCode()}")
    }

    override fun putItem(key: String, item: Any) {
        lruCache.put(key, item)
    }

    override fun getItem(key: String): Any? {
        return lruCache[key]
    }

    override fun clearItem(key: String) {
        lruCache.remove(key)
    }
}