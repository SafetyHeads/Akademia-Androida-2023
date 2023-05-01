package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

import java.sql.Date

data class Answer(
    //= Date(java.util.Date().time)
    val answerDate: Date,
    val publish: Boolean,
    var text: String
)
