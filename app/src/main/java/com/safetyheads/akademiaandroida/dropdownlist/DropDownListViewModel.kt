package com.safetyheads.akademiaandroida.dropdownlist

import androidx.lifecycle.ViewModel
import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ParentModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DropDownListViewModel(private val loadItemsToDropDownListUseCase: LoadItemsToDropDownListUseCase) : ViewModel() {

    val items: Flow<List<ParentModel>> = flow {
        val latestItems = loadItemsToDropDownListUseCase.execute()
        emit(latestItems)
    }

}