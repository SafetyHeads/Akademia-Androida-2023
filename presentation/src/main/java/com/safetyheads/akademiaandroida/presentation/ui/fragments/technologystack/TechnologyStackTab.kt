package com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack

import com.safetyheads.akademiaandroida.domain.entities.technologystack.TechnologyStackType

enum class TechnologyStackTab {
    Mobile, Web, Others
}
fun TechnologyStackTab.toTechnologyStackType() = when(this) {
    TechnologyStackTab.Mobile -> TechnologyStackType.Mobile
    TechnologyStackTab.Web -> TechnologyStackType.Web
    TechnologyStackTab.Others -> TechnologyStackType.Others
}