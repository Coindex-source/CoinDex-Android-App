package com.coindex.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coindex.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //        enableEdgeToEdge()
//                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        // 启用沉浸式主题
//        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
//        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
////        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars()) // 隐藏状态栏
////        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars()) // 隐藏导航栏
//
//        // 设置状态栏和导航栏的颜色样式（可选）
//        windowInsetsController.isAppearanceLightStatusBars = true // 状态栏使用亮色样式
//        windowInsetsController.isAppearanceLightNavigationBars = true // 导航栏使用亮色样式
//
//        // 设置fitsSystemWindows为false，允许内容延伸到状态栏和导航栏区域
//        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}