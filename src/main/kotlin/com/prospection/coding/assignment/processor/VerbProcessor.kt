package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.validator.VerbValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerbProcessor(
    @Autowired private val verbValidator: VerbValidator,
) {

    infix fun process(sentenceWords: List<String>): Pair<Int, Int> {
        val allVerbs = sentenceWords.filter { verbValidator.verbSized(it) }
        val validVerbsCount = allVerbs.count { verbValidator.isVerb(it) }
        val invalidVerbsCount = allVerbs.size - validVerbsCount
        return Pair(validVerbsCount, invalidVerbsCount)
    }

}