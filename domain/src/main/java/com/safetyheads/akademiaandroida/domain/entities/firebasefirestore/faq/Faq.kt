package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

data class Faq(
    val answer: Answer,
    val publish: Boolean,
    val question: Question,
    val type: Type
)
