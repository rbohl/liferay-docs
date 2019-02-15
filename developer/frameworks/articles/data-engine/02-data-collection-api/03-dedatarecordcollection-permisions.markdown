# Granting and Revoking Model Permissions on Data Record Collections

To demonstrate the use of the Data Record Collections API, consider the
operation for granting and revoking permissions on a particular
`DEDataRecordCollection` model object.

The API supports granting permissions for these actions, shown by their action
ID and the corresponding builder method:

- `DELETE`/`allowDelete()`: delete the record collection
- `UPDATE`/`allowUpdate()`: update the record collection's description and name
- `VIEW`/`allowView()`: view the record collection in the UI
- `ADD_DATA_RECORD`/`allowAddDataRecord()`: add a data record to the record collection 
- `DELETE_DATA_RECORD`/`allowDeleteDataRecord()`: delete a record form the record collection
- `UPDATE_DATA_RECORD`/`allowUpdateDataRecord()`: update a data record in the record collection
- `VIEW_DATA_RECORD`/`allowViewDataRecord()`: view a data record in the record collection

Each action has an `actionId`, as presented above (e.g., `DELETE`). This ID is
used explicitly by developers when building a
`DEDataRecordCollectionDeleteModelPermissionsRequest`.  When building a _save_
request, the IDs are not used by the developer directly. Instead, the
`DEDataRecordCollectionSaveModelPermissionsRequestExecutor` class in the Data
Engine's internal code checks whether a particular builder method was called,
and parses it to the correct `actionId` for you. For example, a call to
`allowDelete()` in the request builder code is mapped in the request  executor
code to `DELETE`.

## Granting Model Permissions for Multiple Data Record Collection Actions

1.  First build a `DEDataRecordCollectionRequest` that includes these parameters:

    - `long companyId`: the Company ID of the portal instance
    - `long groupId`: the ID of the Group where the permissions request will apply
    - `long scopeUserId`: the ID of the User invoking the
        `saveModelPermissionsBuilder` method
    - `long scopeGroupId`: the Group the Scoped User belongs to
    - `long deDataRecordCollectionId`: the ID of the Data Record Collection this
        request applies to
    - `String[] roleNames`: the Roles this request applies to

    Also call the builder methods for the permissions you'll grant in the request.
    This example uses `allowDelete` and `allowView`.

    Here's a , including any
    of the optional builder methods you need.

        DEDataRecordCollectionSaveModelPermissionsRequest deDataRecordCollectionSaveModelPermissionsRequest = DEDataRecordCollectionRequestBuilder
                .saveModelPermissionsBuilder(companyId, group.getGroupId(), adminUser.getUserId(), group.getGroupId(),
                        deDataRecordCollection.getDEDataRecordCollectionId(), new String[] { "Site Member" })
                .allowView().allowDelete().build();

    This `DEDataRecordCollectionSaveModelPermissionsRequest` was built to grant
    permission to Site Members to view and delete a particular Data Record
    Collection.

2.  Next, pass the request object to the `DEDataRecordCollectionService`'s
    `execute` method.

        DEDataRecordCollectionSaveModelPermissionsResponse deDataRecordCollectionSaveModelPermissionsResponse =
                    _deDataRecordCollectionService.execute(deDataRecordCollectionSaveModelPermissionsRequest);

    The service's `execute` method is overloaded. Passing it a properly
    constructed `DEDataRecordCollectionSaveModelPermissionsRequest` is enough to
    ensure that a `DEDataRecordCollectionSaveModelPermissionsResponse` is
    returned. 

The response in this case is simple: you receive the ID of the
`DEDataRecordCollection` object that's had its permissions modified. 

So where did the permissions actually get added? In
`DEDataRecordCollectionSaveModelPermissionsRequestExecutor`, the
`ResourceLocalService` service's `addModelResourcePermissions` call was
executed.
