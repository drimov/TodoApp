package com.drimov.todoapp.util

import android.content.Context
import com.drimov.todoapp.R

class ResourcesHelper(
    private val context: Context
) {
    val message_delete
        get() = context.resources.getString(R.string.message)
    val message_title_need
        get() = context.resources.getString(R.string.title_message)

    val action
        get() = context.resources.getString(R.string.action)
}