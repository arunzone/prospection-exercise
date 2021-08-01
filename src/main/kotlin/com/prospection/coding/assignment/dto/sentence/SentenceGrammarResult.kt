package com.prospection.coding.assignment.dto.sentence

data class SentenceGrammarResult(
    val violation: SentenceViolationsResult,
    val verbsCount: Int,
    val nounsCount: Int,
    val prepositionsCount: Int,
)