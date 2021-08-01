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
            .reduce { grammerResult, calculatedGrammarResult -> GrammarResult(
                    verbsCount = calculatedGrammarResult.verbsCount + grammerResult.verbsCount,
                    nounsCount = calculatedGrammarResult.nounsCount + grammerResult.nounsCount,
                    prepositionsCount = calculatedGrammarResult.prepositionsCount + grammerResult.prepositionsCount,
                    violations = ViolationsResult(
                        charactersCount = calculatedGrammarResult.violations.charactersCount + grammerResult.violations.charactersCount,
                        wordsCount = calculatedGrammarResult.violations.wordsCount + grammerResult.violations.wordsCount,
                        sentencesCount = calculatedGrammarResult.violations.sentencesCount + grammerResult.violations.sentencesCount,
                    )
                )
            }
    }

    companion object {
        private val sentenceSeparator = "(\\.|!)\\s*".toRegex()
    }
}