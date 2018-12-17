## Indexers [](id=indexers)

There is an Indexer for each asset in the portal (e.g., DLFileEntryIndexer).
This allows each asset to control what fields are indexed and what filters are
applied to the search query.

Generally, when you create an asset that requires indexing, you would implement
a new Indexer by extending `com.liferay.portal.kernel.search.BaseIndexer<T>`.

For more information, consult the Javadocs for
`com.liferay.portal.kernel.search.Indexer<T>` and
`com.liferay.portal.kernel.search.BaseIndexer<T>`: [@platform-ref@/7.0-latest/javadocs](@platform-ref@/7.0-latest/javadocs)
