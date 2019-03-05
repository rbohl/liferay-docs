* Target Audience: Developers
* Article Type: Development Frameworks

## External References

https://www.elastic.co/guide/en/elasticsearch/reference/6.5/search-aggregations-metrics-cardinality-aggregation
https://lucene.apache.org/solr/guide/7_5/the-stats-component.html

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

## Deprecated APIs
* SearchSearchRequest#getStats()
* SearchSearchRequest#setStats(Map<String, Stats> stats)

## Cardinality Aggregations

Support for [GroupBy](https://github.com/liferay/liferay-portal/blob/7.2.x/portal-kernel/src/com/liferay/portal/kernel/search/GroupBy.java) and [Stats](https://github.com/liferay/liferay-portal/blob/7.2.x/portal-kernel/src/com/liferay/portal/kernel/search/Stats.java) aggregations were introduced in 7.0.

To extend Liferay's search aggregation capabilities, in @product-ver@, we added support for Cardinality Aggregations which provides an approximate (aka. statistical) count of distinct values. Please refer to the "External References" above for more details.

As part of the modularization efforts, [StatsRequest](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-api/src/main/java/com/liferay/portal/search/stats/StatsRequest.java) and [StatsResponse](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-api/src/main/java/com/liferay/portal/search/stats/StatsResponse.java) were introduced in the `com.liferay.portal.search.api` module to avoid modifying `portal-kernel`. StatsRequest provides the same statistical features that the legacy `com.liferay.portal.kernel.search.Stats` does besides the new "cardinality" option.

### StatsRequest

Provides a map of field names and the metric aggregations that are to be computed for each field.

1. Obtain a reference to SearchRequestBuilderFactory:

    	@Reference(unbind = "-")
    	protected void setSearchRequestBuilderFactory(
    		SearchRequestBuilderFactory searchRequestBuilderFactory) {

    		_searchRequestBuilderFactory = searchRequestBuilderFactory;
    	}

2. Get SearchRequestBuilder for the search context:
```
    SearchRequestBuilder searchRequestBuilder = _searchRequestBuilderFactory.getSearchRequestBuilder(searchContext);
```

3. Get SearchRequest from the builder:
```
    SearchRequest searchRequest = searchRequestBuilder.build();
```
4. Obtain a reference to StatsRequestBuilderFactory:

    	@Reference(unbind = "-")
    	protected void setStatsRequestBuilderFactory(
    		StatsRequestBuilderFactory statsRequestBuilderFactory) {

    		_statsRequestBuilderFactory = statsRequestBuilderFactory;
    	}

5. Get StatsRequestBuilder and build StatsRequest with the desired metrics:

    		StatsRequestBuilder statsRequestBuilder =
    			_statsRequestBuilderFactory.getStatsRequestBuilder();

    		StatsRequest statsRequest = statsRequestBuilder.cardinality(
    			true
    		).count(
    			true
    		).field(
    			field
    		).max(
    			true
    		).mean(
    			true
    		).min(
    			true
    		).missing(
    			true
    		).sum(
    			true
    		).sumOfSquares(
    			true
    		).build();

6. Set Statsrequest on the SearchRequest:
```
    searchRequest.statsRequests(statsRequest);
```
7. Execute search using SearchEngineAdapter (This should be explained earlier in an introduction article)
```
    _searchEngineAdapter.execute(searchRequest);
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L128

**Note:** there is still support internally for the legacy Stats object

1. Create a Stats object with the desired metrics:

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

2. Set Stats on the SearchContext:
```
    searchContext.addStats(stats)
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L42

### StatsResponse

Contains the metrics aggregations computed by the search engine for a given field.

1. Get the map containing the metrics aggregations computed by the search engine:
```
    Map<String, StatsResponse> map = searchResponse.getStatsResponseMap();
```
2. Get the StatsResponse for a given field:
```
    StatsResponse statsResponse = map.get(field);
```
3. Get the desired metric, for example "cardinality":
```
    statsResponse.getCardinality();
```

**Example:** https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/portal-search/portal-search-test-util/src/main/java/com/liferay/portal/search/test/util/stats/BaseStatisticsTestCase.java#L128
