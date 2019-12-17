---
header-id: form-storage-adapters
---

# Form Storage Adapters

[TOC levels=1-4]

When a User adds a form record, the Forms API routes the processing of the
request through a storage adapter API. The same is true for the other *CRUD*
operations performed on form entries (read, update, and delete operations). The
default implementation of the storage service is called `DDMJSONStorageAdapter`,
and as its name implies, it implements the `DDMStorageAdapter` interface to provide
JSON storage of form entry data.

The Dynamic Data Mapping (DDM) backend can *adapt* to other data storage formats
for form records. Want to store your data in XML? YAML? No problem. Because the
storage API is separated from the regular service calls used to populate the
database table for form entries, a developer can even choose to store form data
outside the Liferay database.
<!--A Diagram?-->

Define your own format to save form entries by writing your own implementation
of the `DDMStorageAdapter` interface. The interface follows the *CRUD* approach, so
implementing it requires that you write methods to create, read, update and
delete form values.

| **Note:** The `StorageAdapter` interface and it's abstract implementation,
| `BaseStorageAdapter`, are deprecated in @product-ver@. Migrate existing code
| using these classes to implement the `DDMStorageAdapter` interface.

A newly added storage adapter can only be used with new Forms. All existing
Forms continue to use the adapter selected (JSON by default) at the time of
their creation, and a different storage adapter cannot be selected.

The example storage adapter in this tutorial serializes form data to be stored
in a simple file, stored on the file system.

![Figure 1: Choose from available Storage Types.](../../images/forms-storage-type.png)

## The `DDMStorageAdapter` Interface

You must implement three methods: `delete`, `get`, and `save`.

```java
public DDMStorageAdapterDeleteResponse delete(
        DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
    throws StorageException;

public DDMStorageAdapterGetResponse get(
        DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
    throws StorageException;

public DDMStorageAdapterSaveResponse save(
        DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
    throws StorageException;
```

Each method must return a
_DDMStorageAdapter[[Save](https://github.com/liferay/liferay-portal/blob/7.2.0-ga1/modules/apps/dynamic-data-mapping/dynamic-data-mapping-api/src/main/java/com/liferay/dynamic/data/mapping/storage/DDMStorageAdapterSaveResponse.java)/[Get](https://github.com/liferay/liferay-portal/blob/7.2.0-ga1/modules/apps/dynamic-data-mapping/dynamic-data-mapping-api/src/main/java/com/liferay/dynamic/data/mapping/storage/DDMStorageAdapterGetResponse.java)/[Delete](https://github.com/liferay/liferay-portal/blob/7.2.0-ga1/modules/apps/dynamic-data-mapping/dynamic-data-mapping-api/src/main/java/com/liferay/dynamic/data/mapping/storage/DDMStorageAdapterDeleteSaveResponse.java)]Response_
object, each constructed using a static inner `Builder` class's `newBuilder`
method. 

Each method of the storage adapter is passed a very useful
`DDMStorageAdapter[Save/Delete/Get]Request`. The request objects contain getter
methods that return useful contextual information.

You must only worry about your implementation needs and building/returning the
appropriate response objects. The Forms application's service code looks up the
appropriate storage adapter, and calls its code against the
`DDMFormInstanceRecord` for the backing form is configured to use the storage
adapter.

From `DDMFormInstanceRecordLocalServiceImpl`:

```java
DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter();

DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
    ddmStorageAdapter.save(ddmStorageAdapterSaveRequest);
```

## `DDMStorageAdapter[Save/Delete/Get]Request` Getters

Call the request object's getters to access the necessary data:

`DDMStorageAdapterSaveRequest`:

- Method: `public String getClassName()` 
    Returns a String, `com.liferay.dynamic.data.mapping.model.DDMStorageLink`
    <!-- is this always returned?-->

- Method: `public DDMFormValues getDDMFormValues()`
    Returns the `DDMFormValues` of the form. You need the form values object in
    order to manipulate into the format in which they should be stored.
    <!-- note about serialization here?-->

- Method: `public long getPrimaryKey()`
    Returns the `storageId` <!-- ???--> of the form record, which is useful for
    any work that requires identifying a record. This will be unique for each
    form record in the @product@ database, but isn't guaranteed to be unique
    across portal environments like staging and live. Before the record is
    persisted for the first time (whether through auto-save or user submission
    of the form), this returns `0`.

- Method: `public long getScopeGroupId()`
    Returns the group ID of the current scope. For example, if the Form widget
    is placed on a portal page, the scope group ID returned is the group ID for
    the site. If the form is accessed by direct URL, the group ID returned is a
    group ID dedicated to the Forms application.

- Method: `public long getUserId()`
    Returns the user ID submitting the request. For a save event, this would be
    the @product@ User ID if authenticated in the session, or the ID for the
    default account if an unauthenticated User or Guest User submits a form.

- Method: `public String getUuid()`
    Returns the unique ID of the Form Record, which is useful for identifying
    the form record across portal environments (like staged and live). This is
    often needed to pass to the `ServiceContext` object used in a service call.
    Before the record is persisted, this returns `null`.

- Method: `public boolean isInsert()`
    Returns _true_ if the primary key is `0`, and _false_ if there's an existing
    ID for the form record. The default JSON storage adapter uses it to test
    whether an add or update is called for.

`DDMStorageAdapterGetRequest`:

- Method: `public long getPrimaryKey()`
    Returns the `storageId` of the form record, which is useful for
    any work that requires identifying a record.

- Method: `public DDMForm getDDMForm()`
    Returns the `DDMForm` object of the form the record is associated with.

`DDMStorageAdapterDeleteRequest`:

- Method: `public long getPrimaryKey()`
    Returns the `storageId` of the form record, which is useful for
    any work that requires identifying a record. The default JSON storage
    adapter uses it to retrieve the `DDMContent` object associated with the
    primary key, which, in turn with the form itself (see the next method) is
    used to get deserialized `DDMFormValues`, which can be passed to the delete
    request.

## Implementing a `DDMStorageAdapter`

Here's an overview of what you must accomplish in implementing each storage
adapter method:

`save`
: Detect whether the form record is new or if it already exists (due to the
auto-save feature). If new, call the DDM service for adding `DDMContent` to the
database, passing to it, among other things, the serialized version (transformed
into your storage format) of the `DDMFormValues`.

`delete`
: There's no serialization or deserialization involved with deletion. If you
don't need additional custom logic for deletion, you can use the default storage
adapter's basic deletion code, which calls the DDM content service's `delete`
method, passing the primary key of the form record, as retrieved from the delete
request. This will be demonstrated in the next tutorial. 

`get`
: Get the DDM content using the storage ID of the form record, retrieved from
the request object, then construct the `DDMFormValues` object from the
serialized data you've already stored, by calling the deserialization code. Set
the now-deserialized `DDMFormValues` into the response object. Of course, if you
need to add custom logic in the meantime, do that too.

Serializing and deserializing the form record's content requires some additional
work and documentation for the interfaces Liferay provides will be written. 

The basic idea is that when you're reading `DDMContent`, it must be deserialized
from the storage format back into a `DDMFormValues` object that the
`DDMStorageAdapterGetResponse`'s `Builder` constructor requires. Conversely,
when you're saving `DDMcontent`, you need to get the `DDMFormValues` passed to
the get request, and serialize it into the storage format. There's a
`DDMFormValuesSerializer` to implement for the serialization logic, and a
`DDMFormValuesDeserializer` to implement for the deserialization logic.
Liferay's default implementations, which support transforming the
`DDMFormValues` object to JSON and vice versa, can be found
[here](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/dynamic-data-mapping/dynamic-data-mapping-service/src/main/java/com/liferay/dynamic/data/mapping/internal/io/DDMFormValuesJSONSerializer.java)
and
[here](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/dynamic-data-mapping/dynamic-data-mapping-service/src/main/java/com/liferay/dynamic/data/mapping/internal/io/DDMFormValuesJSONDeserializer.java),
respectively.

All the Java code for the logic discussed here is shown in the next article,
[Creating a Form Storage Adapter](/docs/7-2/customization/-/knowledge_base/c/creating-a-form-storage-adapter).

## Enabling the Storage Adapter

The storage adapter is enabled at the individual form level. Create a new form,
and select the Storage Adapter _before saving or publishing the form_. If you
wait until first Saving the Form, the default Storage Adapter is already
assigned to the Form, and this setting is no longer editable.

1.  Go to the Site Menu &rarr; Content &rarr; Forms, and click the *Add* button
    (![Add](../../images/icon-add.png)).

2.  In the Form Builder view, click the *Options* button
    (![Options](../../images/icon-options.png)) and open the *Settings*
    window. 

3.  From the select list field called *Select a Storage Type*, choose the
    desired type and click _Done_.

Now all the form's entries are stored in the desired format.
