package com.charan.norton.features.genie.domain.usecase

import com.charan.norton.common.network.NetworkChecker
import com.charan.norton.features.genie.domain.model.RiskLevel
import com.charan.norton.features.genie.domain.model.ScamResult
import com.charan.norton.features.genie.domain.repository.GenieRepository
import javax.inject.Inject

class AnalyseMessageUseCase @Inject constructor(
    private val repository: GenieRepository,
    private val networkChecker: NetworkChecker
) {

    suspend operator fun invoke(input: String): ScamResult {
        if (!networkChecker.isConnected()) return localAnalyse(input)
        val raw = repository.analyseMessage(input)
        return parseResult(raw)
    }

    private fun localAnalyse(input: String): ScamResult {
        val text = input.lowercase()

        val dangerousPatterns = listOf(
            Regex("""https?://\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}"""),
            Regex("""\b(password|otp|cvv|pin|card\s*number|account\s*number)\b"""),
            Regex("""account.{0,40}(suspended|blocked|terminated|will be closed)"""),
            Regex("""[a-z0-9\-]+\.(help|xyz|click|info|top|live|online)/"""),
            Regex("""[/\-](verify|secure|login)[/\-]""")
        )

        val suspiciousPatterns = listOf(
            Regex("""(bit\.ly|tinyurl\.com|t\.co|goo\.gl)/"""),
            Regex("""\b(urgent|immediately|act now|verify now|expires today|limited time)\b"""),
            Regex("""\b(winner|you('ve| have) won|prize|lottery|claim your)\b"""),
            Regex("""\b(24h|24 hours|48 hours|within \d+ hours)\b"""),
        )

        return when {
            dangerousPatterns.any { it.containsMatchIn(text) } -> ScamResult(
                riskLevel = RiskLevel.DANGEROUS,
                confidence = 70,
                explanation = "Offline analysis: high-risk patterns detected. Connect to internet for full AI scan."
            )
            suspiciousPatterns.any { it.containsMatchIn(text) } -> ScamResult(
                riskLevel = RiskLevel.SUSPICIOUS,
                confidence = 50,
                explanation = "Offline analysis: suspicious patterns detected. Connect to internet for accurate AI-powered scan."
            )
            else -> ScamResult(
                riskLevel = RiskLevel.UNKNOWN,
                confidence = 0,
                explanation = "Offline analysis: cannot determine risk without internet. Connect to internet for an AI-powered scan."
            )
        }
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