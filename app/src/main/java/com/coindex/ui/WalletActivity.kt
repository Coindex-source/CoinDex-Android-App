package com.coindex.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.coindex.basic.BindingActivity
import com.coindex.databinding.ActivityWalletBinding
import com.coindex.utils.FileUtils
import com.coindex.utils.LogUtils.logD
import com.coindex.utils.LogUtils.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.File


/**
 *
 */
class WalletActivity : BindingActivity<ActivityWalletBinding>() {

    lateinit var fileFolder:File
    lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      
    }

}