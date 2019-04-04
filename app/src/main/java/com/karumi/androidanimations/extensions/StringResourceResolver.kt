package com.karumi.androidanimations.extensions

import android.content.Context

interface StringResourceResolver {
    fun Int.resolve(context: Context): String = context.getString(this)
}