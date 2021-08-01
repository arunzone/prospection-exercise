package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.GrammarResult
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
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violations = grammerResult.violations
                )
            }
    }

    companion object {
        private val sentenceSeparator = "(\\.|!)\\s*".toRegex()
    }
}