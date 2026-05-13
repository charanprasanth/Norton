package com.charan.norton.features.genie.domain.repository

interface GenieRepository {
    suspend fun analyseMessage(input: String): String
}