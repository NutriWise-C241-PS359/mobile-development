package com.product.nutriwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.product.nutriwise.data.remote.response.DataItem
import com.product.nutriwise.databinding.ListHistoryMakananBinding
import com.product.nutriwise.ui.main.history.detail.HistoryDetailActivity

class HistoryDetailAdapter(private val list: ArrayList<DataItem>) : RecyclerView.Adapter<HistoryDetailAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
       val binding = ListHistoryMakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    inner class ListViewHolder(private val binding: ListHistoryMakananBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DataItem){
            binding.nameFood.text = item.food?.name
            binding.piKarbo.max = 100
            val progres1 = item.food?.carbohydrates
            binding.piKarbo.progress = progres1?.toInt() ?: 0
            binding.tvPiKarbo.text = String.format("%.2f", item.food?.carbohydrates)
            binding.piProtein.max = 100
            val progres2 = item.food?.protein
            binding.piProtein.progress = progres2?.toInt() ?: 0
            binding.tvPiProtein.text = String.format("%.2f", item.food?.protein)
            binding.piLemak.max = 100
            val progres3 = item.food?.fats
            binding.piLemak.progress = progres3?.toInt() ?: 0
            binding.tvPiLemak.text = String.format("%.2f", item.food?.fats)
            binding.piKalori.max = 1000
            val progres4 = item.food?.energy
            binding.piKalori.progress = progres4?.toInt() ?: 0
            binding.tvPiKalori.text = String.format("%.2f", item.food?.energy)
        }
    }
}