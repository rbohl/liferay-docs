# Statistical Aggregations

* Target Audience: Developers
* Article Type: Development Frameworks

## External References

* https://www.elastic.co/guide/en/elasticsearch/reference/6.5/search-aggregations-metrics.html
* https://www.elastic.co/guide/en/elasticsearch/reference/6.5/search-aggregations-metrics-cardinality-aggregation.html
* https://lucene.apache.org/solr/guide/7_5/the-stats-component.html

## Search Engine Connector Support
* Elasticsearch 6: Yes
* Solr 7: Yes

## New/Related APIs

Listed those which are relevant for developers.
 
API (FQCN) | Provided by Artifact |
---------: | :------------------: |
`com.liferay.portal.search.searcher.SearchRequestBuilder#statsRequests(StatsRequest... statsRequests)` | com.liferay.portal.search.api
`com.liferay.portal.search.searcher.SearchResponse#getStatsResponseMap()` | com.liferay.portal.search.api
**`com.liferay.portal.search.stats.StatsRequest`** |	com.liferay.portal.search.api
`com.liferay.portal.search.stats.StatsRequestBuilder` |	com.liferay.portal.search.api
`com.liferay.portal.search.stats.StatsRequestBuilderFactory` |	com.liferay.portal.search.api
**`com.liferay.portal.search.stats.StatsResponse`** |	com.liferay.portal.search.api
`com.liferay.portal.kernel.search.Stats` | portal-kernel

## Deprecated APIs
* SearchSearchRequest#getStats()
* SearchSearchRequest#setStats(Map<String, Stats> stats)

----

## Draft

Support for [GroupBy](https://github.com/liferay/liferay-portal/blob/7.2.x/portal-kernel/src/com/liferay/portal/kernel/search/GroupBy.java) and [Stats](https://github.com/liferay/liferay-portal/blob/7.2.x/portal-kernel/src/com/liferay/portal/kernel/search/Stats.java) aggregations were introduced in 7.0.

To extend Liferay's search aggregation capabilities, in @product-ver@, we added support for Cardinality Aggregations which provides an approximate (aka. statistical) count of distinct values. Please refer to the "External References" above for more details.

As part of the modularization efforts, [StatsRequest](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-api/src/main/java/com/liferay/portal/search/stats/StatsRequest.java) and [StatsResponse](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-api/src/main/java/com/liferay/portal/search/stats/StatsResponse.java) were introduced in the `com.liferay.portal.search.api` module to avoid modifying `portal-kernel`. StatsRequest provides the same statistical features that the legacy `com.liferay.portal.kernel.search.Stats` does besides the new "cardinality" option.

### StatsRequest

Provides a map of field names and the metric aggregations that are to be computed for each field.

1. Get a reference to `com.liferay.portal.search.searcher.SearchRequestBuilderFactory`:
```java

@Reference
SearchRequestBuilderFactory searchRequestBuilderFactory;
```
2. Get an instace of `com.liferay.portal.search.searcher.SearchRequestBuilder`:
```java

SearchRequestBuilder searchRequestBuilder = searchRequestBuilderFactory.getSearchRequestBuilder();
```
3. Get a`com.liferay.portal.search.searcher.SearchRequest` instance from the builder:
```java

SearchRequest searchRequest = searchRequestBuilder.build();
```
4. Get a reference to `com.liferay.portal.search.stats.StatsRequestBuilderFactory`:
```java

@Reference
StatsRequestBuilderFactory statsRequestBuilderFactory;
```
5. Get a `com.liferay.portal.search.stats.StatsRequestBuilder` instance and build `com.liferay.portal.search.stats.StatsRequest` with the desired metrics:
```java

StatsRequestBuilder statsRequestBuilder = statsRequestBuilderFactory.getStatsRequestBuilder();

StatsResponse expectedStatsResponse = statsResponseBuilder.cardinality(
    31
).count(
    31
).field(
    field
).max(
    31
).mean(
    16
).min(
    1
).sum(
    496
).sumOfSquares(
    10416
).build();
```
6. Set Statsrequest on the SearchRequest:
```java

searchRequest.statsRequests(statsRequest);
```
7. Get a reference to `com.liferay.portal.search.searcher.Searcher`:
```java
@Reference
protected Searcher searcher;
```
8. Perform a search using Searcher and SearchRequest to get `com.liferay.portal.search.searcher.SearchResponse`:
```java

SearchResponse searcher.search(searchRequest);
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L128

**Note:** there is still support for the legacy `com.liferay.portal.kernel.search.Stats` object:

1. Create a Stats instance with the desired metrics:
```java

Stats stats = new Stats() {
   {
       setCount(true);
    			setField(field);
    			setMax(true);
    			setMean(true);
    			setMin(true);
    			setSum(true);
    			setSumOfSquares(true);
   }
};
```
3. Set Stats on the SearchContext:
```java
searchRequestBuilder.withSearchContext(searchContext -> searchContext.addStats(stats));
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L42

### StatsResponse

Contains the metrics aggregations computed by the search engine for a given field.

1. Get the map containing the metrics aggregations computed by the search engine:
```java

Map<String, StatsResponse> map = searchResponse.getStatsResponseMap();
```
2. Get the StatsResponse for a given field:
```java

StatsResponse statsResponse = map.get(field);
```
3. Get the desired metric, for example "cardinality":
```java

statsResponse.getCardinality();
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L128
