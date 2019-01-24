# Data Engine

Past versions of @product@ included a robust framework for creating form and
list-based applications, either programmatically or from the Dynamic Data Lists
Application.  This framework was rewritten to be based on the Forms
Application's backend. ????? SAY WHY ?????. In @product-ver@, only the backend
APIs are available for developers

As part of the rewrite of the Dynamic Data List framework, there are new names
and new APIs to leverage in your own code. The Data Engine framework has three
fundamental entities. You'll be familiar with these entities if you used the
Dynamic Data List framework in the past.

1.  Data Definitions
2.  Data Collections
3.  Data Views

##  Data Definitions

Data Definitions (`DEDataDefinition`s) hold the form's field types (e.g., text,
boolean, date, radio buttons, selector menus, etc.) and those fields' labels and
settings. Data definitions effectively serve as data models for a Data
Collection. For example, you could create a data definition with two text
fields: one for a user's name, and one for their comments. You could then
display a form that gathers user feedback via a dynamic data list that uses that
data definition. 

## Data Collections

Data collections (`DEDataRecordCollection`s) are used to associate a displayed
form with its backing data definition. The relationship between 

## Data Views

Once you have a Data Definition and a Data Collection, it's common to need
control over display. Do this with FreeMarker templates,



