package com.example.myapplication.repositories

import com.example.myapplication.models.PlayerWithScore
import kotlinx.coroutines.flow.Flow

interface PlayersScoresRepository {

    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>
}