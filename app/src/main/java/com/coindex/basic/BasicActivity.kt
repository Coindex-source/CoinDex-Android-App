package com.coindex.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

open class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * @param isLightMode
     * true：状态栏文字为黑色
     * false：状态栏文字为白色
     */
    fun toggleStatusBarText(isLightMode: Boolean) {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = isLightMode
    }

}