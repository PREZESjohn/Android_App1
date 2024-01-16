package com.example.myapplication.repositories

import com.example.myapplication.dao.ScoreDao
import com.example.myapplication.models.Score
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject
import javax.inject.Singleton


//class ScoresRepositoryImpl(private val scoreDao: ScoreDao) : ScoresRepository {
@Singleton
class ScoresRepositoryImpl @Inject constructor(private val scoreDao: ScoreDao) : ScoresRepository {
override suspend fun insertScore(score: Score): Long = scoreDao.insert(score)

}