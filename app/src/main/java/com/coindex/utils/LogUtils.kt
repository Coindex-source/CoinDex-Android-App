package com.coindex.utils

import android.util.Log

object LogUtils {

    private var tag: String? = "unInit TAG"
    private var enable: Boolean = false

    fun init(appTag: String):LogUtils{
        tag=appTag
        return this
    }
    fun showLog(showLog: Boolean){
        enable=showLog
    }

    fun String.logD(tempTag: String?=null){
        if (enable) Log.d(tempTag?:tag, this)
    }
    fun String.logE(tempTag: String?=null){
        if (enable) Log.e(tempTag?:tag, this)
    }
    fun String.logI(tempTag: String?=null){
        if (enable) Log.i(tempTag?:tag, this)
    }
    fun String.logV(tempTag: String?=null){
        if (enable) Log.v(tempTag?:tag, this)
    }
    fun String.logW(tempTag: String?=null){
        if (enable) Log.w(tempTag?:tag, this)
    }
    fun String.logWTF(tempTag: String?=null){
        if (enable) Log.wtf(tempTag?:tag, this)
    }
}
