package com.coindex.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object AnimateUtils {

    /**
     * 动画显示或隐藏 ViewGroup
     * @param viewGroup 要操作的 ViewGroup
     * @param visible 是否显示
     * @param duration 动画时长（毫秒，默认300ms）
     * @param disableButtons 要禁用的按钮集合
     * @param onAnimationEnd 动画结束后的回调
     */
    fun animateViewGroupVisibility(
        viewGroup: View,
        visible: Boolean,
        duration: Long = 300L,
        disableButtons: List<View>? = null,
        onAnimationEnd: (() -> Unit)? = null
    ) {
        // 如果视图已经是目标状态，直接回调并返回
        if ((visible && viewGroup.visibility == View.VISIBLE) || (!visible && viewGroup.visibility == View.GONE)) {
            disableButtons?.forEach { it.isEnabled = true } // 确保按钮重新启用
            onAnimationEnd?.invoke()
            return
        }

        // 如果动画正在执行，取消之前的动画
        if (viewGroup.tag is Animator) {
            (viewGroup.tag as Animator).cancel()
        }

        // 设置初始状态
        if (viewGroup.visibility == View.GONE && visible) {
            viewGroup.alpha = 0f
            viewGroup.visibility = View.VISIBLE
        }

        // 创建透明度动画
        val animator = ObjectAnimator.ofFloat(viewGroup, "alpha", viewGroup.alpha, if (visible) 1f else 0f).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    // 动画开始时禁用所有按钮
                    disableButtons?.forEach { it.isEnabled = false }
                }

                override fun onAnimationEnd(animation: Animator) {
                    // 动画结束后设置最终状态
                    viewGroup.visibility = if (visible) View.VISIBLE else View.GONE
                    viewGroup.tag = null  // 清除动画标记

                    // 动画结束后重新启用所有按钮
                    disableButtons?.forEach { it.isEnabled = true }

                    // 调用回调
                    onAnimationEnd?.invoke()
                }
            })
        }

        // 将动画存储在 viewGroup 的 tag 中，以便后续取消
        viewGroup.tag = animator

        // 开始动画
        animator.start()
    }
}