package com.coindex.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


/**
 */
open class BasicFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 视图创建完成后执行的逻辑
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Activity 创建完成后执行的逻辑
    }

    override fun onStart() {
        super.onStart()
        // Fragment 开始时执行的逻辑
    }

    override fun onResume() {
        super.onResume()
        // Fragment 恢复时执行的逻辑
    }

    override fun onPause() {
        super.onPause()
        // Fragment 暂停时执行的逻辑
    }

    override fun onStop() {
        super.onStop()
        // Fragment 停止时执行的逻辑
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 视图销毁时执行的逻辑
    }

    override fun onDestroy() {
        super.onDestroy()
        // Fragment 销毁时执行的逻辑
    }

    override fun onDetach() {
        super.onDetach()
        // Fragment 从 Activity 中分离时执行的逻辑
    }

}