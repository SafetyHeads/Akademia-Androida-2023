package com.safetyheads.akademiaandroida.domain.entities.faqs

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Type

enum class FaqTab {
    Benefits, Delegations, AMA
}

fun FaqTab.toFaqType() = when (this) {
    FaqTab.Benefits -> Type.BENEFITS
    FaqTab.Delegations -> Type.DELEGATIONS
    FaqTab.AMA -> Type.AMA
}