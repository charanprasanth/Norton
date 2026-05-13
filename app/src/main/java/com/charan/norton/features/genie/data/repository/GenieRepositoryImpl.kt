package com.charan.norton.features.genie.data.repository

import android.util.Log
import com.anthropic.client.AnthropicClient
import com.anthropic.models.messages.CacheControlEphemeral
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.Model
import com.anthropic.models.messages.TextBlockParam
import com.charan.norton.features.genie.domain.repository.GenieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenieRepositoryImpl @Inject constructor(
    private val client: AnthropicClient
) : GenieRepository {

    override suspend fun analyseMessage(input: String): String = withContext(Dispatchers.IO) {
        val systemPrompt = """
            You are a scam detection engine inside Norton Genie, a security app. Analyze the given message, URL, or email for fraud, phishing, and scam indicators.

            Respond ONLY with a valid JSON object. No extra text, no markdown, no code blocks:
            {"risk":"SAFE","confidence":95,"reason":"Plain English explanation in 1-2 sentences."}

            RISK classification:
            - SAFE: Known brand, verified domain (amazon.com, google.com), normal language, no urgency, no credential requests. confidence 75-99
            - SUSPICIOUS: Unknown sender, mild urgency, shortened URLs, slightly misspelled domains, unverifiable claims. confidence 40-74
            - DANGEROUS: Fake or spoofed domains (usps-track.help, paypa1.com), impersonates real brands, demands immediate action, requests passwords/OTP/card details. confidence 75-99

            Red flags that increase risk:
            - Domain does not match the brand it claims to be (amazon-support.net vs amazon.com)
            - Urgency phrases: "within 24 hours", "immediately", "your account will be closed"
            - Requests for: password, OTP, credit card, SSN, personal details
            - Suspicious TLDs: .help, .xyz, .click, .info combined with brand names
            - Grammar errors mixed with urgent tone
            - Shortened or redirected URLs

            reason rules:
            - 1-2 sentences only
            - Plain English, no jargon
            - Be specific: mention the domain, the urgency phrase, or the red flag you detected
            - Do not be generic like "this looks suspicious"

            CRITICAL: Return raw JSON only. Do not wrap in ```json or ``` code blocks. The response must start with { and end with }
        """.trimIndent()

        val response = client.messages().create(
            MessageCreateParams.builder()
                .model(Model.CLAUDE_HAIKU_4_5_20251001)
                .maxTokens(256L)
                .systemOfTextBlockParams(listOf(
                    TextBlockParam.builder()
                        .text(systemPrompt)
                        .cacheControl(CacheControlEphemeral.builder().build())
                        .build()
                ))
                .addUserMessage(input)
                .build()
        )

        response.content()
            .mapNotNull { block -> block.text().orElse(null)?.text() }
            .firstOrNull()
            .also { Log.d("SCAM_RESULT", it ?: "") }
            ?: ""
    }
}