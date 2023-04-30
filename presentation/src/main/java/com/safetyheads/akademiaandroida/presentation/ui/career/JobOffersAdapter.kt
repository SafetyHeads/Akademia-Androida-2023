package com.safetyheads.akademiaandroida.presentation.ui.career

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.safetyheads.akademiaandroida.presentation.databinding.JobOfferListItemBinding
import com.safetyheads.akademiaandroida.domain.entities.JobOffer

class JobOffersAdapter(val onButtonClick: (String) -> Unit): RecyclerView.Adapter<JobOffersAdapter.JobOffersViewHolder>() {

    private val jobOfferList = ArrayList<JobOffer>()

    fun setJobOffers(list: List<JobOffer>) {
        jobOfferList.clear()
        jobOfferList.addAll(list)
        notifyDataSetChanged()
    }

    class JobOffersViewHolder(binding: JobOfferListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val jobTitle = binding.jobTitle
        val jobTechnology = binding.jobTechnologies
        val jobExperience = binding.jobExperience
        val jobSalary = binding.jobSalary
        val button = binding.button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobOffersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = JobOfferListItemBinding.inflate(
            inflater,
            parent,
            false
        )
        return JobOffersViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return  jobOfferList.size
    }

    override fun onBindViewHolder(holder: JobOffersViewHolder, position: Int) {
        holder.jobTitle.text = jobOfferList[position].jobTitle
        holder.jobTechnology.text = jobOfferList[position].jobTechnology
        holder.jobExperience.text = jobOfferList[position].jobExperience
        holder.jobSalary.text = jobOfferList[position].jobSalary
        holder.button.setOnClickListener {
            onButtonClick(jobOfferList[position].jobUrl)
        }
    }
}
