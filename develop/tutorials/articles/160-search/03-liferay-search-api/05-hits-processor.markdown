# HitsProcessor [](id=hitsprocessor)

`com.liferay.portal.kernel.search.HitsProcessor` allows developers to
preprocess the results from the search engine before they are returned to the
user. This allows for features like

- spell checking
- suggesting related queries
- indexing search queries that have returned high quality search results

HitsProcessors are stored in a HitsProcessorRegistry and sorted by their
`sort.order`. Essentially, we have a chain of responsibility held by the
HitsProcessorRegistry.

By default, the HitsProcessor order is:

1. `CollatedSpellCheckHitsProcessor`
    - Performs a spell check if the minimum score for search results is less than a given threshold
    - Number of results defined in portal.properties
      (index.search.collated.spell.check.result.scores.threshold)

2. `AlternateKeywordQueryHitsProcessor`
    - Automatically issue a query using the suggested keywords from the `CollatedSpellCheckHitsProcessor`.

3. `QueryIndexingHitsProcessor`
    - If query indexing is enabled (`index.search.query.indexing.enabled` in
      `portal.properties`), then index the search query if the number of hits
      has exceeded a configured quantity
      (`index.search.query.indexing.threshold` in `portal.properties`).

4. `QuerySuggestionHitsProcessor`
    - If number of results returned has not met a given threshold
      (`index.search.query.suggestion.scores.threshold` in `portal.properties`),
      then suggest other potential queries that previous searches have yielded
      more results (`index.search.query.suggest.max` in `portal.properties`).

