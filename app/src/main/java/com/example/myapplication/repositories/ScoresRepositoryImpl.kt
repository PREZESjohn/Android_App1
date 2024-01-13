package com.example.myapplication.repositories

import com.example.myapplication.dao.ScoreDao
import com.example.myapplication.models.Score

class ScoresRepositoryImpl(private val scoreDao: ScoreDao) : ScoresRepository {
    override suspend fun insertScore(score: Score): Long = scoreDao.insert(score)

}