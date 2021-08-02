export const transformResult = data => {
    const {verbsCount, nounsCount, prepositionsCount, violation} = data
    const {charactersCount, wordsCount, sentencesCount, paragraphSuffixesCount, paragraphSentencesCount} = violation
    return {
        status: Object.values(violation).some((count) => count > 0) ? 'INVALID' : 'VALID',
        types: [
            {
                name: 'Verbs', count: verbsCount
            },
            {
                name: 'Nouns', count: nounsCount
            },
            {
                name: 'Prepositions', count: prepositionsCount
            }
        ],
        violations: [
            {
                name: 'Rule 1', count: charactersCount
            },
            {
                name: 'Rule 2', count: wordsCount
            },
            {
                name: 'Rule 3', count: violation.verbsCount
            },
            {
                name: 'Rule 4', count: sentencesCount
            },
            {
                name: 'Rule 5', count: paragraphSentencesCount
            },
            {
                name: 'Rule 6', count: paragraphSuffixesCount
            }
        ]
    }
}