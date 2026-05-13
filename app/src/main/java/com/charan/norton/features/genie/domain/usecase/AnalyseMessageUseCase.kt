package com.charan.norton.features.genie.domain.usecase

import android.util.Log
import com.charan.norton.features.genie.domain.model.RiskLevel
import com.charan.norton.features.genie.domain.model.ScamResult
import com.charan.norton.features.genie.domain.repository.GenieRepository
import org.json.JSONObject
import javax.inject.Inject

class AnalyseMessageUseCase @Inject constructor(
    private val repository: GenieRepository
) {

    suspend operator fun invoke(input: String): ScamResult {
        val raw = repository.analyseMessage(input)
        return parseResult(raw)
    }

    private fun parseResult(raw: String): ScamResult {
        return try {
            val json = JSONObject(raw.trim())
            val riskLevel = when (json.getString("risk").uppercase()) {
                "DANGEROUS" -> RiskLevel.DANGEROUS
                "SUSPICIOUS" -> RiskLevel.SUSPICIOUS
                else -> RiskLevel.SAFE
            }
            ScamResult(
                riskLevel = riskLevel,
                confidence = json.getInt("confidence").coerceIn(0, 100),
                explanation = json.getString("reason"),
            )
        } catch (e: Exception) {
            Log.e("SCAM_RESULT", "Parse failed: ${e.message} | raw: $raw")
            ScamResult(RiskLevel.SUSPICIOUS, 50, "Unable to determine risk.")
        }
    }
}