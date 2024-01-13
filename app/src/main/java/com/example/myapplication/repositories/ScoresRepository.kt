package com.example.myapplication.repositories

import com.example.myapplication.models.Score

interface ScoresRepository {
    suspend fun insertScore(score: Score): Long
}