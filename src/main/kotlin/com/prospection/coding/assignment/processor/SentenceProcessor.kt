package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.controller.GrammarResult
import com.prospection.coding.assignment.validator.VerbValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SentenceProcessor(@Autowired private val verbValidator: VerbValidator) {
    companion object{
        private val wordSeparator = "(\\s)+".toRegex()
    }
    fun process(sentence: String): GrammarResult {
        val verbs = sentence.split(wordSeparator).count { verbValidator.isVerb(it) }
        return GrammarResult(verbs)
    }
}