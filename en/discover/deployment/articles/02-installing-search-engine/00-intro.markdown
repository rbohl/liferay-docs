---
header-id: installing-a-search-engine
---

# Installing a Search Engine

[TOC levels=1-4]

A search engine is a critical component of your @product@ installation. If
you're here, you probably know the basics already and want to configure a search
engine for your @product@ deployment. 

@product@ ships with Elasticsearch, a highly scalable, full-text search engine.
Elasticsearch is well-supported and almost certainly meets any search and
indexing need you have. For deployment settings, learn to configure
a standalone or remote Elasticsearch server or cluster
[here](/docs/7-1/deploy/-/knowledge_base/d/installing-elasticsearch).

[Solr](http://lucene.apache.org/solr) is also supported in @product@. 

## Choosing a Search Engine

Elasticsearch and Solr are both supported, but there are some differences in how
they work with @product@. In certain cases, you must choose Elasticsearch. 

If you answer _yes_ to either of these questions, you must choose Elasticsearch:

1.  You're using 
    [@commerce@, Liferay's commerce solution](/web/commerce/documentation/-/knowledge_base/1-0/getting-started).

2.  Your custom search code requires the use of the `TermsSetFilter` API or the
    Geolocation APIs that are implemented in the Liferay Connector to
    Elasticsearch.

@commerce@ requires the `TermsSetFilter` implementation available in the
Elasticsearch connector, so you must use Elasticsearch if you're using
@commerce@.

Both of these Elasticsearch-only developer features are not currently
implemented in the Liferay Connector to Solr, but may be added in the future.
If you must use either of those features in your search solution's code, use
Elasticsearch. If you're using @commerce@, use Elasticsearch.
Otherwise, feel free to use Elasticsearch or Solr to index your portal content.

<!-- copied wholesale ffrom the 7.2 article. need to curate for 7.1 -->

## Choosing a Search Engine

Elasticsearch and Solr are both supported, but there are limitations to
Liferay's Solr integration. To make use of some features, you must choose
Elasticsearch. 

### End User Feature Limitations of Liferay's Solr Integration

- [Liferay Commerce](https://help.liferay.com/hc/en-us/articles/360017869952)
- [Workflow Metrics](https://help.liferay.com/hc/en-us/articles/360029042071-Workflow-Metrics-The-Service-Level-Agreement-SLA-) 
- [Custom Filter search widget](/docs/7-2/user/-/knowledge_base/u/filtering-search-results-with-the-custom-filter-widget)
- [The Low Level Search Options widget](/docs/7-2/user/-/knowledge_base/u/low-level-search-options-searching-additional-or-alternate-indexes)
- [Search Tuning: Customizing Search Results](https://help.liferay.com/hc/en-us/articles/360034473872-Search-Tuning-Customizing-Search-Results) 
- [Search Tuning: Synonyms](https://help.liferay.com/hc/en-us/articles/360034473852-Search-Tuning-Synonym-Sets) 

### Developer Feature Limitations of Liferay's Solr Integration

Implementation for the following APIs may be added in the future, but they are
not currently supported by Liferay's Solr connector.

- From Portal Core (Module: `portal-kernel`, Artifact:
    `com.liferay.portal.kernel`):
    - `com.liferay.portal.kernel.search.generic.NestedQuery`
    - `com.liferay.portal.kernel.search.filter`:
        - `ComplexQueryPart`
        - `GeoBoundingBoxFilter`
        - `GeoDistanceFilter`
        - `GeoDistanceRangeFilter`
        - `GeoPolygonFilter`
- From the Portal Search API (Module: `portal-search-api`, Artifact:
    `com.liferay.portal.search.api`):
    - `com.liferay.portal.search.filter`:
        - `ComplexQueryPart`
        - `TermsSetFilter`
    - `com.liferay.portal.search.geolocation.*`
    - `com.liferay.portal.search.highlight.*`
    - `com.liferay.portal.search.query.function.*`
    - `com.liferay.portal.search.query.*`:
    - `com.liferay.portal.search.script.*`
    - `com.liferay.portal.search.significance.*`
    - `com.liferay.portal.search.sort.*`: only `Sort`,`FieldSort`, and
        `ScoreSort` are supported
- Portal Search Engine Adapter API (Module: `portal-search-engine-adapter-api`,
    Artifact: `com.liferay.portal.search.engine.adapter.api`)
    - `com.liferay.portal.search.engine.adapter.cluster.*`
    - `com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest`
    - `com.liferay.portal.search.engine.adapter.index.*`: only `RefreshIndexRequest` is supported
    - `com.liferay.portal.search.engine.adapter.search.*`: 
        - `MultisearchSearchRequest` 
        - `SuggestSearchRequest`
    - `com.liferay.portal.search.engine.adapter.snapshot.*`

Liferay Commerce requires the `TermsSetFilter` implementation, only available
in the Elasticsearch connector.

Another factor to consider in your search engine selection is JDK version. The
search engine and @product@ must use the same JDK version and distribution
(e.g., Oracle Open JDK 1.8.0_201). Consult the 
[Elasticsearch compatibility matrix](https://www.elastic.co/support/matrix#matrix_jvm)
and the 
[@product@ compatibility matrix](https://web.liferay.com/documents/14/21598941/Liferay+DXP+7.1+Compatibility+Matrix/9f9c917a-c620-427b-865d-5c4b4a00be85) 
to learn more about supported JDK distributions and versions. This consideration
is not necessary for Solr, because no JVM level serialization happens between
the servers. All communication occurs at the HTTP level.

