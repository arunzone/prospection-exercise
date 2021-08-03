package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class VerbValidator {
    fun isVerb(word: String): Boolean {
        return word.matches(verbSuffix)
    }

    fun verbSized(word: String): Boolean {
        return word.length in 4..5
    }

    companion object {
        private val verbSuffix = ".*[drl]".toRegex()
    }
}