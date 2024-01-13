package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.models.PlayerWithScore
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerScoreDao {
    //złączenie tabel i pobranie danych do klasy pośredniczącej
    @Query(
        "SELECT players.playerId AS playerId, scores.scoreId AS scoreId, players.name AS playerName, scores.value AS scoreValue " +
                "FROM players, scores WHERE players.playerId = scores.playerId ORDER BY scoreValue ASC"
    )
    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>
}