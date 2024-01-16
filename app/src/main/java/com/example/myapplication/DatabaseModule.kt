package com.example.myapplication

import android.content.Context
import com.example.myapplication.dao.PlayerDao
import com.example.myapplication.dao.PlayerScoreDao
import com.example.myapplication.dao.ScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesScoreDao(highScoreDatabase: HighScoreDatabase): ScoreDao {
        return highScoreDatabase.scoreDao()
    }
    @Provides
    fun providersPlayerScoreDao(highScoreDatabase: HighScoreDatabase): PlayerScoreDao {
        return highScoreDatabase.playerScoreDao()
    }
    @Provides
    fun providersPlayerDao(highScoreDatabase: HighScoreDatabase): PlayerDao {
        return highScoreDatabase.playerDao()
    }
    @Provides
    @Singleton //instancja tworzona tylko raz
    fun provideHighScoreDatabase(
        @ApplicationContext applicationContext: Context //wstrzykiwanie kontekstu
        //aplikacji
    ): HighScoreDatabase {
        return HighScoreDatabase.getDatabase(applicationContext)
    }
}