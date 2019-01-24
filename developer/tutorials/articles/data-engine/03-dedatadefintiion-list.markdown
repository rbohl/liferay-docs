# Listing Data Definitions

The List API can be called from Java code or using HTTP GET request.

## Listing Data Definitions in Java

The API for listing Data Definitions does not support filtering. To get an
unfiltered list of all the `DEDataDefinition`s in the database,

1. Create a `DEDataDefinitionListRequest`, with or without pagination start and
   end parameters. 

    Here's a request without pagination:

        DEDataDefinitionListRequest deDataDefinitionListRequest =
                        DEDataDefinitionRequestBuilder.listBuilder(
                        ).inCompany(
                            _group.getCompanyId()
                        ).inGroup(
                            _group.getGroupId()
                        ).build();

    Here's a request with pagination: 

        DEDataDefinitionListRequest deDataDefinitionListRequest =
                    DEDataDefinitionRequestBuilder.listBuilder(
                    ).startingAt(
                        3
                    ).endingAt(
                        7
                    ).inCompany(
                        _group.getCompanyId()
                    ).inGroup(
                        _group.getGroupId()
                    ).build();

2.  Get the response, passing the request into the
    `DEDatadefinitionListResponse`'s `execute` method:

        DEDataDefinitionListResponse deDataDefinitionListResponse =
                    _deDataDefinitionService.execute(deDataDefinitionListRequest);

## Listing Data Definitions with a HTTP POST Request

There's an accompanying GraphQL API in place for the List API, allowing you to
list the Data Definitions with two HTTP POST requests; one that builds a
`DEDataDefinitionListRequest`, and another that builds a
`DEDataDefinitionListResponse`. There's a handy `DEGraphQLServlet` that links
the List Java API with the GraphQL API.

List Request/Response (API Module)
DEDataDefinitionListRequest
Build the request;
Requires group ID, company ID, start index and end index to build the request;
GroupID and company ID is also part of permission checking;
DEDataDefinitionListResponse
Build the response;
Has a list property, that contains all the data definition registry saved;

List Executor
DEDataDefinitionListRequestExecutor
Responsible for invoking the list operation in DDMStructure API;
According to group ID and company ID contained in the list request, the execute method returns a response with a list of DEDataDefinitions. The caller can specify indexes for pagination; 
Two auxiliaries methods are implemented to parse ddmStructures to DEDataDefinition objects;
Map: Does a mapping between ddmStructe and DEDataDefinition;
Deserialize: Do the ddmStructure fields deserializing process, converting JSON to Java objects;

Service implementation
DEDataDefinitionRequestBuilder
Calls operations builder methods; 
DEDataDefinitionServiceImpl
Invokes the execute method given in executor class;

GraphQL
ListDataDefinitionType
Model definition;
Represents the list operation returns;
Contains a list of DataDefinition as attribute representing the data engine structures saved in the database associated with the group ID and company ID passed in the request;
DEListDataDefinitionDataFetcher
Contains a get method called by graphQL API;
Builds a list request passing group ID and company ID as an argument;
Calls execute methods;
Returns a ListDataDefinitionType;
DEGraphQLServlet
Links GraphQL API with List API;
