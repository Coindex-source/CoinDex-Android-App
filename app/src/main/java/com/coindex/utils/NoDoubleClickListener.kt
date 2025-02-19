package com.coindex.utils

import android.view.View

class NoDoubleClickListener(private val interval: Long = 300L, // 默认防抖间隔为 300 毫秒
                            private val onNoDoubleClick: (View) -> Unit) : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > interval) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }
}