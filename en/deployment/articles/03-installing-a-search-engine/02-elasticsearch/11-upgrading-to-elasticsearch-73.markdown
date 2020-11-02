---
header-id: upgrading-to-elasticsearch-7
---

# Upgrading to Elasticsearch 7

[TOC levels=1-4]

Elasticsearch 7 is supported for @product-ver@. If you're upgrading
@product@ and still running Elasticsearch 6, consider upgrading your
Elasticsearch servers too. If you're setting up a new system and not already
running a remote Elasticsearch 6 server, follow the 
[installation guide](/docs/7-2/deploy/-/knowledge_base/d/installing-elasticsearch) to install
Elasticsearch and the 
[configuration guide](/docs/7-2/deploy/-/knowledge_base/d/configuring-the-liferay-elasticsearch-connector)
to configure the Elasticsearch adapter.

| **Before Proceeding,** back up your existing data before upgrading
| Elasticsearch. If something goes wrong during or after the upgrade, roll back
| to the previous version using the uncorrupted index snapshots. See
| [here](/docs/7-2/deploy/-/knowledge_base/d/backing-up-elasticsearch) for more
| information.

Here, you'll learn to upgrade an
existing Elasticsearch 6 server (or cluster) to Elasticsearch 7: 

1. [Install and configure Elasticsearch 7](/docs/7-2/deploy/-/knowledge_base/d/installing-elasticsearch).

2. [Back up the application specific indexes for Search Tunings (Synonym Sets and Result
    Rankings), see below this page.]

3. Upgrade Elasticsearch

4. If you're using X-Pack security, enable it (it's disabled by default):
    ```yml
    xpack.security.enabled: true
    ```

5. Blacklist the bundled Liferay Connector to Elasticsearch 6.

6. Install and configure the Liferay Connector to Elasticsearch 7.

7. Re-index all search and spell check indexes.

8. Verify that Search Tuning entries have been carried over

| **Known Issue:** See
| [LPS-103938](https://issues.liferay.com/browse/LPS-103938). The Liferay
| Connector to Elasticsearch 7 throws an exception in the log when the LPKG
| file is deployed. There are no known functional impacts. If unexpected errors
| occur, re-start the @product@ server.

Learn about configuring Elasticsearch [here](/docs/7-2/deploy/-/knowledge_base/d/configuring-the-liferay-elasticsearch-connector).

## Upgrading Elasticsearch

If you are using a rolling-restart eligible version (`6.8.x`), doing a [rolling upgrade](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/rolling-upgrades.html) is the recommended way to ugprade your Elasticsearch cluster. Otherwise, follow the [full cluster restart upgrade
](https://www.elastic.co/guide/en/elasticsearch/reference/7.x/restart-upgrade.html) guide.

## Blacklisting Elasticsearch 6

To blacklist Elasticsearch 6,

1.  Create a configuration file named

    ```bash
    com.liferay.portal.bundle.blacklist.internal.BundleBlacklistConfiguration.config
    ```

2.  Give it these contents:

    ```properties
    blacklistBundleSymbolicNames=[ \
        "com.liferay.portal.search.elasticsearch6.api", \
        "com.liferay.portal.search.elasticsearch6.impl", \
        "com.liferay.portal.search.elasticsearch6.spi", \
        "com.liferay.portal.search.elasticsearch6.xpack.security.impl", \
        "Liferay Enterprise Search Security  - Impl" \
    ]
    ```

## Re-index

Once the Elasticsearch adapter is installed and talking to the Elasticsearch
cluster, navigate to *Control Panel* &rarr; *Configuration* &rarr; *Search*,
and click *Execute* for the *Reindex all search indexes* entry.

You must also re-index the spell check indexes.

## Reverting to Elasticsearch 6

Stuff happens. If that stuff involves an unrecoverable failure during the
upgrade to Elasticsearch 7, roll back to Elasticsearch 6 and regroup.

Since your Elasticsearch 6 and 7 are currently two separate installations, this
procedure takes only a few steps:

1.  Stop the Liferay Connector to Elasticsearch 6.

2.  Stop Elasticsearch 7 and make sure that the Elasticsearch 6
    `elasticsearch.yml` and the connector app are configured to use the same
    port (9200 by default).

3.  Start the Elasticsearch server, and then restart the Liferay Connector to
    Elasticsearch 6.

## Backing up and Restoring Search Tuning Indexes

Creating a snapshot of your Elasticsearch indexes is highly recommended, especially for the indexes of the _Search Tuning_ features ([Synonym Sets](/docs/7-2/user/-/knowledge_base/u/search-tuning-synonym-sets) and [Result Rankings](/docs/7-2/user/-/knowledge_base/u/search-tuning-customizing-search-results)) as they are the primary storage of your entries, there are no records in the database.

Here we provide an example usage of Elasticsearch's [snapshot and restore](https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshot-restore.html) feature to back-up and restore _Search Tuning_ indexes and documents.

1. Create a folder called `elasticsearch_local_backup` somewhere in your system where Elasticsearch has read and write access to, for example `/path/to/elasticsearch_local_backup`

2. Edit the `elasticsearch.yml` on [all master and data nodes](https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-register-repository.html#snapshots-filesystem-repository) in your Elasticsearch cluster and add

    ```yaml
    path.repo: [ "/path/to/elasticsearch_local_backup" ]
    ```

3. Restart your Elasticsearch nodes

4. [Register the snapshot repository](https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-register-repository.html): run the following API request (for example through the Dev Tools console in Kibana):

    ```
    PUT /_snapshot/elasticsearch_local_backup
    {
      "type": "fs",
      "settings": {
        "location": "/path/to/elasticsearch_local_backup"
      }
    }
    ```

5. [Create a snapshot](https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-take-snapshot.html):

    ```
    PUT /_snapshot/elasticsearch_local_backup/snapshot1?wait_for_completion=true
    {
      "indices": "liferay-search-tuning*",
      "ignore_unavailable": true,
      "include_global_state": false
    }
    ```

    If you want to create a snapshot for all Liferay indexes, you can use `"indices": "liferay*,workflow-metrics*"` instead.

6. To [restore](https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-restore-snapshot.html) only specific indexes from a snapshot under a different name, run an API call similar to this:

    ```
    POST /_snapshot/elasticsearch_local_backup/snapshot1/_restore
    {
      "indices": "liferay-20101-search-tuning-synonyms,liferay-20101-search-tuning-rankings",
      "ignore_unavailable": true,
      "include_global_state": false,
      "rename_pattern": "(.+)",
      "rename_replacement": "restored_$1",
      "include_aliases": false
    }
    ```

    where `indicies` sets the index names to restore from (as they are stored in the snapshot!). The indexes from the above call will be restored as `restored_liferay-20101-search-tuning-rankings` and `liferay-20101-search-tuning-synonyms` according to the `rename_pattern` and `rename_replacement` regular expressions.

Restoring the _Search Tuning_ indexes from a snapshot under different names comes in handy, if you did not upgrade your Elasticsearch 6 cluster, but rather, you just set-up your new Elasticsearch 7 cluster, configured Elasticsearch 7 in @product-ver@ and performed a full reindex from @product@.

In this case, existing _Search Tuning_ entries will be missing, because whose indexes will be empty.

To restore your existing _Search Tuning_ index documents, you can use the [Reindex API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html#docs-reindex) of Elasticsearch, like this:

```
   POST _reindex/
   {
     "dest": {
       "index": "liferay-20101-search-tuning-synonyms"
     },
     "source": {
       "index": "restored_liferay-20101-search-tuning-synonyms"
     }
   }
```

Note: it is possible to create and manage snapshots through Kibana's UI in [Kibana 7.x](https://www.elastic.co/guide/en/kibana/7.x/snapshot-repositories.html).

### Default Search Tuning Elasticsearch Index Names

As a reference, here are the out-of-the-box _Search Tuning_ index names depending on your @product-ver@ patch level:
* `liferay-search-tuning-rankings` on @product-ver@ SP1-SP2
* `liferay-search-tuning-synonyms-liferay-<companyId>` on @product-ver@ SP2
* `liferay-<companyId>-search-tuning-rankings` on @product-ver@ SP3+/FP8+
* `liferay-<companyId>-search-tuning-synonyms` on @product-ver@ SP3+/FP8+

where the `<companyId>` (like `20101`) belongs to a given Company record in your the database. It is displayed as "Instance ID" in the UI and represents a [Virtual Instance](/docs/7-2/user/-/knowledge_base/u/virtual-instances).