package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.controller.GrammarResult
import com.prospection.coding.assignment.validator.AlphabetValidator
import com.prospection.coding.assignment.validator.VerbValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SentenceProcessor(
    @Autowired private val verbValidator: VerbValidator,
    @Autowired private val alphabetValidator: AlphabetValidator) {

    companion object{
        private val wordSeparator = "(\\s)+".toRegex()
    }

    fun process(sentence: String): GrammarResult {
        val (words, validWords) = validWordsFrom(sentence)
        val verbsCount = validWords.count { verbValidator.isVerb(it) }
        val invalidCharactersCount = words.size-validWords.size
        return GrammarResult(
            invalidCharactersCount=invalidCharactersCount,
            verbsCount=verbsCount)
    }

    private fun validWordsFrom(sentence: String): Pair<List<String>, List<String>> {
        val words = sentence.split(wordSeparator)
        val validWords = words.filter { alphabetValidator.isValid(it) }
        return Pair(words, validWords)
    }
}