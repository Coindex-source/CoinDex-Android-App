package com.coindex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.coindex.R
import com.coindex.adapters.BackupWordsAdapter
import com.coindex.basic.BindingActivity
import com.coindex.beans.Word
import com.coindex.databinding.ActivityBackupWordsBinding
import com.coindex.utils.GridSpacingItemDecoration
import com.coindex.utils.IntExt.dp2px
import com.coindex.utils.StorageUtils

/**
 * 备份助记词
 */
class BackupWordsActivity : BindingActivity<ActivityBackupWordsBinding>() {

    private var adapter: BackupWordsAdapter?=null
    private var layoutManager:GridLayoutManager?=null
    private var words= StorageUtils.loadWords()
    private var wordList= mutableListOf<Word>()
    private val spanCount=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBackupWordsBack.setOnClickListener {
            finish()
        }
        binding.tvBackupWordsSubmit.setOnClickListener {
            startActivity(Intent(this@BackupWordsActivity,CheckWordsActivity::class.java))
        }

        val splitList = words.split(" ")
        splitList.forEach {
            wordList.add(Word(0,it))
        }
        layoutManager= GridLayoutManager(this@BackupWordsActivity,spanCount,RecyclerView.VERTICAL,false)
        adapter=BackupWordsAdapter(wordList)
        val spacing = 20.dp2px(this@BackupWordsActivity)
        binding.rvBackupWords.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, false))
        binding.rvBackupWords.layoutManager=layoutManager
        binding.rvBackupWords.adapter=adapter

    }
}