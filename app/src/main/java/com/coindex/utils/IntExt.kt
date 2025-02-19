package com.coindex.utils

import android.content.Context

object IntExt {

    fun Int.dp2px(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    fun Int.px2dp(context: Context): Int {
        return (this / context.resources.displayMetrics.density).toInt()
    }
}