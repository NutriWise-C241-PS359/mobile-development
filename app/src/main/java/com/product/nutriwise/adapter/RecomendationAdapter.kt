package com.product.nutriwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecomendationAdapter(private val listRecomendation: ArrayList<Recomendation>) :
    RecyclerView.Adapter<ListRecomendationAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowRecomendationBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (category, name, calorie) = listRecomendation[position]
        holder.binding.tvItemCategory.text = category
        holder.binding.tvItemName.text = name
        holder.binding.tvItemCalorie.text = calorie
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRecomendation[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listRecomendation.size

    class ListViewHolder(var binding: ItemRowRecomendationBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Recomendation)
    }
}