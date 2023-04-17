package com.safetyheads.akademiaandroida.fragments.technologystack

import androidx.lifecycle.ViewModel
import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ParentModel
import kotlinx.coroutines.flow.*

class TechnologyStackViewModel(private val loadItemsUseCase: TechnologyStackUseCase) : ViewModel() {

    private val selectedTab = MutableStateFlow(TechnologyStackTab.Mobile)
    val items: Flow<List<ParentModel>> = selectedTab.map {
        loadItemsUseCase.execute(it)
    }

    fun tabSelected(tab: TechnologyStackTab) {
        selectedTab.tryEmit(tab)
    }

}