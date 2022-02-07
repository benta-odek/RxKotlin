package com.odhiambodevelopers.rxkotlin.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.odhiambodevelopers.rxkotlin.databinding.RxRowBinding

class DetailsAdapter : ListAdapter<DetailsEntity, DetailsAdapter.MyViewHolder>(DiffUtilCallback) {

    //Compare old and new data
    object DiffUtilCallback : DiffUtil.ItemCallback<DetailsEntity>() {
        override fun areItemsTheSame(oldItem: DetailsEntity, newItem: DetailsEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DetailsEntity, newItem: DetailsEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //Connects the data with the views
    inner class MyViewHolder(private val binding: RxRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(details: DetailsEntity?) {
            binding.tvName.text = details?.name
            binding.tvWeight.text = details?.weight
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RxRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }
}