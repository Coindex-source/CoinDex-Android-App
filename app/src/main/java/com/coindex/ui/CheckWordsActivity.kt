package com.coindex.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coindex.R
import com.coindex.adapters.BackupWordsAdapter
import com.coindex.adapters.CheckWordsAdapter
import com.coindex.adapters.UnCheckWordsAdapter
import com.coindex.basic.BindingActivity
import com.coindex.beans.Word
import com.coindex.databinding.ActivityCheckWordsBinding
import com.coindex.utils.GridSpacingItemDecoration
import com.coindex.utils.IntExt.dp2px
import com.coindex.utils.LogUtils.logE
import com.coindex.utils.NoDoubleClickListener
import com.coindex.utils.StorageUtils
import com.coindex.utils.ToastUtils

/**
 * 确认助记词
 */
class CheckWordsActivity : BindingActivity<ActivityCheckWordsBinding>() {

    private var adapter1: CheckWordsAdapter?=null
    private var adapter2: UnCheckWordsAdapter?=null
    private var layoutManager1: GridLayoutManager?=null
    private var layoutManager2: GridLayoutManager?=null
    private var words= StorageUtils.loadWords()//助记词字符串
    private var wordList= mutableListOf<Word>()//原始正确的
    private var wordList1= mutableListOf<Word>()//用户选择的
    private var wordList2= mutableListOf<Word>()//随机打乱的
    private var word:Word?=null
    private val spanCount=3

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivCheckWordsBack.setOnClickListener {
            finish()
        }
        binding.tvCheckWordsSubmit.setOnClickListener(NoDoubleClickListener(3000){
            if (diffList(wordList,wordList1)){
                ToastUtils.showCenter(this@CheckWordsActivity,"确认成功")
            }else{
                ToastUtils.showCenter(this@CheckWordsActivity,"确认失败")
            }
        })

        val splitList = words.split(" ")
        splitList.forEachIndexed{ _,name ->
            wordList.add(Word(0,name))
            wordList2.add(Word(0,name))
        }
        wordList2.shuffle()
        wordList2.forEachIndexed { index, _ ->
            wordList2[index].index=index
        }

        layoutManager1= GridLayoutManager(this@CheckWordsActivity,spanCount,
            RecyclerView.VERTICAL,false)
        adapter1= CheckWordsAdapter(wordList1){
            word=wordList1.removeAt(wordList1.lastIndex)
            wordList2[word!!.index].enable=true

            adapter1!!.notifyItemChanged(wordList1.size)
            adapter1!!.notifyItemRangeChanged(wordList1.size-1,wordList1.size)

            adapter2!!.notifyItemChanged(word!!.index)
            adapter2!!.notifyItemRangeChanged(word!!.index,1)
        }
        val spacing1 = 2.dp2px(this@CheckWordsActivity)
        binding.rvCheckWords1.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing1, false))
        binding.rvCheckWords1.layoutManager=layoutManager1
        binding.rvCheckWords1.adapter=adapter1

        layoutManager2= GridLayoutManager(this@CheckWordsActivity,spanCount,
            RecyclerView.VERTICAL,false)
        adapter2= UnCheckWordsAdapter(wordList2){ index ->
            word=wordList2[index]
            word!!.enable=false
            wordList1.add(word!!)

            adapter1!!.notifyItemChanged(wordList1.size)
            adapter1!!.notifyItemRangeChanged(wordList1.size-2,2)
            adapter2!!.notifyItemChanged(index)
            adapter2!!.notifyItemRangeChanged(index,1)
        }
        val spacing2 = 8.dp2px(this@CheckWordsActivity)
        binding.rvCheckWords2.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing2, false))
        binding.rvCheckWords2.layoutManager=layoutManager2
        binding.rvCheckWords2.adapter=adapter2

    }

    private fun diffList(wordList: MutableList<Word>, wordList1: MutableList<Word>): Boolean {
        if (wordList.size!=wordList1.size){
            return false
        }
        wordList.forEachIndexed { index, word ->
            if (!TextUtils.equals(word.name , wordList1[index].name)){
                return false
            }
        }
        return true
    }
}