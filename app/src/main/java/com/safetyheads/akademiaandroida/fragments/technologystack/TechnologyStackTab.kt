package com.safetyheads.akademiaandroida.fragments.technologystack

import com.safetyheads.domain.entities.technologystack.TechnologyStackType

enum class TechnologyStackTab {
    Mobile, Web, Others
}
fun TechnologyStackTab.toTechnologyStackType() = when(this) {
    TechnologyStackTab.Mobile -> TechnologyStackType.Mobile
    TechnologyStackTab.Web -> TechnologyStackType.Web
    TechnologyStackTab.Others -> TechnologyStackType.Others
}