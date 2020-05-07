---
header-id: introduction-to-liferay-search
---

# 検索

[TOC levels=1-4]

Liferayは情報をデータベースに保存しているので、データベースを直接検索してみてください。検索エンジンの複雑さが増しているのはなぜでしょうか。第一に、データベーステーブルのマージはコストがかかるからです。サーチインデックス内のドキュメントには、多くの場合、データベース内の複数のテーブルからの検索が可能なフィールドが含まれています。この方法で日付を移動するには時間がかかりすぎます。

パフォーマンスの問題に加えて、検索エンジンでは関連性やスコアなどの追加機能にもアクセスできます。対照的に、データベースはファジー検索や関連性などの機能をサポートしていません。さらに、検索エンジンは、「More Like This」などのアルゴリズムを適用して、類似するコンテンツを返すことができます。検索エンジンは、地理位置情報、検索結果のファセット、および多言語検索もサポートしています。

このセクションには、Liferayの検索機能の拡張、@product@でのカスタムエンティティのインデックス作成と検索、およびニーズに合わせた開発者向けの組み込みElasticsearchサーバーの設定に関する情報が含まれています。まず、いくつかの基本的な検索概念から説明します。

## 基本的な検索概念

**インデックス作成**：インデックス作成中に、ドキュメントが検索エンジンに送信されます。このドキュメントには、さまざまなタイプのフィールドのコレクション（文字列など）が含まれています。検索エンジンは、ドキュメント内の各フィールドを処理します。検索エンジンは、フィールドごとに、フィールドを単に保存するだけでよいか、または特別な分析（インデックス時間分析）を行う必要があるかどうかを判断します。インデックス時間分析は、フィールドごとに構成できます（マッピング定義を参照）。

分析が必要なフィールドの場合、検索エンジンは最初に値をトークン化して、個々の単語またはトークンを取得します。トークン化した後、検索エンジンは一連のアナライザーに各トークンを渡します。アナライザーはさまざまな機能を実行します。一般的な単語やストップワード（「the」、「and」、「or」など）を削除するものもあれば、すべての文字を小文字にする操作などを実行するものもあります。

**検索**：検索では、検索クエリを送信し、検索エンジンから結果（別名ヒット）を取得します。検索クエリは、クエリとフィルターの両方で構成される場合があります（これについては後で説明します）。各クエリまたはフィルターは、検索するフィールドと一致する値を指定します。検索クエリを受信すると、検索エンジンはネストされたクエリおよびフィルター内の各フィールドを反復処理します。このプロセス中に、エンジンはクエリを実行する前に特別な分析を実行する場合があります（検索時間分析）。検索時間分析は、フィールドごとに構成できます（マッピング定義を参照）。

## マッピング定義

検索エンジンはセミインテリジェントで、渡されたドキュメントの処理方法を自動的に解読します。ただし、フィールドの処理方法を明示的に設定することが望ましい場合もあります。

_マッピング_は、検索エンジンが特定のフィールドを処理する方法を制御します。たとえば、フィールド名が「es_ES」で終わる場合、フィールド値をスペイン語として処理し、「si」などの一般的なスペイン語の単語を削除します。

Liferayポータルでサポートされている2つの検索エンジンであるElasticsearchとSolrでは、マッピングはそれぞれ`liferay-type-mappings.json`と`schema.xml`で定義されています。

ElasticsearchマッピングのJSONファイルは、`portal-search-elasticsearch6`モジュールにある@product@の[ソースコード](https://www.liferay.com/downloads-community)で確認できます。

    portal-search-elasticsearch6-impl/src/main/resources/META-INF/mappings/liferay-type-mappings.json

Solr `schema.xml`は、`portal-search-solr7`モジュールのソースコードで確認できます。

    portal-search-solr7-impl/src/main/resources/META-INF/resources/schema.xml

[ここ](https://github.com/liferay/liferay-portal/blob/7.1.x/modules/apps/portal-search-solr7/portal-search-solr7-impl/src/main/resources/META-INF/resources/schema.xml)から、`liferay-portal`リポジトリからSolr 7モジュールのソースコードにアクセスします。

これらは、製品に付属しているデフォルトのマッピングファイルです。ニーズに合わせてこれらのマッピングをさらにカスタマイズできます。たとえば、カスタムインベントリ番号フィールドに特別なアナライザーを使用できます。

## Liferay検索インフラストラクチャ

検索エンジンはすでにネイティブAPIを提供しています。Liferayが検索エンジンをラップする検索インフラストラクチャを提供するのはなぜでしょう。Liferayの検索インフラストラクチャには、次のような機能があります。

1. Liferayが必要とするフィールドで文書にインデックスを作成する（`entryClassName`、`entryClassPK`、`assetTagNames`、`assetCategories`、`companyId`、`groupId`、ステージングステータスなど）。

2. 検索クエリに適切なフィルターを適用する（たとえば、結果の範囲を限定するため）。

3. 結果に権限チェックを適用する。

4. 返された結果を要約する。

これは、Liferayの検索インフラストラクチャのほんの一部です。引き続き読み進め、詳細をご確認ください。