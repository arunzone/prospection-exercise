package com.prospection.coding.assignment.service

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.dto.paragraph.ViolationsResult
import com.prospection.coding.assignment.processor.ParagraphProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LanguageService(@Autowired private val paragraphProcessor: ParagraphProcessor) {
    infix fun analyseGrammar(text: String): GrammarResult {
        val rawParagraphs = text.split(paragraphSeparator)
        if (notHavingEvenOneParagraph(rawParagraphs, text)) {
            return noParagraphResult()
        }

        val (paragraphResult, violation) = process(rawParagraphs, text)
        return GrammarResult(
            verbsCount = paragraphResult.verbsCount,
            nounsCount = paragraphResult.nounsCount,
            prepositionsCount = paragraphResult.prepositionsCount,
            violation = violation
        )
    }

    private fun notHavingEvenOneParagraph(rawParagraphs: List<String>, text: String) = rawParagraphs.size < 2 && !text.endsWith("!")

    private fun process(rawParagraphs: List<String>, text: String): Pair<GrammarResult, ViolationsResult> {
        val paragraphs = rawParagraphs.filter { it.isNotEmpty() }
        val paragraphResult = paragraphs.map { sanitizeParagraphSuffix(it) }
            .map { paragraphProcessor process it }
            .reduce { grammarResult, calculatedGrammarResult ->
                aggregateResult(calculatedGrammarResult, grammarResult)
            }

        val violation = violationsResultFrom(text, paragraphs, paragraphResult)
        return Pair(paragraphResult, violation)
    }

    private fun noParagraphResult(): GrammarResult {
        return GrammarResult(
            verbsCount = 0,
            nounsCount = 0,
            prepositionsCount = 0,
            violation = ViolationsResult(
                charactersCount = 0,
                wordsCount = 0,
                verbsCount = 0,
                sentencesCount = 0,
                paragraphSentencesCount = 0,
                paragraphSuffixesCount = 1
            )
        )
    }


    private fun violationsResultFrom(text: String, paragraphs: List<String>, paragraphResult: GrammarResult): ViolationsResult {
        val invalidParagraphs = text.split(invalidParagraphSeparator)
        val paragraphSuffixesDifference = invalidParagraphs.size - paragraphs.size
        val invalidParagraphSuffixesCount =
            if (paragraphSuffixesDifference > 0) paragraphSuffixesDifference else 0
        val paragraphViolation = paragraphResult.violation
        return ViolationsResult(
            charactersCount = paragraphViolation.charactersCount,
            wordsCount = paragraphViolation.wordsCount,
            verbsCount = paragraphViolation.verbsCount,
            sentencesCount = paragraphViolation.sentencesCount,
            paragraphSentencesCount = paragraphViolation.paragraphSentencesCount,
            paragraphSuffixesCount = paragraphViolation.paragraphSuffixesCount + invalidParagraphSuffixesCount
        )
    }

    private fun sanitizeParagraphSuffix(it: String) =
        if (it.endsWith("!")) it.dropLast(1) else it

    private fun aggregateResult(calculatedGrammarResult: GrammarResult, grammarResult: GrammarResult) = GrammarResult(
        verbsCount = calculatedGrammarResult.verbsCount + grammarResult.verbsCount,
        nounsCount = calculatedGrammarResult.nounsCount + grammarResult.nounsCount,
        prepositionsCount = calculatedGrammarResult.prepositionsCount + grammarResult.prepositionsCount,
        violation = ViolationsResult(
            charactersCount = calculatedGrammarResult.violation.charactersCount + grammarResult.violation.charactersCount,
            wordsCount = calculatedGrammarResult.violation.wordsCount + grammarResult.violation.wordsCount,
            verbsCount = calculatedGrammarResult.violation.verbsCount + grammarResult.violation.verbsCount,
            sentencesCount = calculatedGrammarResult.violation.sentencesCount + grammarResult.violation.sentencesCount,
            paragraphSentencesCount = calculatedGrammarResult.violation.paragraphSentencesCount + grammarResult.violation.paragraphSentencesCount,
            paragraphSuffixesCount = calculatedGrammarResult.violation.paragraphSuffixesCount + grammarResult.violation.paragraphSuffixesCount,
        )
    )

    companion object {
        private val paragraphSeparator = "![\\n\\r]+".toRegex()
        private val invalidParagraphSeparator = "[^!][\\n\\r]+".toRegex()
    }

}