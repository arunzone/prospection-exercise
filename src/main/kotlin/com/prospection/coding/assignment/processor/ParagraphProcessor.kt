package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.dto.paragraph.ViolationsResult
import com.prospection.coding.assignment.dto.sentence.SentenceGrammarResult
import com.prospection.coding.assignment.dto.sentence.SentenceViolationsResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ParagraphProcessor(@Autowired private val sentenceProcessor: SentenceProcessor) {

    infix fun process(paragraph: String): GrammarResult {
        val sentences = paragraph.split(sentenceSeparator).filter { it.isNotEmpty() }
        val sentenceResult = sentences.map { sentenceProcessor process it }
            .reduce { grammarResult, calculatedGrammarResult ->
                aggregateResult(calculatedGrammarResult, grammarResult)
            }
        val sentenceViolation = sentenceResult.violation
        val paragraphSentencesViolationCount = if (sentences.size in 1..3) 0 else 1

        val violation = ViolationsResult(
            charactersCount = sentenceViolation.charactersCount,
            wordsCount = sentenceViolation.wordsCount,
            sentencesCount = sentenceViolation.sentencesCount,
            paragraphSentencesCount = paragraphSentencesViolationCount,
            paragraphSuffixesCount = 0
        )
        return GrammarResult(
            nounsCount = sentenceResult.nounsCount,
            verbsCount = sentenceResult.verbsCount,
            prepositionsCount = sentenceResult.prepositionsCount,
            violation = violation
        )
    }

    private fun aggregateResult(
        calculatedSentenceGrammarResult: SentenceGrammarResult,
        sentenceGrammarResult: SentenceGrammarResult
    ) = SentenceGrammarResult(
        verbsCount = calculatedSentenceGrammarResult.verbsCount + sentenceGrammarResult.verbsCount,
        nounsCount = calculatedSentenceGrammarResult.nounsCount + sentenceGrammarResult.nounsCount,
        prepositionsCount = calculatedSentenceGrammarResult.prepositionsCount + sentenceGrammarResult.prepositionsCount,
        violation = SentenceViolationsResult(
            charactersCount = calculatedSentenceGrammarResult.violation.charactersCount + sentenceGrammarResult.violation.charactersCount,
            wordsCount = calculatedSentenceGrammarResult.violation.wordsCount + sentenceGrammarResult.violation.wordsCount,
            sentencesCount = calculatedSentenceGrammarResult.violation.sentencesCount + sentenceGrammarResult.violation.sentencesCount,
        )
    )

    companion object {
        private val sentenceSeparator = "\\.\\s*".toRegex()
    }
}