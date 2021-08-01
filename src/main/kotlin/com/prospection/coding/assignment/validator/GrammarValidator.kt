package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class GrammarValidator {
    fun isNoun(word: String): Boolean {
        return word.length in 6..8
    }
    fun isPreposition(word: String): Boolean {
        return word.length in 1..3
    }
}