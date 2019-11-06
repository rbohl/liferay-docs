---
header-id: installing-a-search-engine
---

# Installing a Search Engine

[TOC levels=1-4]

Search engines are a critical component of your @product@ installation. They allow
you to provide documents in an index that can quickly be searched, rather than
relying on expensive database queries. For more fundamental material on search
and indexing in @product@, read
[here](/docs/7-0/tutorials/-/knowledge_base/t/introduction-to-liferay-search).
If you're here, you probably know the basics already and want to get started on
configuring a search engine for your @product@ deployment. 

@product@ ships with Elasticsearch, a highly scalable, full-text search engine.
Elasticsearch is well supported and will almost certainly meet any search and
indexing need you have. For deployment settings, learn to configure a
standalone, or remote, Elasticsearch server or cluster
[here](/docs/7-0/deploy/-/knowledge_base/d/configuring-elasticsearch-for-liferay-0).

If you need to use Solr, it's also supported in @product@, and you can read more
about its configuration [here](/docs/7-0/deploy/-/knowledge_base/d/using-solr).

<!-- copied from the 7.2 article. should be curated for 7.0 if we want to have this-->
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
