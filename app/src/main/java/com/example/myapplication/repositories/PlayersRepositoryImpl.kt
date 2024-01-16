package com.example.myapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dao.PlayerDao
import com.example.myapplication.models.Player
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


//class PlayersRepositoryImpl(private val playerDao: PlayerDao) : PlayersRepository {
@Singleton
class PlayersRepositoryImpl @Inject constructor(private val playerDao: PlayerDao) : PlayersRepository {
    private val currentPlayerId = MutableLiveData<Long>()

    override fun getCurrentPlayerId(): LiveData<Long>{
        return currentPlayerId
    }
    override fun setCurrentPlayerId(playerId: Long){
        currentPlayerId.postValue(playerId)
    }

    override fun getAllPlayersStream(): Flow<List<Player>> {
        TODO("Not yet implemented")
    }
    override fun getPlayerStream(playerId: Int): Flow<Player?> =
        playerDao.getPlayerStream(playerId)
    override suspend fun getPlayersByEmail(email: String): List<Player> =
        playerDao.getPlayersByEmail(email)
    override suspend fun insertPlayer(player: Player): Long = playerDao.insert(player)
    override suspend fun updatePlayer(player: Player): Int =playerDao.updatePlayer(player)
}