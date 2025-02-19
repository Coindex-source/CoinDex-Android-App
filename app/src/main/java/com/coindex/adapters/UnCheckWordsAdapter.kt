package com.coindex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.coindex.R
import com.coindex.beans.Word
import com.coindex.databinding.ItemUncheckWordsBinding
import com.coindex.utils.LogUtils.logE

class UnCheckWordsAdapter(private val wordList: List<Word>,private val onItemClickListener: (Int) -> Unit) :
    Adapter<UnCheckWordsAdapter.UnCheckWordsViewHolder>() {

    inner class UnCheckWordsViewHolder(private val binding: ItemUncheckWordsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word, enable: Boolean,position: Int) {
            binding.apply {
                tvItemWordsName.text = word.name
                tvItemWordsName.setBackgroundResource(if (enable) R.drawable.shape_white_corners_10_stroke_1 else R.drawable.shape_gray_btn_corners_10)
                tvItemWordsName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (enable) R.color.text_title else R.color.text_subtitle
                    )
                )
                // 设置点击事件
                root.setOnClickListener {
                    if (enable) onItemClickListener(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnCheckWordsViewHolder {
        val binding = ItemUncheckWordsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UnCheckWordsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: UnCheckWordsViewHolder, position: Int) {
        holder.bind(wordList[position], wordList[position].enable,position)
    }

}