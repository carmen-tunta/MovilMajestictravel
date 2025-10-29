package com.ucb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val title: String,
    val posterPath: String,
    val overview: String,
)
