package com.coindex.utils

import android.content.Context
import com.tencent.mmkv.MMKV
import org.web3j.abi.datatypes.Bool

object StorageUtils {

    // 获取默认实例
    private lateinit var mmkv:MMKV

    fun init(appCtx:Context) {
        MMKV.initialize(appCtx)
        mmkv = MMKV.defaultMMKV()
    }

    /**
     * 阅读并同意服务协议
     */
    fun loadAgreement(): Boolean {
        return mmkv.decodeBool("loadAgreement")
    }
    fun saveAgreement(value: Boolean){
        mmkv.encode("loadAgreement",value)
    }

    /**
     * 助记词
     */
    fun loadWords():String{
        return mmkv.decodeString("loadWords","").toString()
    }
    fun saveWords(value: String){
        mmkv.encode("loadWords",value)
    }

}