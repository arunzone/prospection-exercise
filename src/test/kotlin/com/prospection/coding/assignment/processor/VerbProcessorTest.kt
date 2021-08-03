package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.validator.VerbValidator
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class VerbProcessorTest : ShouldSpec() {
    override fun isolationMode() = IsolationMode.InstancePerTest
    val verbProcessor = VerbProcessor(VerbValidator())

    init {
        context("Only valid verbs") {
            context("only verbs") {
                val (validVerbs, invalidverbs) = verbProcessor.process(listOf("nand", "abcgr"))
                should("have valid verbs") {
                    validVerbs shouldBe 2
                }
                should("have invalid verbs") {
                    invalidverbs shouldBe 0
                }
            }
            context("verb and others") {
                val (validVerbs, invalidverbs) = verbProcessor.process(listOf("nand", "abcgrwe"))
                should("have valid verbs") {
                    validVerbs shouldBe 1
                }
                should("have invalid verbs") {
                    invalidverbs shouldBe 0
                }
            }
        }
        context("Only invalid verbs") {
            context("not in size") {
                val (validVerbs, invalidverbs) = verbProcessor.process(listOf("nanbcd", "abc"))
                should("have valid verbs") {
                    validVerbs shouldBe 0
                }
                should("have invalid verbs") {
                    invalidverbs shouldBe 0
                }
            }
            context("not ends with r or r or l") {
                val (validVerbs, invalidverbs) = verbProcessor.process(listOf("nanbc", "abce"))
                should("have valid verbs") {
                    validVerbs shouldBe 0
                }
                should("have invalid verbs") {
                    invalidverbs shouldBe 2
                }
            }
        }
    }
}