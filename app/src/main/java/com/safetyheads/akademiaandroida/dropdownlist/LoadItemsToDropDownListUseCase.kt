package com.safetyheads.akademiaandroida.dropdownlist

import com.safetyheads.domain.entities.technologystack.ChildModel
import com.safetyheads.domain.entities.technologystack.ParentModel
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
