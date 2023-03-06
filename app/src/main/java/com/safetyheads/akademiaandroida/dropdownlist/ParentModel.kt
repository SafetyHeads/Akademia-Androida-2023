package com.safetyheads.akademiaandroida.dropdownlist

class ParentModel {
    private var name: String? = null
    private var itemList = ArrayList<ChildModel>()

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getItemList(): ArrayList<ChildModel>? {
        return itemList
    }

    fun setItemList(itemList: ArrayList<ChildModel>) {
        this.itemList = itemList
    }
}