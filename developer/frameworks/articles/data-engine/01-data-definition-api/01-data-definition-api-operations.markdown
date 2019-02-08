# Operations of the Data Definitions API

Each `DEDataDefinition[Operation]Request` object has required parameters. These,
of course, must be specified for the operation to succeed. Requests can also
have optional parameters, which will change the nature of the operation's
outcome if included. 

Some operations even have additional methods that can be called while the
request is being constructed. These are referred to as _optional builder
methods_ in this article, because they are methods you can call while building
the request. Perhaps the most complex request you can build is for defining
permissions on a Data Definition. This example gives Site Member Users
permission to update, delete, and view a particular Data Definition:

    DEDataDefinitionSaveModelPermissionsRequest deDataDefinitionSaveModelPermissionsRequest =
            DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
                companyId, group.getGroupId(), adminUser.getUserId(), group.getGroupId(),
                deDataDefinition.getDEDataDefinitionId(), new String[] {"Site Member"}
                ).allowUpdate().allowView().allowDelete().build();

For a request that can contain optional builder methods, if no optional builder
methods are called when building the request, nothing at all happens. No
exceptions are thrown because these are optional, but nothing useful happens in
the system either. Make sure that you build the request properly to return the
proper response.

## Getting a List of Data Definitions

To get a `List` of `DEDataDefinition`s, pass a `DEDataDefinitionListRequest` to
`DEDataDefinitionService.execute()`.

Required parameters: 

- `long companyId`
- `long groupId`

Optional parameters:

- `int start`
- `int end`

Include the optional parameters to create the request with pagination.

## Getting a Data Definition

To get a single `DEDataDefinition`, pass a `DEDataDefinitionGetRequest` to
`DEDataDefinitionService.execute()`.

Required parameter: `deDataDefinitionId`
Optional parameters: none

## Counting Data Definitions

To get an `int` representing the total number of `DEDataDefinition`s in the
system, pass a `DEDataDefinitionCountRequest` to
`DEDataDefinitionService.execute()`.

Required parameters: 

- `long companyId`
- `long groupId`

Optional parameters: none

## Searching Data Definitions

To get a paginated `List` of `DEDataDefinition`s with a name or description
matching the specified `keywords`, pass a `DEDataDefinitionSearchRequest` to
`DEDataDefinitionService.execute()`.

Required parameters: 

- `long companyId`
- `long groupId`
- `String keywords`

Optional parameters:

- `int start`
- `int end`

When a data definition is created, it includes a name and description field. The
search operation returns any data definition with at least one of the keywords
in its name or description. It also implements relevance scoring, so data
definitions that best match the keywords are returned first. Results are
paginated if the optional `start` and `end` parameters are included.

## Counting the Searched Data Definitions

To get an `int` count of the `DEDataDefinition`s matching a `keywords`
parameter, pass a `DEDataDefinitionSearchCountRequest` to
`DEDataDefinitionService.execute()`.

Required parameters: 

- `long companyId`
- `long groupId`
- `String keywords`

Optional parameters: none

## Saving Data Definitions

Data Definition creation happens in two steps: create, then save. To save a
programmatically created `DEDataDefinition`, pass a
`DEDataDefinitionSaveRequest` to `DEDataDefinitionService.execute()`.

Required parameters:

- `long groupId`
- `long userId`

Optional parameters: none

## Deleting Data Definitions

To delete a `DEDataDefinition`, pass a `DEDataDefinitionDeleteRequest` to
`DEDataDefinitionService.execute()`.

Required parameter: `long dataDefinitionId`
Optional parameters: none

## Granting and Revoking Permissions on Data Definitions

The permissions operations for `DEDataDefinition`s work similarly to the other
operations. Pass a `DEDataDefinition*PermissionsRequest` to
`DEDataDefinitionService.execute`. There are permissions for granting and
revoking permissions on Data Definitions.

### Granting and Revoking Permission to Add Data Definitions and Manage Permissions

`DEDataDefinitionSavePermissionsRequest` is for granting one or more Roles
permission to add a new Data Definition to the system, and/or the ability to
manage User permissions on Data Definitions.

Required parameters: 

- `long companyId`
- `long scopeGroupId`
- `String[] roleNames`

Optional parameters: none

Optional builder methods:

- `allowAddDefinition()` to give Role Users permission Add new Data Definitions
- `allowDefinePermissions()` to give Role Users permission to grant Data
    Definition permissions for other Roles. Once a Role has this permission, its
    Users can 
    - Grant other Roles permission to Add, Delete, View, Update, or Define
        Permissions.
    - Revoke the same permissions.
    - Grant themselves permission to Add, Delete, View, Update, or Define
        Permissions if they don't already have those permissions. 

Specify more than one builder method to grant multiple permissions
simultaneously.

`DEDataDefinitionDeletePermissionsRequest` is for revoking the same permissions
granted by the `DeDataDefinitionSavePermissionsRequest`. 

Required parameters: 

- `long companyId`
- `long scopeGroupId`
- `String[] roleNames`

Optional parameters: none

Once the `DEDataDefinitionDeletePermissionsRequest` is executed, all specified
Roles lose both permissions simultaneously: Role Users can no long add Data
Definitions or manage permissions for Data Definitions. 

### Granting and Revoking Permission to View, Update, or Delete a specific Data Definition

`DEDataDefinitionSaveModelPermissionsRequest` is for granting permission to
update, view, and/or delete a specific Data Definition already in the system. 

Required parameters:

- `long companyId`
- `long groupId`
- `long scopeUserId`
- `long scopeGroupId`
- `long deDataDefinitionId`
- `String[] roleNames`

Optional parameters: none

Optional builder methods:

- `allowDelete()` to grant deletion permission
- `allowUpdate()` to grant update permission
- `allowView()` to grant view permission

Specify more than one builder method to grant multiple permissions
simultaneously.

`DEDataDefinitionDeleteModelPermissionsRequest` is for revoking permission to
view, update, and/or delete a specific data definition already in the system. If
a user choose to make this type of delete request, all the roles listed in the
request will lose whichever permissions are specified in the `actionIds`
parameter for the given Data Definition.

Required parameters:

- `long companyId`
- `long scopeGroupId`
- `long deDataDefinitionId`
- `String[] actionIds`
- `String[] roleNames`

Optional parameters: none

The possible action IDs are: `DELETE`, `UPDATE`, and `VIEW`.

