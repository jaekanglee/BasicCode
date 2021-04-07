package com.example.recyclerviewpagingsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpagingsample.databinding.ItemTestBinding

class GeneraRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val fieldItems: ArrayList<TestModelEntity> by lazy {
        ArrayList()
    }

    fun addItems(items: ArrayList<TestModelEntity>) {
        fieldItems.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemTestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.bind(fieldItems[position])
        }
    }

    override fun getItemCount(): Int {
        return fieldItems.size
    }


    class ItemViewHolder(
        private val binding: ItemTestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestModelEntity) {
            binding.textTitle.text = item.title
            binding.executePendingBindings()
        }
    }
}