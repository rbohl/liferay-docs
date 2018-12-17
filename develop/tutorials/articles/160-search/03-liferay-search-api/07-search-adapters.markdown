# Search Adapter API [](id=search-adapter-api)

Search adapters convert Liferay Portal's API to the underlying search engine's
API. This pluggable architecture allows customers to more easily integrate with
other search engines. Liferay ships with two adapters: an Elasticsearch adapter
and a Solr adapter.

The search adapter API has 2 primary interfaces:

- `IndexSearcher`: invoked for all search operations
- `IndexWriter`: used when adding, updating, or deleting documents from the
  search engine.

## Transactional Search [](id=transactional-search)

Search engines do not operate within a traditional JTA/JTS transaction. In
place of "real" transactions, Liferay buffers indexing operations (delete,
update) until either the surrounding transaction has been committed or we have
exceeded the max buffer size. The buffered indexer requests are abandoned in
the event of transaction rollback. This gives us some semblance of
transactional control, except in scenarios where we have large batches of
commits (e.g., exceeds `maxBufferSize`).

When `maxBufferSize` has been exceeded, the search infrastructure executes
buffered indexer requests to free up space in the buffer.

Buffered `IndexerRequests` always execute in FIFO order. There is no collation
of `IndexerRequests` in the buffer.

You can activate / deactivate and set the buffer size by configuring
`com.liferay.portal.search.configuration.IndexerRegistryConfiguration`. By
default, the buffering is activated and the max buffer size is 200.

For a list of buffered methods, see `com.liferay.portal.kernel.search.Indexer`.
All methods annotated with `@Bufferable` are subject to potential buffering.
