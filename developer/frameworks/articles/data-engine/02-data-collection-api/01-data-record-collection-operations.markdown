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
permissions on a Data Record Collection. See the 
[dedicated article on crafting Data Record collection permissions requests for details on this](LINK).

If no optional builder methods are called when building a request that accepts
them, nothing at all happens. No exceptions are thrown because these are
optional, but nothing useful happens in the system either. Make sure that you
build the request properly to return the proper response.

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
- `allowAddDataRecord()` to grant add permissions to add `DEDataRecord`
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

## Deleting a Data Record Collection

To delete a `DEDataRecordCollection`, pass a `DEDataRecordCollectionDeleteRequest` as parameter to
`DEDataRecordCollectionService.execute()`.

Required parameter: none
Optional parameters: none

Optional builder methods:

- `byId(long deDataRecordCollectionId)` sets the ID from the DEDataRecordCollection that will be deleted

Always include the `byId` method, otherwise no data will be deleted.

## Deleting a Data Record

To delete a `DEDataRecord`, pass a `DEDataRecordCollectionDeleteRecordRequest` as parameter to
`DEDataRecordCollectionService.execute()`.

Required parameter: `long deDataRecordCollectionId`
Optional parameters: none

## Getting a Data Record Collection

To get a single `DEDataRecordCollection`, pass a `DEDataRecordCollectionGetRequest` as parameter to
`DEDataRecordCollectionService.execute()`.

Required parameter: none
Optional parameters: none

Optional builder methods:

- `byId(long deDataRecordCollectionId)` sets the DEDataRecordCollection ID that will be retrieved

Always include the `byId` method, otherwise no data will be retrieved.

## Getting a Data Record

To get a single `DEDataRecord`, pass a `DEDataRecordCollectionGetRecordRequest` as parameter to
`DEDataRecordCollectionService.execute()`.

Required parameter: `deDataRecordId`
Optional parameters: none

## Saving a Data Record

To save new or edited `DEDataRecord`s to the database, pass a
`DEDataRecordCollectionSaveRecordRequest` as parameter to
`DEDataRecordCollectionService.execute()`.

Required parameter: `DEDataRecord` object
Optional parameters: none

Optional builder methods:

- `onBehalfOf(long userId)` sets the user ID responsible for the request
- `inGroup(long groupId)` sets the group ID responsible for the request

Always include the `inGroup` and `onBehalfOf` methods, otherwise no data will be saved.

Whatever 
[`DEDataRecord` object you construct](LINK TO CREATION ARTICLE) 
is saved once the request is executed. 

## Getting a List of Data Collections

To get a `List` of `DEDataRecordCollection`s from the database, pass a
`DEDataRecordCollectionListRequest` to
`DEDataRecordCollectionService.execute()`.

Required parameters: none
Optional parameters: none

Optional builder methods:

- `inGroup(long groupId)` narrows the results to a particular `Group`.
- `startingAt(int start)` sets the start position for pagination
- `endingAt(int end)` sets the ending position for pagination

Always include the `inGroup` method, otherwise no data will be returned. If the
results are to be paginated, it's best to include the start and end parameters.
If you only include the start method, you'll get an empty list returned, and
you probably don't want this. If you only include the `endingAt` position,
pagination will work as expected, with the initial index beginning at the first
item in the list.

## Getting a List of Data Records

To get a `List` of all the `DEDataRecord`s in a Record Collection, pass a
`DEDataRecordCollectionListRecordRequest` to
`DEDataRecordCollectionService.execute()`.

Required parameter: `deDataRecordCollectionId`

Optional builder methods:

- `startingAt` sets the start position for pagination
- `endingAt` sets the ending position for pagination

All the Data Records associated with the Data Record collection are returned
when the request is executed. 
