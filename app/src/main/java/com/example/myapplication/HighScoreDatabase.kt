package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.PlayerDao
import com.example.myapplication.dao.PlayerScoreDao
import com.example.myapplication.dao.ScoreDao
import com.example.myapplication.models.Player
import com.example.myapplication.models.Score

@Database( //tylko punkty i gracze są encjami, klasa pośrednicząca nie jest encją
    entities = [Score::class, Player::class],
    version = 1,
    exportSchema = false
)
abstract class HighScoreDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun playerScoreDao(): PlayerScoreDao
    abstract fun scoreDao(): ScoreDao
    companion object {
        @Volatile
        private var Instance: HighScoreDatabase? = null
        fun getDatabase(context: Context): HighScoreDatabase {
            return Room.databaseBuilder(
                context,
                HighScoreDatabase::class.java,
                "highscore_database"
            )
                .build().also { Instance = it }

        }
    }
}