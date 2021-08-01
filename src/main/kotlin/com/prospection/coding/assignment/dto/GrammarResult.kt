package com.prospection.coding.assignment.dto

data class GrammarResult(
    val violations: ViolationsResult,
    val verbsCount: Int,
    val nounsCount: Int,
    val prepositionsCount: Int,
)