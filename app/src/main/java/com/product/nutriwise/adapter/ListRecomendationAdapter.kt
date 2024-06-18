package com.product.nutriwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.product.nutriwise.data.remote.response.ResultItem
import com.product.nutriwise.databinding.CardRecomendationBinding

class ListRecomendationAdapter(private val list: ArrayList<ResultItem>) : RecyclerView.Adapter<ListRecomendationAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private lateinit var onButtonClickCallback: OnButtonClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnButtonClickCallback(onButtonClickCallback: OnButtonClickCallback){
        this.onButtonClickCallback = onButtonClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    inner class ListViewHolder(private val binding: CardRecomendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultItem) {
            binding.tvNamefood.setText(item.name)
            binding.tvFoodcal2.setText(item.calorie.toString())
            binding.pilih.setOnClickListener {
                onButtonClickCallback.onItemClicked(item)
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultItem)
    }

    interface OnButtonClickCallback {
        fun onItemClicked(data: ResultItem)
    }
}