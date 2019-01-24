# Data Engine

Past versions of @product@ included a robust framework for creating form and
list-based applications, either programmatically or from the Dynamic Data Lists
Application. While Dynamic Data Lists continue to exist, a new framework was
written to be based on the Forms Application's backend. ????? SAY WHY ?????. In
@product-ver@, only the backend APIs are available for developers

As part of the rewrite of the Dynamic Data List framework, there are new names
and new APIs to leverage in your own code. The Data Engine framework has three
fundamental entities. You'll be familiar with these entities if you used the
Dynamic Data List framework in the past.

1.  Data Definitions
2.  Data Record Collections
3.  Data Records

##  Data Definitions

Data Definitions (`DEDataDefinition`s) hold the form's field types (e.g., text,
boolean, date, radio buttons, selector menus, etc.) and those fields' labels and
settings. Data definitions effectively serve as data models for a Data
Collection. For example, you could create a data definition with two text
fields: one for a user's name, and one for their comments. You could then
display a form that gathers user feedback via a dynamic data list that uses that
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

Once you have a Data Definition and a Data Collection, it's common to need
control over display. Do this with FreeMarker templates, like in the Dynamic
Data Lists application.



