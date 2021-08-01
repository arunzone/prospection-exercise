package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.validator.AlphabetValidator
import com.prospection.coding.assignment.validator.GrammarValidator
import com.prospection.coding.assignment.validator.VerbValidator
import com.prospection.coding.assignment.validator.WordValidator
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class SentenceProcessorTest : ShouldSpec() {
    override fun isolationMode() = IsolationMode.InstancePerTest
    private var sentenceProcessor = SentenceProcessor(
        verbValidator = VerbValidator(),
        alphabetValidator = AlphabetValidator(),
        grammarValidator = GrammarValidator(),
        wordValidator = WordValidator()
    )

    init {
        should("return verb count 1 for a sentence with one verb") {
            val processResult =
                sentenceProcessor.process("Cufabiu nonad")

            processResult.verbsCount shouldBe 1
        }

        should("return verb count 2 for a sentence with 2 verb") {
            val processResult = sentenceProcessor.process("Cufabiu nonad in finl")

            processResult.verbsCount shouldBe 2
        }

        should("return invalid characters count 1 for a sentence with one invalid character") {
            val processResult = sentenceProcessor.process("Cufabiuz nonad")
            processResult.violation.charactersCount shouldBe 1
        }

        context("a word in sentence has one invalid character in verb") {
            val processResult =
                sentenceProcessor.process("Cufabiu nvnad")
            should("return invalid characters count 1") {
                processResult.violation.charactersCount shouldBe 1
            }
            should("return verbs count 0") {
                processResult.verbsCount shouldBe 0
            }
        }

        context("a sentence has one noun, preposition and verb") {
            val processResult =
                sentenceProcessor.process("Cufabiu is nanad")
            should("not have any invalid characters") {
                processResult.violation.charactersCount shouldBe 0
            }
            should("not have any invalid words") {
                processResult.violation.wordsCount shouldBe 0
            }
            should("have a verb") {
                processResult.verbsCount shouldBe 1
            }
            should("have a noun") {
                processResult.nounsCount shouldBe 1
            }
            should("have a preosition") {
                processResult.prepositionsCount shouldBe 1
            }
        }

        context("a sentence has one noun, preposition, verb, invalid character and invalid word") {
            val processResult =
                sentenceProcessor.process("Nunddz cufabiu is augueculrices nanad")
            should("not have any invalid characters") {
                processResult.violation.wordsCount shouldBe 1
            }
            should("not have any invalid words") {
                processResult.violation.charactersCount shouldBe 1
            }
            should("have a verb") {
                processResult.verbsCount shouldBe 1
            }
            should("have a noun") {
                processResult.nounsCount shouldBe 1
            }
            should("have a preposition") {
                processResult.prepositionsCount shouldBe 1
            }
            should("not have any violation") {
                processResult.violation.sentencesCount shouldBe 0
            }
        }

        context("violation in a sentence") {
            should("have violation for no preposition") {
                val processResult = sentenceProcessor.process("Aenean aucbor purusa")
                processResult.violation.sentencesCount shouldBe 1
            }
            should("have violation for no verb") {
                val processResult = sentenceProcessor.process("Aenean auc purusa")
                processResult.violation.sentencesCount shouldBe 1
            }
            should("have violation for no Noun") {
                val processResult = sentenceProcessor.process("Aucbor pur")
                processResult.violation.sentencesCount shouldBe 1
            }
        }
    }
}