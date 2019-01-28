# Data Definitions API

The Data Definitions API allows the following operations:

Getting a List of Data Definitions
Getting a Data Definition
Counting Data Definitions
Searching/Filtering Data Definitions
Counting the Searched Data Definitions
Saving Data Definitions
Deleting Data Definitions
<!--Defining Permissions on Data Definitions-->

Each of these operations is performed on the `DEDataDefinition` entity. It's
important to note that while you're interacting with the `DEDataDefinition`
API, in the backend this framework calls the `DDMStructureService`. For
example, when you send a `DEDataDefinitionCountRequest`, the request is
processed by `DEDataDefinitionCountRequestExecutor`, which makes a call to
`ddmStructureService.getStructuresCount()`.

To call the `DEDataDefinition` operations in your own code, use these general
steps:

1.  Build a request for the supported operation. For example, `DEDataDefinitionListRequest`.
2.  Pass the request object to the `DEDataDefinitionService`'s overloaded `execute
    method`.
3.  Store the returned response in a variable of the appropriate response type.
    For example, `DEDataDefinitionListResponse`.

The following outline describes the general pattern used for the
`DEDataDefinitionService` operations.

- List Request/Response (data-engine-api module)
    - `DEDataDefinition[Operation]Request`
        - Build the request.
            - Include required parameters (for example, the Group ID and Company ID are required for the List operation, for retrieval and permission checking).
            - Include optional parameters, if desired (for the List operation, start index and end index parameters, if pagination is desired).
    - `DEDataDefinition[Operation]Response`
        - Build the response.
        - Pass the request object to the `DEDataDefinitionService`'s `execute` method.
- Service
    - `DEDataDefinitionService`
        - The overloaded `execute` method receives a `DEDataDefinition[Operation]Request` and returns a
            `DEDataDefinition[Operation]Response`.

For each operation you can invoke, it's important to understand the required and
the optional parameters.

