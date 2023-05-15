package com.safetyheads.akademiaandroida.domain.entities.faqs

enum class FaqTab {
    Benefits, Delegations, AMA
}

fun FaqTab.toFaqType() = when(this) {
    FaqTab.Benefits -> FaqType.Benefits
    FaqTab.Delegations -> FaqType.Delegations
    FaqTab.AMA -> FaqType.AMA
}