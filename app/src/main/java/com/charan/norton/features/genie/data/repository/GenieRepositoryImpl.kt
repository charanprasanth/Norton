package com.charan.norton.features.genie.data.repository

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
            You are a scam detection engine inside Norton Genie. Analyse the given message, URL, email, or text for phishing, fraud, impersonation, scams, or malicious intent.
            Respond in EXACTLY this format with no markdown and no extra text:
            
            risk: SAFE
            confidence: 95
            reason: Plain English explanation in 1-2 sentences.
            
            Valid risk values:
            - SAFE
            - SUSPICIOUS
            - DANGEROUS
            - UNKNOWN
            
            Classification guidelines:
            
            SAFE:
            Trusted domains, normal communication, no urgency, no credential or payment requests.
            
            SUSPICIOUS:
            Unknown sender, shortened URLs, mild urgency, unusual claims, slightly suspicious domains, or weak scam indicators.
            
            DANGEROUS:
            Spoofed domains, impersonation, phishing attempts, credential requests, OTP/password/card requests, threats, or strong urgency.
            
            UNKNOWN:
            Random characters, gibberish, empty input, or content too vague to analyse.
            
            Confidence rules:
            - Integer from 0 to 100
            - Represents certainty in the classification
            - Use 0 only for UNKNOWN
            
            Red flags:
            - Fake or mismatched domains
            - Urgency phrases
            - Requests for passwords, OTPs, banking details, or personal information
            - Suspicious redirects or shortened URLs
            - Threats or account suspension warnings
            
            Reason rules:
            - 1-2 sentences only
            - Use plain English
            - Mention the specific red flag detected
            - Avoid generic explanations
            
            If the input is too vague or unclear, return:
            
            risk: UNKNOWN
            confidence: 0
            reason: Could not determine — input is too vague or unclear to analyse.
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
            ?: ""
    }
}