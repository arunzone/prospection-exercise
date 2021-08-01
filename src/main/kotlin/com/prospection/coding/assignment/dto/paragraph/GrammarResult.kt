package com.prospection.coding.assignment.dto.paragraph

data class GrammarResult(
    val violation: ViolationsResult,
    val verbsCount: Int,
    val nounsCount: Int,
    val prepositionsCount: Int,
)