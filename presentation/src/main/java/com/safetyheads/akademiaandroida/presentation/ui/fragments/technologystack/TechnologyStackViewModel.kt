package com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack

import androidx.lifecycle.ViewModel
import com.safetyheads.akademiaandroida.domain.entities.technologystack.ParentModel
import com.safetyheads.akademiaandroida.domain.usecases.GetTechnologyStackUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TechnologyStackViewModel(private val loadItemsUseCase: GetTechnologyStackUseCase) : ViewModel() {

    private val selectedTab = MutableStateFlow(TechnologyStackTab.Mobile)
    val items: Flow<List<ParentModel>> = selectedTab.map {
        loadItemsUseCase.invoke(GetTechnologyStackUseCase.TechnologyStackParam(it.toTechnologyStackType()))
            .first().getOrDefault(emptyList())
    }

    fun tabSelected(tab: TechnologyStackTab) {
        selectedTab.tryEmit(tab)
    }

}