package com.safetyheads.akademiaandroida

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.safetyheads.akademiaandroida.databinding.FontStylesItemBinding

class FontStylesAdapter(
    private var description: ArrayList<String>,
    private var listFonts: ArrayList<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FontStylesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    private inner class MyViewHolder(val binding: FontStylesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun MyFont(viewHolder: MyViewHolder, position: Int) {
        viewHolder.binding.name.text = description[position]
        viewHolder.binding.text.text = "This is " + description[position] + " font."
        viewHolder.binding.text.typeface = ResourcesCompat.getFont(viewHolder.binding.text.context, listFonts[position])
    }

    override fun getItemCount(): Int {
        return description.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return MyFont(holder as MyViewHolder, position)
    }
}