package com.safetyheads.akademiaandroida.domain.entities

data class JobOffer(
    val jobId: Int,
    val jobTitle: String,
    val jobTechnology: String,
    val jobExperience: String,
    val jobSalary: String,
    val jobUrl: String
)
