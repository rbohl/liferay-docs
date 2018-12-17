# Queries and Filters [](id=queries-and-filters)

Elasticsearch and Solr do not make API level distinctions between queries and
filters. However, Liferay's API explicitly provides two sets of APIs, one for
queries and one for filters.

A *filter* asks a yes or no question for every document. A *query* asks the
same yes or no question AND how well (score) a document matches the specified
criteria. For instance, a filter might ask is the status field equal to staging
or live. A query might ask if the document's content field field contains the
words "Liferay", "Content", "Management", and how relevant the content of the
document is to the search terms.

With respect to performance, filters are much faster since the documents that
match a filter can be easily cached. Queries not only match documents but also
calculate scores. Liferay uses filters and queries together so that filters can
reduce the number of matched documents before the query examines them for
scoring.

Liferay's Search API supports the following types of queries:

Full text queries:

- MatchQuery: Full text matching, scored by relevance.
- MultiMatchQuery: MatchQuery over several fields.
- StringQuery: Uses Lucene query syntax

Term queries:

- TermQuery: Exact matching on keyword fields and indexed terms
- TermRangeQuery: TermQuery with a range
- WildcardQuery: Wildcard (* and ?) matching on keyword fields and indexed terms
- FuzzyQuery: Scrambles characters in input before matching

Compound queries:

- BooleanQuery: Allows a combo of several query types. Individual queries are
  added as clauses with SHOULD | MUST | MUST_NOT.
- DisMaxQuery

Other queries:

- MoreLikeThisQuery
- MatchAllQuery: Matches all documents

Liferay's Search API supports the following types of filters:

Term filters:

- TermFilter
- TermsFilter
- PrefixFilter
- ExistsFilter
- MissingFilter
- RangeTermFilter

Compound filters:

- BooleanFilter

Geo filters: (Geolocation filters help filter documents based on the latitude
and longitude fields)

- GeoDistanceFilter
- GeoDistanceRangeFilter
- GeoBoundingBoxFilter
- GeoPolygonFilter

Other filters:

- QueryFilter: Turns any query into a filter. E.g., can a BooleanQuery into a BooleanFilter
- MatchAllFilter: Matches all documents
