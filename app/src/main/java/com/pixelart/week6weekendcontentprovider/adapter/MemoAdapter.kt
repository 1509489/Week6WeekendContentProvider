package com.pixelart.week6weekendcontentprovider.adapter

import android.content.Context
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pixelart.week6weekendcontentprovider.R
import com.pixelart.week6weekendcontentprovider.data.entity.Memo

class MemoAdapter: ListAdapter<Memo, MemoAdapter.ViewHolder> (DIFF_CALLBACK){
    private lateinit var context:Context

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Memo> = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem.title == newItem.title && oldItem.memoBody == newItem.memoBody
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.memo_item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    //override fun getItemCount(): Int = memoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memo = getItem(position)

        holder.tvTitle.text = memo.title
        holder.tvBody.text = memo.memoBody
    }

    fun getMemoAt(position: Int):Memo = getItem(position)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvBody: TextView = itemView.findViewById(R.id.tvBody)
    }
}