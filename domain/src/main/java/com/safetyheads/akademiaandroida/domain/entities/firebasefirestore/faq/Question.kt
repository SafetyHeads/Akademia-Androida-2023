package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

import java.sql.Date

data class Question(
    //= Date(java.util.Date().time)
    val questionDate: Date,
    val publish: Boolean,
    var text: String,
    val userId: String
)
