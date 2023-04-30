package com.safetyheads.akademiaandroida.fragments.technologystack

import androidx.lifecycle.ViewModel
import com.safetyheads.domain.entities.technologystack.ParentModel
import com.safetyheads.domain.usecases.TechnologyStackUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TechnologyStackViewModel(private val loadItemsUseCase: TechnologyStackUseCase) : ViewModel() {

    private val selectedTab = MutableStateFlow(TechnologyStackTab.Mobile)
    val items: Flow<List<ParentModel>> = selectedTab.map {
        loadItemsUseCase.invoke(TechnologyStackUseCase.TechnologyStackParam(it.toTechnologyStackType()))
            .first().getOrDefault(emptyList())
    }

    fun tabSelected(tab: TechnologyStackTab) {
        selectedTab.tryEmit(tab)
    }

}