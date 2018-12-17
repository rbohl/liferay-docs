# Suggestions [](id=suggestions)

Suggestions are a powerful feature where the search engine can suggest
"similar" results for a given query. For instance, suppose you have a blog
entry with the title "Liferay Portal Content Management" and you would like to
find other content with similar titles.

`com.liferay.portal.kernel.search.IndexSearcher` provides methods to access
suggestion capabilities. It implements
`com.liferay.portal.kernel.search.suggest.QuerySuggester`.

The QuerySuggester provides facilities for

- Spell Checking
- Related search queries
- General Suggester requests

### Spell Checking [](id=spell-checking)

For Elasticsearch, spell checking heavily relies on the suggester API:
    - Dictionary words are analyzed by their language specific analyzer and indexed.
    - `TermSuggester` is used to provide suggestions for words based on
      specific `StringDistance` algorithms.

Solr's implementation of `Suggester` is less flexible and sophisticated. Solr's
spell checking algorithm is based strictly on NGrams and does not handle Asian
languages very well.

Note that using the search engine's spell checking functionality doesn't
guarantee returned results. Instead, spell checking seeks to ensure that the
query is correct. 

### Similar Search Queries [](id=similar-search-queries)

Like spell checking, similar search queries has a more robust implementation in
Elasticsearch. The Elasticsearch implementation uses phrase suggesters on
indexed keyword search queries.

Solr's similar search queries implementation is again based on tokenized
NGrams.

### Other Suggesters [](id=other-suggesters)

You can also send custom Suggester requests and get SuggesterResults back from the search engine by calling `QuerySuggester.suggest(SearchContext, Suggester)`.

