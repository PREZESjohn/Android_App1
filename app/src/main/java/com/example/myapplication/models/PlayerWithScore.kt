package com.example.myapplication.models

data class PlayerWithScore(
    val scoreId: Long,
    val playerId: Long,
    val playerName: String,
    val scoreValue: Long
)