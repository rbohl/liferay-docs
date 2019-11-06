---
header-id: installing-x-pack
---

# Installing Liferay Enterprise Search

[TOC levels=1-4]

X-Pack is an 
[Elasticsearch extension](https://www.elastic.co/guide/en/elasticsearch/reference/6.8/setup-xpack.html)
for securing and monitoring Elasticsearch clusters. If you use Elasticsearch,
you should secure it with X-Pack. The security features of X-Pack include
authenticating access to the Elasticsearch cluster's data and encrypting
Elasticsearch's internal and external communications. These are necessary
security features for most production systems. A Liferay Enterprise Search
(LES) subscription gets you access to two X-Pack Connectors for @product@:
monitoring and security. Contact
[Liferay's Sales department for more information](https://www.liferay.com/contact-us#contact-sales).

| **Note:** If you upgrade to @product@ version 7.2, there's an additional LES
| offering:
| [Learning to Rank](https://help.liferay.com/hc/en-us/articles/360035444892-Liferay-Enterprise-Search-Learning-to-Rank)

Here's an overview of using LES with @product@:

1.  Get a [LES subscription](https://help.liferay.com/hc/en-us/articles/360014400932).

2.  You'll receive a license for X-Pack. Install it on your Elasticsearch servers.

2.  [Download](https://customer.liferay.com/downloads)
    and install the LES apps you purchased.

3.  Configure the X-Pack connectors with the proper credentials and encryption
    information.

4.  Restart Elasticsearch. These steps require a full cluster restart.


| **Note:** Out of the box, X-Pack comes with a [30-day
| trial](https://www.elastic.co/guide/en/x-pack/current/license-management.html).
| This can be useful if there's a delay between your subscruption and receipt of
| your production X-Pack license.

Following these instructions gives you a working installation of Elasticsearch
communicating freely with @product@. Elastic's documentation explains additional
configuration options, features, and the architecture of
[X-Pack](https://www.elastic.co/guide/en/elasticsearch/reference/6.8/configuring-security.html). 

Now you can configure security and/or monitoring, depending on your needs.
