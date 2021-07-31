package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.validator.AlphabetValidator
import com.prospection.coding.assignment.validator.VerbValidator
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class SentenceProcessorTest : ShouldSpec() {
    override fun isolationMode() = IsolationMode.InstancePerTest
    private var sentenceProcessor = SentenceProcessor(VerbValidator(), AlphabetValidator())

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
            processResult.invalidCharactersCount shouldBe 1
        }

        context("a word in sentence has one invalid character in verb") {
            val processResult =
                sentenceProcessor.process("Cufabiu nvnad")
            should("it should return invalid characters count 1") {
                processResult.invalidCharactersCount shouldBe 1
            }
            should("it should return verbs count 0") {
                processResult.verbsCount shouldBe 0
            }
        }
    }
}