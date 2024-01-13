package com.example.myapplication.repositories

import androidx.lifecycle.LiveData
import com.example.myapplication.models.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {

//    fun getCurrentPlayerId(): LiveData<Long>
//
//    fun setCurrentPlayerId(playerId: Long)
    fun getAllPlayersStream(): Flow<List<Player>>
    fun getPlayerStream(id: Int): Flow<Player?>
    suspend fun getPlayersByEmail(email: String): List<Player>
    suspend fun insertPlayer(player: Player) : Long

    suspend fun updatePlayer(player: Player): Int

}