package com.sc.easycooking.recipes.impl.domain

internal interface FastAccessDataShare {
    fun putItem(key: String, item: Any)
    fun getItem(key: String): Any?
    fun clearItem(key: String)
}