package com.prospection.coding.assignment.dto.paragraph

data class ViolationsResult(
    val charactersCount: Int,
    val wordsCount: Int,
    val verbsCount: Int,
    val sentencesCount: Int,
    val paragraphSentencesCount: Int,
    val paragraphSuffixesCount: Int
)