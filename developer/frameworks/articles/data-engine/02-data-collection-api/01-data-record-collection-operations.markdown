# Operations of the Data Record Collections API

<!--might want to talk more about the request response structure of the data engine apis in a separate article and keep this article to:
1. what's the operation's purpose
2. what are the required parameters
3. what are the optional parameters
4. what are the optional builder methods
5. what's in the response.
could this cut down on word count?
-->

Each `DEDataRecordCollection[Operation]Request` object has required parameters.
These, of course, must be specified for the operation to succeed. Requests can
also have optional parameters, which will change the nature of the operation's
outcome if included. 

Some operations even have additional methods that can be called while the
request is being constructed. These are referred to as _optional builder
methods_ in this article, because they are methods you can call while building
the request. Perhaps the most complex request you can build is for defining
permissions on a Data Record Collection. This example gives Site Member Users
permission to update, delete, and view a particular Data Record Collection:

<!--ADD EXAMPLE CODE FROM GDOC-->

For a request that can contain optional builder methods, if no optional builder
methods are called when building the request, nothing at all happens. No
exceptions are thrown because these are optional, but nothing useful happens in
the system either. Make sure that you build the request properly to return the
proper response.

## Granting and Revoking Permissions on Data Record Collections

Developers can grant and revoke two kinds of Data Record Collection permissions:

1.  Permission to add Data Record Collections and manage their permissions
2.  Permission to take an action on a specific Data Record Collection

The permissions operations for `DEDataRecordCollection`s work similarly to the other
operations. Pass a `DEDataRecordCollection*PermissionsRequest` to
`DEDataRecordCollectionService.execute`. There are permissions for granting and
revoking permissions on Data Record Collections.

### Granting and Revoking Permission to Add Data Record Collections and Manage Permissions

`DEDataRecordCollectionSavePermissionsRequest` is for granting one or more Roles
permission to add a new Data Record Collection to the system, and/or the ability to
manage User permissions on Data Record Collections.

Required parameters: 

- `long companyId`
- `long scopeGroupId`
- `String[] roleNames`

Optional parameters: none

Optional builder methods:

- `allowAddDataRecordCollection()` to give Role Users permission Add new Data
    Record Collections
- `allowDefinePermissions()` to give Role Users permission to grant Data
    Record Collection permissions for other Roles. Once a Role has this
    permission, its Users can 
    - Grant other Roles permission to Add, Delete, View, Update, or Define
        Permissions.
    - Revoke the same permissions.
    - Grant themselves permission to Add, Delete, View, Update, or Define
        Permissions if they don't already have those permissions. 

Specify more than one builder method to grant multiple permissions
simultaneously.

`DEDataRecordCollectionDeletePermissionsRequest` is for revoking the same permissions
granted by the `DEDataRecordCollectionSavePermissionsRequest`. 

Required parameters: 

- `long companyId`
- `long scopeGroupId`
- `String[] roleNames`

Optional parameters: none

Once the `DEDataRecordCollectionDeletePermissionsRequest` is executed, all
specified Roles lose both permissions simultaneously: Role Users can no long add
Data Record Collections or manage permissions for Data Record Collections. 

### Granting and Revoking Permission to View, Update, or Delete a specific Data Record Collection

`DEDataRecordCollectionSaveModelPermissionsRequest` is for granting permission
to add, update, view, and/or delete permissions on Data Records in a specific
Data Record Collection, and/or delete, update, and view permission on he Data
Record Collection itself.

Required parameters:

- `long companyId`
- `long groupId`
- `long scopeUserId`
- `long scopeGroupId`
- `long deDataRecordCollectionId`
- `String[] roleNames`

Optional parameters: none

Optional builder methods:

- `allowDelete()` to grant deletion permission
- `allowUpdate()` to grant update permission
- `allowView()` to grant view permission
- `allowAddDataRecord()` to grant add permissions, to add `DEDataRecord`
    entities in the Data Record Collection
- `allowDeleteDataRecord()` to grant permission to delete `DEDataRecord`s in
    the Data Record Collection
- `allowUpdateDataRecord()` to grant update permission on any `DEDataRecord`
    in the Data Record Collection
- `allowViewDataRecord()` to grant view permission for any `DEDataRecord` entity
    in the Data Record Collection

Specify more than one builder method to grant multiple permissions
simultaneously.

`DEDataRecordCollectionDeleteModelPermissionsRequest` is for revoking any or all
of the permissions in the above _save_ request. If a user choose to make this
type of delete request, all the roles listed in the request will lose whichever
permissions are specified in the `actionIds` parameter for the given Data Record
Collection.

Required parameters:

- `long companyId`
- `long scopeGroupId`
- `long deDataRecordCollectionId`
- `String[] actionIds`
- `String[] roleNames`

Optional parameters: none

The possible action IDs are: 
- `DELETE`, `UPDATE`, and `VIEW` for revoking the permissions on the Data Record
    Collection itself
- `ADD_DATA_RECORD`, `DELETE_DATA_RECORD`, `UPDATE_DATA_RECORD`, and
    `VIEW_DATA_RECORD` for revoking permissions on the Data Records contained in
    the Data Record collection

