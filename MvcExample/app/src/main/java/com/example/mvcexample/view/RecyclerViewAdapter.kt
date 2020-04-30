package com.example.mvcexample.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvcexample.R
import com.example.mvcexample.data.Model
import com.example.mvcexample.databinding.ItemRowBinding

//리사이클러뷰 어댑터
class RecyclerViewAdapter(var model: Model) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notifyChangedCallback: AdapterNotifyInterface by lazy {
        object : AdapterNotifyInterface {
            override fun notifyDataChanged(pos: Int) {
                if (pos < 0) { //0이하의 값이면 전체 노티파이
                    notifyDataSetChanged()
                } else { //특정 포지션이면 해당 포지션만 노티파이
                    notifyItemChanged(pos)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(
            binding,
            model,
            notifyChangedCallback
        )

    }

    override fun getItemCount(): Int {
        return model.itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind()
        }
    }


    class ItemViewHolder(
        val binding: ItemRowBinding,
        val model: Model,
        val adapterNotifyInterface: AdapterNotifyInterface
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {


        init {
            setListener()
        }

        fun setListener() {
            binding.textContents.setOnClickListener(this)
        }

        fun bind() {
            binding.textContents.text = model.getItemRow(adapterPosition)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.text_contents -> {
                    dataChangeFunc()
                }
            }
        }

        //클릭했을때 작동할 기능 펑션
        fun dataChangeFunc() {
            Log.d("dataChangeFunc","onClicked${adapterPosition}")
            model.clickedRowItem(adapterPosition)
            adapterNotifyInterface.notifyDataChanged(adapterPosition)
        }
    }

}