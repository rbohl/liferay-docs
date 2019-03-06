# Script Aggregations

LPS-90617, script aggregations

[Scripted Metric Aggregations](https://www.elastic.co/guide/en/elasticsearch/reference/6.5/search-aggregations-metrics-scripted-metric-aggregation.html) 
are aggregations of the _metrics_ type, that execute a script to generate their
output.

Scripts are written in ... [painless, expression, mustache according to es docs]:
https://www.elastic.co/guide/en/elasticsearch/reference/6.5/modules-scripting.html]

Example use cases include calculating the total word count of each returned Web
Content Article, ...

Valid return types are primitives, Strings, Maps, and Arrays. Map keys and
values must be of primitive or String type, as must Array elements.

    curl -X POST "localhost:9200/ledger/_search?size=0" -H 'Content-Type: application/json' -d'
    {
        "query" : {
            "match_all" : {}
        },
        "aggs": {
            "profit": {
                "scripted_metric": {
                    "init_script" : "state.transactions = []",
                    "map_script" : "state.transactions.add(doc.type.value == \u0027sale\u0027 ? doc.amount.value : -1 * doc.amount.value)", 
                    "combine_script" : "double profit = 0; for (t in state.transactions) { profit += t } return profit",
                    "reduce_script" : "double profit = 0; for (a in states) { profit += a } return profit"
                }
            }
        }
    }
    '

The `ScriptedMetricAggregation` interface is located in
`com.liferay.portal.search.aggregation.metrics`, 

## Creating Script Aggregations

STEPS AND CODE EXAMPLE HERE
