package com.safetyheads.akademiaandroida.presentation.ui.fragments.font_style

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safetyheads.akademiaandroida.databinding.ItemFontStylesBinding

class FontStylesAdapter(
    private var description: ArrayList<String>,
    private var listFonts: ArrayList<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemFontStylesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    private inner class MyViewHolder(val binding: ItemFontStylesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun MyFont(viewHolder: MyViewHolder, position: Int) {
        viewHolder.binding.name.text = description[position]
        viewHolder.binding.text.text = "This is " + description[position] + " font."
        viewHolder.binding.text.setTextAppearance(listFonts[position])
    }

    override fun getItemCount(): Int {
        return description.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return MyFont(holder as MyViewHolder, position)
    }
}