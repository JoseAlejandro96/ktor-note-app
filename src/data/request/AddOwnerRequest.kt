package com.josh.data.request

data class AddOwnerRequest(
    val noteID: String,
    val owner: String
)