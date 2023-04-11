package com.safetyheads.presentation.activities.activitieslist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safetyheads.akademiaandroida.databinding.CardBinding

class ActivitiesAdapter(private val list: List<String>, private val context: Context) :
    RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder>() {

    class MyViewHolder(binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewCard = binding.textViewCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardBinding = CardBinding.inflate(inflater, parent, false)
        return MyViewHolder(cardBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]
        holder.textViewCard.text = item

        holder.itemView.setOnClickListener {
            val activityClass = Class.forName(item)
            val intentOpenActivity = Intent(context, activityClass)
            context.startActivity(intentOpenActivity)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}