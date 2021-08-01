package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class WordValidator {
    fun isWord(word: String): Boolean {
        return word.length in 1..8
    }
}