## Aggregations [](id=aggregations)

Aggregations help summarize search results. Individual aggregations can be used
to create more complex aggregations. Facets are a type of aggregation. In
addition to facets, Liferay also provides group by and statistics aggregations.

Facets:

- Date Range Facet
- Modified Date Facet
- MultiValue Facet
- Range Facet
- Scope Facet
- Simple Facet

Statistics:

Stats provides general statistics for a desired field within the returned
search results:

- count
- max
- mean
- min
- missing
- standard deviation
- sum
- sum of squares

GroupBy:

GroupBy is a powerful feature that allows you to group search results based on
a particular field. For example, suppose you wish to group the search results
based on the asset type (e.g., web content article, document, blog post, etc.).
To do so, you would create a search query that contains a GroupBy aggregation
with the field "entryClassName".

Other attributes you can specify:

- The maximum number of results in each group
- Special sorting for the grouped results


