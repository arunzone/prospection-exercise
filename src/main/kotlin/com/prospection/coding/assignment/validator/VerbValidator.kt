package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class VerbValidator {
    fun isVerb(word: String): Boolean {
        val verbLength = word.length in 4..5
        val hasVerbTense = word.endsWith("d") or word.endsWith("r") or word.endsWith("l")
        return hasVerbTense and verbLength
    }

}