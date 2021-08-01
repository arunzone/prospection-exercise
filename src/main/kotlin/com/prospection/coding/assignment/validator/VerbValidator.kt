package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class VerbValidator {
    fun isVerb(word: String): Boolean {
        val verbLength = word.length in 4..5
        val hasVerbTense by lazy { word.matches(verbSuffix) }
        return  verbLength and hasVerbTense
    }

    companion object {
        private val verbSuffix = ".*[drl]".toRegex()
    }
}