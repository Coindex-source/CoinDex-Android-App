package com.coindex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.coindex.beans.Word
import com.coindex.databinding.ItemBackupWordsBinding

class BackupWordsAdapter(private val wordList: List<Word>) :
    Adapter<BackupWordsAdapter.BackupWordsViewHolder>() {

    inner class BackupWordsViewHolder(private val binding: ItemBackupWordsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word,index:String) {
            binding.apply {
                tvItemBackupWordsIndex.text = index
                tvItemBackupWordsName.text = word.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackupWordsViewHolder {
        val binding = ItemBackupWordsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BackupWordsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: BackupWordsViewHolder, position: Int) {
        val indexNum = position + 1
        val index = String.format("%02d", indexNum)
        holder.bind(wordList[position], index)
    }
}