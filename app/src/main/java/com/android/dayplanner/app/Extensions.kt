package com.android.dayplanner.app

import com.android.dayplanner.app.data.Task

fun <T> MutableList<T>.addAll(trashExistingItems: Boolean, items: List<T>) {
    clear()
    addAll(items)
}