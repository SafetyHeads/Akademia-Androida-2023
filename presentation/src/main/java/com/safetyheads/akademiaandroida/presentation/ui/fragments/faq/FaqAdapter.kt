package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.presentation.R

class FaqAdapter(private val faqs: List<Faq>, private val onClick: (Faq) -> Unit) :
    RecyclerView.Adapter<FaqAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion : TextView = itemView.findViewById(R.id.tv_question)
        val questionConstraint : ConstraintLayout = itemView.findViewById(R.id.faq_question_item_constraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.faq_question_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return faqs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faq = faqs[position]
        holder.tvQuestion.text = faq.question.text

        holder.questionConstraint.setOnClickListener {
            onClick(faq)
        }
    }
}