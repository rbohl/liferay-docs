# Operations of the Data Definitions API

Each `DEDataDefinition[Operation]Request` object has required parameters. These,
of course, must be specified for the operation to succeed. Requests can also
have optional parameters, which will change the nature of the operation's
outcome if included. 

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

<!-- API NOT READY FOR DOCS YET -->
<!--
## Defining Permissions on Data Definitions

To get a `List` of `DEDataDefinition`, pass a `DEDataDefinitionListRequest` to
`DEDataDefinitionService.execute()`.
-->
