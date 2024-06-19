package com.product.nutriwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.product.nutriwise.data.remote.response.HistoryItem
import com.product.nutriwise.databinding.CardHistoryBinding
import com.product.nutriwise.utils.Utils

class HistoryAdapter(private val listHistory: ArrayList<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = CardHistoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listHistory[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int = listHistory.size

    class ListViewHolder(var binding: CardHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem) {
            binding.tvDateHistory.text = Utils.convertDateFormat(item.date.toString())
            binding.tvTotalcal.text = item.cal.toString()
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: HistoryItem)
    }

}