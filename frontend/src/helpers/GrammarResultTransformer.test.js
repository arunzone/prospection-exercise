import React from "react";
import {transformResult} from "./GrammarResultTransformer";

describe('Grammar result transformer', () => {
    const response = {
        "violation": {
            "charactersCount": 15,
            "wordsCount": 2,
            "verbsCount": 4,
            "sentencesCount": 5,
            "paragraphSentencesCount": 1,
            "paragraphSuffixesCount": 3
        },
        "verbsCount": 4,
        "nounsCount": 8,
        "prepositionsCount": 5
    }
    const result = transformResult(response)

    test('should set verbs count', () => {
        expect(result.types).toContainEqual(
            {
                name: 'Verbs', count: 4
            }
        )
    });
    test('should set nouns count', () => {
        expect(result.types).toContainEqual(
            {
                name: 'Nouns', count: 8
            }
        )
    });
    test('should set prepositions count', () => {
        expect(result.types).toContainEqual(
            {
                name: 'Prepositions', count: 5
            }
        )
    });
    test('should set Rule 1 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 1', count: 15
            }
        )
    });
    test('should set Rule 2 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 2', count: 2
            }
        )
    });
    test('should set Rule 3 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 3', count: 4
            }
        )
    });
    test('should set Rule 4 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 4', count: 5
            }
        )
    });
    test('should set Rule 5 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 5', count: 1
            }
        )
    });
    test('should set Rule 6 count', () => {
        expect(result.violations).toContainEqual(
            {
                name: 'Rule 6', count: 3
            }
        )
    });

    describe('Grammar result transformer', () => {
        test('should set invalid status', () => {
            expect(result).toEqual(
                expect.objectContaining(
                    {
                        status: 'INVALID'
                    }
                )
            )
        });
        test('should set status valid when no violations found', () => {
            const response = {
                "violation": {
                    "charactersCount": 0,
                    "wordsCount": 0,
                    "verbsCount": 0,
                    "sentencesCount": 0,
                    "paragraphSentencesCount": 0,
                    "paragraphSuffixesCount": 0
                },
                "verbsCount": 4,
                "nounsCount": 8,
                "prepositionsCount": 5
            }
            const result = transformResult(response)
            expect(result).toEqual(
                expect.objectContaining(
                    {
                        status: 'VALID'
                    }
                )
            )
        });
    });
});