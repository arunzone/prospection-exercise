package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class AlphabetValidator {
    fun isValid(word: String): Boolean {
        return word matches regex
    }

    companion object {
        private val regex = Regex("^[abcdefghijlmnopqurs.!]+$", RegexOption.IGNORE_CASE)
    }
}