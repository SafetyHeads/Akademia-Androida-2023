package com.safetyheads.akademiaandroida.presentation.ui.activities.activitieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.CardBinding

class FragmentsAdapter(private val list: List<Fragment>, private val context: Context) :
    RecyclerView.Adapter<FragmentsAdapter.MyViewHolder>() {

    class MyViewHolder(binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewCard = binding.textViewCard
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardBinding = CardBinding.inflate(inflater, parent, false)
        return MyViewHolder(cardBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemFragment = list[position]
        val blankFragment = BlankFragment()
        val name = list[position]::class.java.simpleName
        holder.textViewCard.text = name

        val fragmentManager = (context as AppCompatActivity).supportFragmentManager

        holder.itemView.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout2, itemFragment)
            fragmentTransaction.replace(R.id.frame_layout3, blankFragment)
            fragmentTransaction.commit()
            holder.itemView.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}