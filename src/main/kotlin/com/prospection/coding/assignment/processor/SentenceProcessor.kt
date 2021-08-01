package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.GrammarResult
import com.prospection.coding.assignment.dto.ViolationsResult
import com.prospection.coding.assignment.validator.AlphabetValidator
import com.prospection.coding.assignment.validator.GrammarValidator
import com.prospection.coding.assignment.validator.VerbValidator
import com.prospection.coding.assignment.validator.WordValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SentenceProcessor(
    @Autowired private val verbValidator: VerbValidator,
    @Autowired private val wordValidator: WordValidator,
    @Autowired private val grammarValidator: GrammarValidator,
    @Autowired private val alphabetValidator: AlphabetValidator
) {

    companion object {
        private val wordSeparator = "(\\s)+".toRegex()
    }

    infix fun process(sentence: String): GrammarResult {
        val (words, validWords, validAlphabets) = processWordsFrom(sentence)

        val verbsCount = validAlphabets.count { verbValidator.isVerb(it) }
        val nounsCount = validAlphabets.count { grammarValidator.isNoun(it) }
        val prepositionsCount = validAlphabets.count { grammarValidator.isPreposition(it) }

        val violations = violationsResultFrom(
            words,
            validWords,
            validAlphabets,
            listOf(verbsCount, nounsCount, prepositionsCount)
        )

        return GrammarResult(
            violations = violations,
            verbsCount = verbsCount,
            nounsCount = nounsCount,
            prepositionsCount = prepositionsCount
        )
    }

    private fun processWordsFrom(sentence: String): Triple<List<String>, List<String>, List<String>> {
        val words = sentence.split(wordSeparator)
        val validWords = words.filter { wordValidator.isWord(it) }
        val validAlphabets = validWords.filter { alphabetValidator.isValid(it) }
        return Triple(words, validWords, validAlphabets)
    }

    private fun violationsResultFrom(
        words: List<String>,
        validWords: List<String>,
        validAlphabets: List<String>,
        sentenceMetrics: List<Int>
    ): ViolationsResult {
        val invalidWordsCount = words.size - validWords.size
        val invalidCharactersCount = validWords.size - validAlphabets.size
        val inCompleteSentence = sentenceMetrics.any { it == 0 }
        return ViolationsResult(
            charactersCount = invalidCharactersCount,
            wordsCount = invalidWordsCount,
            sentencesCount = if (inCompleteSentence) 1 else 0
        )
    }

}