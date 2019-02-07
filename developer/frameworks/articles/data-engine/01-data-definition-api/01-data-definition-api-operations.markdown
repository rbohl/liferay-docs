# Operations of the Data Definitions API

Each `DEDataDefinition[Operation]Request` object has required parameters. These,
of course, must be specified for the operation to succeed. Requests can also
have optional parameters, which will change the nature of the operation's
outcome if included. 

Some operations even have additional methods that can be called while the
request is being constructed. These are referred to as _Optional <!--are they
optional though?--> builder methods_ in this article, because they are methods
you can call while building the request. Perhaps the most complex 

    DEDataDefinitionSaveModelPermissionsRequest deDataDefinitionSaveModelPermissionsRequest =
            DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
                companyId, group.getGroupId(), adminUser.getUserId(), group.getGroupId(),
                deDataDefinition.getDEDataDefinitionId(), new String[] {"Site Member"}
                ).allowUpdate().allowView().allowDelete().build();

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
in its name or description. It also implements relevance scoring, so that data
definitions that best match the keywords are returned first.  Results are
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
- `allowAddDefinition()` to grant Add permissions
- `allowDefinePermissions()` to grant permissions to grant permissions
<!-- Find a better way to say the above -->

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
update, view, and delete a specific Data Definition already in the system. 

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

Specify more than one builder method to grant mutliple permissions
simultaneously.

Here's an example request builder that grants Site Members all three permissions:

`DEDataDefinitionDeleteModelPermissionsRequest` is for revoking permission to
view, update, or delete two permissions: the permission to define new Data
Definition permissions for a User, and the permission to add a new entity
registry in the system. If a user choose to make this type of delete request,
all the roles listed in the request will lose both permissions at the same
time. 

Required parameters:
- `long companyId`
- `long scopeGroupId`
- `long deDataDefinitionId`
- `String[] actionIds`
- `String[] roleNames`

Optional parameters: none

The possible action IDs are: `DELETE`, `UPDATE`, and `VIEW`.

