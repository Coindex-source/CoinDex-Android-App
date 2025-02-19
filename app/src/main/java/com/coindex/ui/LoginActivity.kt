package com.coindex.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coindex.R
import com.coindex.basic.BindingActivity
import com.coindex.databinding.ActivityLoginBinding
import com.coindex.utils.AnimateUtils
import com.coindex.utils.LogUtils.logE
import com.coindex.utils.ToastUtils

/**
 * 创建钱包&导入钱包
 */
class LoginActivity : BindingActivity<ActivityLoginBinding>() {

    private val onBackPressedCallbackCreate = object : OnBackPressedCallback(false) { // 初始状态为禁用
        override fun handleOnBackPressed() {
            isEnabled=false
            // 在这里处理返回按钮逻辑
            AnimateUtils.animateViewGroupVisibility(binding.groupLoginCreate.root,false,200L)
        }
    }
    private val onBackPressedCallbackLoad = object : OnBackPressedCallback(false) { // 初始状态为禁用
        override fun handleOnBackPressed() {
            isEnabled=false
            // 在这里处理返回按钮逻辑
            AnimateUtils.animateViewGroupVisibility(binding.groupLoginLoad.root,false,200L)
        }
    }
    private var isClickLock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        toggleStatusBarText(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0,0,0, systemBars.bottom)
            insets
        }
        //创建钱包dialog
        binding.tvLoginCreate.setOnClickListener{
            if (!isClickLock){
                isClickLock=true
                AnimateUtils.animateViewGroupVisibility(binding.groupLoginCreate.root,true,300L){
                    onBackPressedCallbackCreate.isEnabled=true
                    isClickLock=false
                }
            }
        }
        //导入钱包dialog
        binding.tvLoginLoad.setOnClickListener{
            if (!isClickLock){
                isClickLock=true
                AnimateUtils.animateViewGroupVisibility(binding.groupLoginLoad.root,true,300L){
                    onBackPressedCallbackLoad.isEnabled=true
                    isClickLock=false
                }
            }
        }
//        隐藏创建钱包dialog
        binding.groupLoginCreate.vItemLoginCreateDismiss.setOnClickListener{
            AnimateUtils.animateViewGroupVisibility(binding.groupLoginCreate.root,false,200L){
                onBackPressedCallbackCreate.isEnabled=false
            }
        }
//        隐藏导入钱包dialog
        binding.groupLoginLoad.vItemLoginLoadDismiss.setOnClickListener{
            AnimateUtils.animateViewGroupVisibility(binding.groupLoginLoad.root,false,200L){
                onBackPressedCallbackLoad.isEnabled=false
            }
        }
//        立即创建
        binding.groupLoginCreate.tvItemLoginCreateSubmit.setOnClickListener {
            AnimateUtils.animateViewGroupVisibility(binding.groupLoginCreate.root,false,100L){
                onBackPressedCallbackCreate.isEnabled=false
                startActivity(Intent(this@LoginActivity,CreateWalletActivity::class.java))
            }
        }
//        服务条款
        // 获取颜色资源
        val colorText = ContextCompat.getColor(this, R.color.text_subtitle)
        val colorURL = ContextCompat.getColor(this, R.color.primary_blue)
        // 创建 SpannableString
        val spannableString = SpannableString("创建即表示同意Coindex服务条款")
        spannableString.setSpan(
            ForegroundColorSpan(colorText),
            0, 7,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(colorURL),
            7, 18,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        // 设置 TextView 的文本
        binding.groupLoginCreate.tvItemLoginCreateAgreement.text = spannableString
        binding.groupLoginCreate.tvItemLoginCreateAgreement.setOnClickListener {
            ToastUtils.show(this,"查看条款")
        }
//      助记词
        binding.groupLoginLoad.vItemLoginLoadClick1.setOnClickListener {
            ToastUtils.showCenter(this,"导入助记词")
        }
//        私钥
        binding.groupLoginLoad.vItemLoginLoadClick2.setOnClickListener {
            ToastUtils.show(this,"导入私钥")
        }
//        云备份
        binding.groupLoginLoad.vItemLoginLoadClick3.setOnClickListener {
            ToastUtils.showCenter(this,"导入云备份")
        }
//        JSON文件
        binding.groupLoginLoad.vItemLoginLoadClick4.setOnClickListener {
            ToastUtils.show(this,"导入JSON文件")
        }
        binding.groupLoginCreate.vItemLoginCreateBg.setOnClickListener {  }
        binding.groupLoginLoad.vItemLoginLoadBg.setOnClickListener {  }


        // 注册回调到 Activity 的 OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, onBackPressedCallbackCreate)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallbackLoad)
    }

}