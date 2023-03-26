package com.safetyheads.akademiaandroida.dropdownlist

import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ChildModel
import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ParentModel
import kotlinx.coroutines.coroutineScope

class LoadItemsToDropDownListUseCase {

    private val testList: List<ParentModel> = listOf(
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C"))),
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C"))),
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C")))
    )

    suspend fun execute(): List<ParentModel> {
        return coroutineScope {
            return@coroutineScope testList
        }
    }
}