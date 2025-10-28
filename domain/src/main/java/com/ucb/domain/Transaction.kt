package com.ucb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: Int = 0,
    val name: String,
    val amount: Double,
    val description: String,
    val date: String
)