package com.example.myapplication.repositories

import com.example.myapplication.dao.PlayerScoreDao
import com.example.myapplication.models.PlayerWithScore
import kotlinx.coroutines.flow.Flow

class PlayersScoresRepositoryImpl(private val playerScoreDao: PlayerScoreDao): PlayersScoresRepository {
    override fun loadPlayersWithScores(): Flow<List<PlayerWithScore>> = playerScoreDao.loadPlayersWithScores()


}