package com.safetyheads.akademiaandroida.domain.entities

data class Media(
    val mediaId: String = "",
    val mediaTitle: String = "",
    val mediaPublishTime: String = "",
    val mediaUrl: String = "",
    val mediaType: MediaType? = null
)
