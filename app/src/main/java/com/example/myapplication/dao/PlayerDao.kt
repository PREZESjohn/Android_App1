package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.models.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    //jeżeli zwraca Long to jest to id nowego obiektu
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player) : Long
    //są też adnotacje @Delete, @Update...
    //metoda, która zwraca Flow nie musi być wstrzymująca
    @Query("SELECT * from players WHERE playerId = :playerId")
    fun getPlayerStream(playerId: Int): Flow<Player>
    //metoda, która nie zwraca Flow musi być wstrzymująca
    @Query("SELECT * from players WHERE email = :email")
    suspend fun getPlayersByEmail(email: String): List<Player>

    @Update
    suspend fun updatePlayer(player: Player): Int
}