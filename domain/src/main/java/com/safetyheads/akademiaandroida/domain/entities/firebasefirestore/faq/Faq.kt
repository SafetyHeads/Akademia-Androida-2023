package com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq

data class Faq(
    var answer: Answer = Answer(),
    var publish: Boolean = true,
    var question: Question = Question(),
    var type: String = ""
)
