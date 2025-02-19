package com.coindex.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.ImageView
import com.coindex.R
import com.coindex.basic.BindingActivity
import com.coindex.databinding.ActivityCreateWalletBinding
import com.coindex.utils.StorageUtils
import com.coindex.utils.ToastUtils

/**
 * 创建钱包
 */
class CreateWalletActivity : BindingActivity<ActivityCreateWalletBinding>() {

    private var isPwdHide=true
    private var isPwdAgainHide=true
    private var isReadAgreement=StorageUtils.loadAgreement()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivCreateWalletBack.setOnClickListener {
            finish()
        }
        //显示隐藏密码
        binding.ivCreateWalletPasswordSwitch.setOnClickListener{ view ->
            view as ImageView
            if (isPwdHide){
                isPwdHide=false
                view.setImageResource(R.drawable.ic_password_show)
                binding.etCreateWalletPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                isPwdHide=true
                view.setImageResource(R.drawable.ic_password_hide)
                binding.etCreateWalletPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etCreateWalletPassword.clearFocus()
            binding.etCreateWalletPassword.requestFocus()
            binding.etCreateWalletPassword.setSelection(binding.etCreateWalletPassword.text.length)
        }
        //显示隐藏重复密码
        binding.ivCreateWalletPasswordAgainSwitch.setOnClickListener { view ->
            view as ImageView
            if (isPwdAgainHide){
                isPwdAgainHide=false
                view.setImageResource(R.drawable.ic_password_show)
                binding.etCreateWalletPasswordAgain.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                isPwdAgainHide=true
                view.setImageResource(R.drawable.ic_password_hide)
                binding.etCreateWalletPasswordAgain.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // 刷新软键盘
            binding.etCreateWalletPasswordAgain.clearFocus()
            binding.etCreateWalletPasswordAgain.requestFocus()
            binding.etCreateWalletPasswordAgain.setSelection(binding.etCreateWalletPasswordAgain.text.length)
        }
        //同意服务协议
        binding.cbCreateWallet.isChecked=isReadAgreement
        binding.cbCreateWallet.setOnCheckedChangeListener { buttonView, isChecked ->
            isReadAgreement=isChecked
            StorageUtils.saveAgreement(isReadAgreement)
        }

        binding.tvCreateWalletAgreement.setOnClickListener {
            ToastUtils.show(this,"查看条款")
        }

        binding.tvCreateWalletSubmit.setOnClickListener {
            if (!checkSubmitState()){
                ToastUtils.showCenter(this@CreateWalletActivity,"创建成功")
                StorageUtils.saveWords("aa bb cc dd ee ff gg hh qq ww ee rr")
                startActivity(Intent(this@CreateWalletActivity,BackupWordsActivity::class.java))
                finish()
            }
        }


    }
    private fun checkSubmitState():Boolean{
        if (binding.etCreateWalletName.text.isEmpty()){
            ToastUtils.showCenter(this@CreateWalletActivity,"钱包名不能为空")
            binding.etCreateWalletName.clearFocus()
            binding.etCreateWalletName.requestFocus()
            binding.etCreateWalletName.setSelection(binding.etCreateWalletPasswordAgain.text.length)
            return false
        }
        if (binding.etCreateWalletPassword.text.length<8){
            ToastUtils.showCenter(this@CreateWalletActivity,"密码长度不能小于8位")
            binding.etCreateWalletPassword.clearFocus()
            binding.etCreateWalletPassword.requestFocus()
            binding.etCreateWalletPassword.setSelection(binding.etCreateWalletPasswordAgain.text.length)
            return false
        }
        if (!TextUtils.equals(binding.etCreateWalletPassword.text ,binding.etCreateWalletPasswordAgain.text)){
            ToastUtils.showCenter(this@CreateWalletActivity,"2次密码输入不一致")
            binding.etCreateWalletPasswordAgain.clearFocus()
            binding.etCreateWalletPasswordAgain.requestFocus()
            binding.etCreateWalletPasswordAgain.setSelection(binding.etCreateWalletPasswordAgain.text.length)
            return false
        }
        if (binding.etCreateWalletPasswordAgain.text.length<8){
            ToastUtils.showCenter(this@CreateWalletActivity,"密码长度不能小于8位")
            binding.etCreateWalletPasswordAgain.clearFocus()
            binding.etCreateWalletPasswordAgain.requestFocus()
            binding.etCreateWalletPasswordAgain.setSelection(binding.etCreateWalletPasswordAgain.text.length)
            return false
        }
        if (!isReadAgreement){
            ToastUtils.showCenter(this@CreateWalletActivity,"请阅读并同意服务协议")
            return false
        }
        return true
    }
}