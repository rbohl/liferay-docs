# Data Definitions API
<!--Should maybe describe the details of what each operation is for in this
article instead of in the next one on building each request, which is getting
long-->

The Data Definitions API allows the following operations:

- Getting a List of Data Definitions
- Getting a Data Definition
- Counting Data Definitions
- Searching/Filtering Data Definitions
- Counting the Searched Data Definitions
- Saving Data Definitions
- Deleting Data Definitions
- Defining Permissions on Data Definitions

To call the `DEDataDefinition` operations in your own code, use these general
steps:

1.  Build a request for the supported operation. For example, `DEDataDefinitionListRequest`.
2.  Pass the request object to the `DEDataDefinitionService`'s overloaded `execute
    method`.
3.  Store the returned response in a variable of the appropriate response type.
    For example, `DEDataDefinitionListResponse`.

See a complete example demonstrating for the `DEDataDefinitionListRequest/Response` [here](LINK TO HOW-TO ARTICLE)

The following outline describes the general pattern used for the
`DEDataDefinitionService` operations.

- Operation Request/Response (data-engine-api module)
    - `DEDataDefinition[Operation]Request`
        - Build the request.
            - Include required parameters (for example, the Group ID and Company ID are required for the List operation, for retrieval and permission checking).
            - Include optional parameters, if desired (for the List operation, start index and end index parameters, if pagination is desired).
            - Pass the request object to the `DEDataDefinitionService`'s `execute` method.
    - `DEDataDefinition[Operation]Response`
        - Build the response.
        - Contains objects or properties resulting from execute methods.
- Service
    - `DEDataDefinitionService`
        - The overloaded `execute` method receives a `DEDataDefinition[Operation]Request` and returns a
            `DEDataDefinition[Operation]Response`.

For each operation you can invoke, it's important to understand the required
and the optional parameters.

