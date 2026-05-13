package com.charan.norton.features.genie.domain.usecase

import com.charan.norton.features.genie.domain.model.RiskLevel
import com.charan.norton.features.genie.domain.model.ScamResult
import com.charan.norton.features.genie.domain.repository.GenieRepository
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
            val fallback = ScamResult(RiskLevel.UNKNOWN, 0, "Unable to determine risk.")
            val risk = Regex("""risk:\s*(\w+)""").find(raw)?.groupValues?.get(1)
                ?: return fallback
            val confidence = Regex("""confidence:\s*(\d+)""").find(raw)?.groupValues?.get(1)?.toInt()
                ?: return fallback
            val reason = Regex("""reason:\s*(.+)""").find(raw)?.groupValues?.get(1)?.trim()
                ?: return fallback

            val clampedConfidence = confidence.coerceIn(0, 100)
            if (clampedConfidence == 0) return ScamResult(RiskLevel.UNKNOWN, 0, reason.ifEmpty { "Could not determine." })
            ScamResult(
                riskLevel = when (risk.uppercase()) {
                    "DANGEROUS" -> RiskLevel.DANGEROUS
                    "SUSPICIOUS" -> RiskLevel.SUSPICIOUS
                    else -> RiskLevel.SAFE
                },
                confidence = clampedConfidence,
                explanation = reason,
            )
        } catch (e: Exception) {
            ScamResult(RiskLevel.SUSPICIOUS, 50, "Unable to determine risk.")
        }
    }
}