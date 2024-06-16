package com.product.nutriwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.product.nutriwise.databinding.CardHistoryBinding

//class HistoryAdapter(private val listHistory: ArrayList<History>) : RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>(){
//
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
//        val binding = CardHistoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (name, description, photo) = listHistory[position]
//        holder.binding.imgItemPhoto.setImageResource(photo)
//        holder.binding.tvItemName.text = name
//        holder.binding.tvItemDescription.text = description
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listHistory[holder.adapterPosition]) }
//    }
//
//    override fun getItemCount(): Int = listHistory.size
//
//    class ListViewHolder(var binding: CardHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
////        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
////        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
////        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: History)
//    }
//
//}