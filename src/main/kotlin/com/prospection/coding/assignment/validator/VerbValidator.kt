package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class VerbValidator {
    fun isVerb(word: String): Boolean {
        val verbLength = word.length in 4..5
        val hasVerbTense by lazy { word.matches(verbSuffix) }
        return  verbLength and hasVerbTense
    }

    fun isNotVerb(word: String): Boolean {
        val verbLength = word.length in 4..5
        val notVerbTense by lazy { word.matches(nonVerbSuffix) }
        return  verbLength and notVerbTense
    }

    companion object {
        private val verbSuffix = ".*[drl]".toRegex()
        private val nonVerbSuffix = ".*[^drl]".toRegex()
    }
}