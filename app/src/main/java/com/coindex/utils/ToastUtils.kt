package com.coindex.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtils {

    private var currentToast: Toast? = null

    /**
     * 在默认位置显示 Toast
     * @param context 上下文
     * @param message 要显示的消息
     * @param duration 显示时长（Toast.LENGTH_SHORT 或 Toast.LENGTH_LONG）
     */
    fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        showCustomToast(context, message, duration)
    }

    /**
     * 在屏幕中间显示 Toast
     * @param context 上下文
     * @param message 要显示的消息
     * @param duration 显示时长（Toast.LENGTH_SHORT 或 Toast.LENGTH_LONG）
     */
    fun showCenter(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        showCustomToast(context, message, duration, Gravity.CENTER, 0, 0)
    }

    /**
     * 内部方法：显示自定义 Toast
     * @param context 上下文
     * @param message 要显示的消息
     * @param duration 显示时长
     * @param gravity Toast 的位置
     * @param xOffset X 偏移量
     * @param yOffset Y 偏移量
     */
    private fun showCustomToast(
        context: Context,
        message: String,
        duration: Int,
        gravity: Int = Gravity.BOTTOM,
        xOffset: Int = 0,
        yOffset: Int = 0
    ) {
        currentToast?.cancel() // 取消当前正在显示的 Toast

        currentToast = Toast.makeText(context, message, duration).apply {
            setGravity(gravity, xOffset, yOffset)
        }
        currentToast?.show()
    }
}