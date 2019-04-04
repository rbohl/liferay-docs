# Forms

The 
[Liferay Forms](/7-2/user/-/knowledge_base/user/liferay-forms)
application is a full-featured form building tool for collecting data. There's
lots of built-in functionality, and for the pieces you're missing, there's lots
of extensibility.

Lucky for you, the great functionality Liferay's form builder provides aren't
locked up in a proprietary code base. On the contrary, Liferay is more like
XXXXXXX than Fort Knox. Use the Forms API freely, and create wonderful
form-based applications. 

In addition, there's a new set of APIs you can use in your applications and
other code. It's a form based API that's meant for building forms, collecting
the data from those forms, and then displaying the data. If you've been around
Liferay Portal for a while, you'll recognize how much this description resembles
[Dynamic Data Lists](/7-2/user/-/knowledge_base/user/ddl). That's because Data
Engine is becoming the replacement for DDL in @product-ver@. DDL and its API is
still available, but it's deprecated and will be entirely replaced in a future
version. If you're new to Liferay, become familiar with the Forms API and the
Data Engine API. If you've already harnessed DDL APIs in your code, it's time to
start migrating off of that code and onto the new APIs.

Since there are multiple avenues to coding up Forms functionality in your
applications, use this table to understand the use cases for each one:

| API               |      Module     |  Deprecated? | Define a Form's Fields | Create a Collection of   
|-------------------|-----------------|--------------|
| Forms             | dynamic-data-mapping  | No           |
| Data Engine       | data-engine              | No           |
| Dynamic Data Lists| left-aligned    | Yes          |


|       | Forms | Data Engine | Dynamic Data Lists |
|-------|-------|-------------|--------------------|
| Module | | | |
| Deprecated | | | |
| 


# Collaboration

[TOC levels=1-4]

Underlying the 
[collaboration suite](/discover/portal/-/knowledge_base/7-2/collaboration) 
is a set of powerful APIs that add collaboration features to your apps. For 
example, if your app contains a custom content type, you can use the 
collaboration suite's social API to enable comments and ratings for that 
content. You can also integrate your app with the Documents and Media Library, 
and much more. 

Here are a few of the things you can do with the collaboration suite's APIs. 

## Data Engine

Past versions of @product@ included a robust framework for creating form and
list-based applications, either programmatically or from the Dynamic Data Lists
Application. While Dynamic Data Lists continue to exist, a new framework was
written to be based on the Forms Application's backend. ????? SAY WHY ?????. In
@product-ver@, only the backend APIs are available for developers.

The Data Engine framework has three fundamental entities. You'll be familiar
with these entities if you used the Dynamic Data List framework in the past.

1.  Data Definitions
2.  Data Record Collections
3.  Data Records

##  Data Definitions

Data Definitions (`DEDataDefinition`s) hold the form's field types (e.g., text,
boolean, date, radio buttons, selector menus, etc.) and those fields' labels and
settings. Data definitions effectively serve as data models for a Data
Collection. For example, you could create a data definition with two text
fields: one for a user's name, and one for their comments. You could then
display a form that gathers user feedback via a data record collection that uses that
data definition. 

## Data Record Collections

Data Record Collections (`DEDataRecordCollection`s) collect Data Records.
They're used to associate a displayed form with the data records it holds and
its backing data definition.

## Data Records

Data Records are the individual form entries for a given Data Record
Collection.

## Data Views

There's another important piece to the Data Engine framework: displays.

NOT WITH TEMPLATES. TALK TO JEYVISON. Once you have a Data Definition and a Data
Collection, it's common to need control over display. Do this with FreeMarker
templates, like in the Dynamic Data Lists application.


