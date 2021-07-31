package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.validator.VerbValidator
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK

internal class SentenceProcessorTest : BehaviorSpec() {
    @MockK
    lateinit var verbValidator: VerbValidator
    lateinit var sentenceProcessor: SentenceProcessor
    override fun isolationMode() = IsolationMode.InstancePerTest
    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        sentenceProcessor = SentenceProcessor(verbValidator)
    }

    init {
        given("a valid sentence has one verb") {
            `when`("verb validator is invoked ") {
                every { verbValidator.isVerb("Cufabiu") } returns false
                every { verbValidator.isVerb("nonad.") } returns true
                val processResult =
                    sentenceProcessor.process("Cufabiu nonad.")
                then("it should return count 1") {
                    processResult.verbs shouldBe 1
                }
            }
        }

        given("a valid sentence has 2 verb") {
            `when`("verb validator is invoked ") {
                every { verbValidator.isVerb("Cufabiu") } returns false
                every { verbValidator.isVerb("in") } returns false
                every { verbValidator.isVerb("nonad") } returns true
                every { verbValidator.isVerb("finl.") } returns true
                val processResult =
                    sentenceProcessor.process("Cufabiu nonad in finl.")
                then("it should return count 2") {
                    processResult.verbs shouldBe 2
                }
            }
        }
    }


    override fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }
}