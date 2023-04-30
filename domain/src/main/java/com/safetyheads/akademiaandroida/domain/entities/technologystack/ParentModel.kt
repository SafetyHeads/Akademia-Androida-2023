package com.safetyheads.akademiaandroida.domain.entities.technologystack

data class ParentModel(var name: String, var itemList: List<ChildModel> = emptyList())