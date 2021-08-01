package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.GrammarResult
import com.prospection.coding.assignment.dto.ViolationsResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ParagraphProcessor(@Autowired private val sentenceProcessor: SentenceProcessor) {
    infix fun process(paragraph: String): GrammarResult {
        return paragraph.split(sentenceSeparator)
            .filter { it.isNotEmpty() }
            .map { sentenceProcessor process it }
            .reduce { grammarResult, calculatedGrammarResult ->
                aggregateResult(calculatedGrammarResult, grammarResult)
            }
    }

    private fun aggregateResult(
        calculatedGrammarResult: GrammarResult,
        grammarResult: GrammarResult
    ) = GrammarResult(
        verbsCount = calculatedGrammarResult.verbsCount + grammarResult.verbsCount,
        nounsCount = calculatedGrammarResult.nounsCount + grammarResult.nounsCount,
        prepositionsCount = calculatedGrammarResult.prepositionsCount + grammarResult.prepositionsCount,
        violations = ViolationsResult(
            charactersCount = calculatedGrammarResult.violations.charactersCount + grammarResult.violations.charactersCount,
            wordsCount = calculatedGrammarResult.violations.wordsCount + grammarResult.violations.wordsCount,
            sentencesCount = calculatedGrammarResult.violations.sentencesCount + grammarResult.violations.sentencesCount,
        )
    )

    companion object {
        private val sentenceSeparator = "(\\.|!)\\s*".toRegex()
    }
}