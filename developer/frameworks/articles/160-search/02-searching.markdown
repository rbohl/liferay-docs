# Search

What's a search framework without the ability to, you know, search for indexed
data? Users can execute searches from the Search widgets in the @product@ UI,
but there are often search requirements in custom code. This article discusses
the supported approaches to searching programmatically, and the proper context
for each way of getting it done.

We'll covered these approaches in the order that we'd like you to follow,
recognizing that you won't always be able to sue the recommended approach. Start
at the top of this article and work your way down until you find the search
approach you need. Let's start with the best, most foolproof approach to
searching.

## The Searcher Interface

In `portal-search-api` lives the `Searcher` interface. If you're new to Liferay
search, start here. Obtain a reference to `Searcher`, construct a search request
and pass it to the only `Searcher` method, `search`:

```java
SearchResponse searchResponse = searcher.search(searchRequest);
```

It looks simple but it's powerful. The returned search response includes not
only the documents matching whatever query is passed into the search request,
but also the `int count` of the hits.
<!--include info about counting with  SearchRequestBuilder.size(0), the search call will return just the count
and zero actual documents-->
<!-- Add a list of the things you can add to the search request? model indexer
classes, etc.?-->
<!-- Will need to cover how to build the search request and what's included in
the returned response-->

For example, the new `CTEntrylocalServiceImpl` demonstrates how
to get a `Stream<Document>` from the response:

```java
Stream<Document> documentsStream = searchResponse.getDocumentsStream();
```

## Search from the Low Level Search Interface

This is a brand new concept, and is good for advanced cases, like searching
application-specific custom indexes. If you're using this to search, that means
you've been using it to do other things directly at the search engine level,
such as creating custom, application-specific indexes, or adding field mappings
via the API. If you're trying to do a regular search on the regular Liferay
index, you've gone too far. Go back up and use the `Searcher` from
`portal-search-api` instead. This causes your search to skip the `Indexer`
framework altogether.

```java
SearchSearchResponse searchSearchResponse = 
    searchEngineAdapter.execute(searchSearchRequest);
```

<!--Cover the building of the SearchSearchRequest and whats included in the
returned SearchSearchResponse-->

For example, get the hits from the response:

```java
SearchHits searchHits = searchSearchResponse.getSearchHits();
```

## Deprecated: Indexer `portal-kernel`

This is a legacy interface, but it's still used widely in @product@ itself and
is still supported. Use this if you're already using it and can't yet switch to
the new `Searcher` interface.

Get the indexer you need, build a search context, and execute the search:

```java
Hits hits = indexer.search(searchContext);
```

<!--Will need to cover how to build Search Context and what's included in the
returned Hits-->
`JournalDisplayContext` class does this for the search context:

```java
SearchContext searchContext = 
    buildSearchContext(folderIds, articleSearchContainer.getStart(), 
    articleSearchContainer.getEnd(), sort, showVersions);
```
-->
