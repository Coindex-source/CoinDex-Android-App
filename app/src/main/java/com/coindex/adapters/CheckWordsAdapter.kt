package com.coindex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.coindex.R
import com.coindex.beans.Word
import com.coindex.databinding.ItemCheckWordsBinding
import com.coindex.utils.LogUtils.logE
import com.coindex.utils.NoDoubleClickListener

class CheckWordsAdapter(private val wordList: List<Word>,private val onItemClickListener: (Word) -> Unit) :
    Adapter<CheckWordsAdapter.CheckWordsViewHolder>() {



    inner class CheckWordsViewHolder(private val binding: ItemCheckWordsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word,cancel:Boolean) {
            binding.apply {
                tvItemWordsName.text = word.name
                flItemWordsBg.setBackgroundResource(if (cancel) R.drawable.shape_pink_corners_10_stroke_1 else R.drawable.shape_white_corners_10_stroke_1)
                tvItemWordsName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (cancel) R.color.text_red else R.color.text_title
                    )
                )
                ivItemWordsDelete.visibility=if (cancel) View.VISIBLE else View.GONE
                // 设置点击事件
                root.setOnClickListener(NoDoubleClickListener {
                    if (cancel) onItemClickListener(word)
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckWordsViewHolder {
        val binding = ItemCheckWordsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CheckWordsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: CheckWordsViewHolder, position: Int) {
        holder.bind(wordList[position], itemCount==(position+1) )
    }

}
