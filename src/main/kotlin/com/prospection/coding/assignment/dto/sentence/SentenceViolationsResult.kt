package com.prospection.coding.assignment.dto.sentence

data class SentenceViolationsResult(
    val charactersCount: Int,
    val wordsCount: Int,
    val sentencesCount: Int,
    val verbsCount: Int,
    val paragraphSuffixesCount: Int,
)