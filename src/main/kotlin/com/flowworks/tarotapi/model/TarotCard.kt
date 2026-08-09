package com.flowworks.tarotapi.model

import kotlinx.serialization.Serializable

@Serializable
data class Orientation(
    val keywords: List<String>,
    val meaning: String,
    val reflection: String
)

@Serializable
data class TarotCard(
    val id: String,
    val name: String,
    val suit: String,
    val rank: String? = null,
    val value: Int,
    val upright: Orientation,
    val reversed: Orientation
)

@Serializable
data class CardListResponse(val count: Int, val cards: List<TarotCard>)

@Serializable
data class ErrorResponse(val error: String)
