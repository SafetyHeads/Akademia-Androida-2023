package com.safetyheads.domain.entities.technologystack

data class ParentModel(var name: String, var itemList: List<ChildModel> = emptyList())
