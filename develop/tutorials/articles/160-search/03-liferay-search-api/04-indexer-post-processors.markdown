## IndexerPostProcessor [](id=indexerpostprocessor)

The IndexerPostProcessor allows developers to customize

- Search queries before they are sent to the search engine
- Documents before they are sent to the search engine
- Summaries for results before they are returned to the end users

This is the preferred way to customize existing Indexers.

Follow these steps to add a new IndexerPostProcessor:

1. Implement the interface
   `com.liferay.portal.kernel.search.IndexerPostProcessor`.
2. Publish it to the OSGi registry with the property `indexer.class.name`

`postProcessContextQueryBooleanFilter` allows the developer to customize the
filters created by the `Indexer.getFacetBooleanFilter`. These filters are
generally applied to the fields:

- entryClassName
- relatedClassName
- relatedEntryClassNames
- permissions related fields (e.g., roleId, groupId, etc.).

`postProcessFullQuery` allows the developer to customize the overall search
query which includes

- Filters for any default facets, including those for
    - asset category ids
    - asset tag names
    - entry class names
    - folderIds
    - groupIds
    - layoutUUIDs
    - userId

- The keyword search queries. By default, this includes searches for the
  fields
    - description
    - title
    - userName
    - keyword
    - searchable Expando fields
    - localized fields for assetCategoryTitles

