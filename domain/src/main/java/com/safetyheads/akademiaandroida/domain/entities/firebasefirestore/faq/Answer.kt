package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

import java.util.Date

data class Answer(
    var answerDate: Date = Date(),
    var publish: Boolean = false,
    var text: String = ""
)
