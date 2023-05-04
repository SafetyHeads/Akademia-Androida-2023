package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

import java.util.Date

data class Question(
    var questionDate: Date = Date(),
    var publish: Boolean = true,
    var text: String = "",
    var userId: String = ""
)
