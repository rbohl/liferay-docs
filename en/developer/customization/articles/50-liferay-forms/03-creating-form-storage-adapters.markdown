---
header-id: creating-a-form-storage-adapter
---

# Creating a Form Storage Adapter

[TOC levels=1-4]

To create a storage adapter, implement the `DDMStorageAdapter` interface. The
[`DDMJSONStorageAdapter`](https://github.com/liferay/liferay-portal/blob/7.2.x/modules/apps/dynamic-data-mapping/dynamic-data-mapping-service/src/main/java/com/liferay/dynamic/data/mapping/internal/storage/DDMJSONStorageAdapter.java)
is @product@'s default implementation.
<!-- Once LPS-97208 or LPS-105739 are fixed, we can rpoceed with actually developing this.-->

## Step 1: Declare the Implementation as a Component

In the component declaration, set the `ddm.storage.adapter.type` property so
that the service registry can retrieve your implementation.

```java
@Component(
	immediate = true, property = "ddm.storage.adapter.type=file",
	service = DDMStorageAdapter.class
)
public class DDMFileSystemStorageAdapter implements DDMStorageAdapter {
```

## Step 2: Implement the `save` Method

CRUD operations must be created to properly handle Form Records. Start with
`save`, which serves as the entry point for adding and updating form records
<!--(do we really do anything with updating?)-->

The `save` method takes a `DDMStorageAdapterSaveRequest`. The interface demands
that you return a `DDMStorageAdapterSaveResponse` and handle
`StorageException`s.

```java
public DDMStorageAdapterSaveResponse save(
        DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
    throws StorageException;
```

The default JSON implementation responds differently depending on the value of
a boolean value stored in the save request, `isInsert`. If true, logic for
adding a new form record is invoked, and if false, an update is precipitated
instead. This logic is contained in two protected methods, `insert` and
`update`.

```java
@Override
public DDMStorageAdapterSaveResponse save(
        DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
    throws StorageException {

    if (ddmStorageAdapterSaveRequest.isInsert()) {
        return insert(ddmStorageAdapterSaveRequest);
    }

    return update(ddmStorageAdapterSaveRequest);
}

protected DDMStorageAdapterSaveResponse insert(
        DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
    throws StorageException {

    DDMFormValues ddmFormValues =
        ddmStorageAdapterSaveRequest.getDDMFormValues();

    try {
        ServiceContext serviceContext = new ServiceContext();

        serviceContext.setScopeGroupId(
            ddmStorageAdapterSaveRequest.getScopeGroupId());
        serviceContext.setUserId(ddmStorageAdapterSaveRequest.getUserId());
        serviceContext.setUuid(ddmStorageAdapterSaveRequest.getUuid());

        DDMContent ddmContent = ddmContentLocalService.addContent(
            ddmStorageAdapterSaveRequest.getUserId(),
            ddmStorageAdapterSaveRequest.getScopeGroupId(),
            ddmStorageAdapterSaveRequest.getClassName(), null,
            serialize(ddmFormValues), serviceContext);

        DDMStorageAdapterSaveResponse.Builder builder =
            DDMStorageAdapterSaveResponse.Builder.newBuilder(
                ddmContent.getPrimaryKey());

        return builder.build();
    }
    catch (Exception e) {
        throw new StorageException(e);
    }
}

protected DDMStorageAdapterSaveResponse update(
        DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
    throws StorageException {

    try {
        DDMContent ddmContent = ddmContentLocalService.getContent(
            ddmStorageAdapterSaveRequest.getPrimaryKey());

        /* The modified date is set here instead of in the service layer.
        */
        ddmContent.setModifiedDate(new Date());

        ddmContent.setData(
            serialize(ddmStorageAdapterSaveRequest.getDDMFormValues()));

        ddmContentLocalService.updateContent(
            ddmContent.getPrimaryKey(), ddmContent.getName(),
            ddmContent.getDescription(), ddmContent.getData(),
            new ServiceContext());

        DDMStorageAdapterSaveResponse.Builder builder =
            DDMStorageAdapterSaveResponse.Builder.newBuilder(
                ddmContent.getPrimaryKey());

        return builder.build();
    }
    catch (Exception e) {
        throw new StorageException(e);
    }
}

protected String serialize(DDMFormValues ddmFormValues) {
    DDMFormValuesSerializerSerializeRequest.Builder builder =
        DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
            ddmFormValues);

    DDMFormValuesSerializerSerializeResponse
        ddmFormValuesSerializerSerializeResponse =
            jsonDDMFormValuesSerializer.serialize(builder.build());

    return ddmFormValuesSerializerSerializeResponse.getContent();
}

@Reference
protected DDMContentLocalService ddmContentLocalService;

@Reference(target = "(ddm.form.values.deserializer.type=json)")
protected DDMFormValuesDeserializer jsonDDMFormValuesDeserializer;

@Reference(target = "(ddm.form.values.serializer.type=json)")
protected DDMFormValuesSerializer jsonDDMFormValuesSerializer;
```

## Step 3: Implement the `delete` Method

Put form record deletion logic in the `delete` method.

The `delete` method takes a `DDMStorageAdapterDeleteRequest`. The interface
demands that you return a `DDMStorageAdapterDeleteResponse` and handle
`StorageException`s.

```java
public DDMStorageAdapterDeleteResponse delete(
        DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
    throws StorageException;
```

The default storage adapter's implementation:

```java
@Override
public DDMStorageAdapterDeleteResponse delete(
        DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
    throws StorageException {

    try {
        ddmContentLocalService.deleteDDMContent(
            ddmStorageAdapterDeleteRequest.getPrimaryKey());

        DDMStorageAdapterDeleteResponse.Builder builder =
            DDMStorageAdapterDeleteResponse.Builder.newBuilder();

        return builder.build();
    }
    catch (Exception e) {
        throw new StorageException(e);
    }
}
```

## Step 4: Implement the `get` Method

Put form record retrieval logic in the `get` method.

The `get` method takes a `DDMStorageAdapterGetRequest`. The interface
demands that you return a `DDMStorageAdapterGetResponse` and handle
`StorageException`s.

```java
public DDMStorageAdapterGetResponse get(
        DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
    throws StorageException;
```

The default implementation:

```java
@Override
public DDMStorageAdapterGetResponse get(
        DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
    throws StorageException {

    try {
        // get the ddm content using the storaeId from the passed request
        DDMContent ddmContent = ddmContentLocalService.getContent(
            ddmStorageAdapterGetRequest.getPrimaryKey());

        // pass the the form object and the ddm content to the deserializer
        // to obtain a pure DDMFormValues object out of the stored JSON.
        DDMFormValues ddmFormValues = deserialize(
            ddmContent.getData(), ddmStorageAdapterGetRequest.getDDMForm());

        // build a response, passing the ddmFormValues
        DDMStorageAdapterGetResponse.Builder builder =
            DDMStorageAdapterGetResponse.Builder.newBuilder(ddmFormValues);

        return builder.build();
    }
    catch (Exception e) {
        // throw a storage exception when an exception is caught
        throw new StorageException(e);
    }
}

protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
    DDMFormValuesDeserializerDeserializeRequest.Builder builder =
        DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
            content, ddmForm);

    DDMFormValuesDeserializerDeserializeResponse
        ddmFormValuesDeserializerDeserializeResponse =
            jsonDDMFormValuesDeserializer.deserialize(builder.build());

    return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
}
```

Deploy your storage adapter and it's ready to use.
