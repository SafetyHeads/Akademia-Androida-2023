package com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DropDownListViewModel(private val loadItemsToDropDownListUseCase: LoadItemsToDropDownListUseCase) :
    ViewModel() {

    val items: Flow<List<ParentModel>> = flow {
        val latestItems = loadItemsToDropDownListUseCase.execute()
        emit(latestItems)
    }

}