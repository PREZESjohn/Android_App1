package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.models.PlayerWithScore
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerScoreDao {
    //złączenie tabel i pobranie danych do klasy pośredniczącej
    @Query(
        "SELECT players.playerId AS playerId, scores.scoreId AS scoreId " +
                "FROM players, scores WHERE players.playerId = scores.playerId"
    )
    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>
}