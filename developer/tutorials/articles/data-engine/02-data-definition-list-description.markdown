# The Data Engine's API for Listing Data Definitions

The following outline describes the classes of the API for listing
`DEDataDefinition`s:

- List Request/Response (data-engine-api module)
    - `DEDataDefinitionListRequest`
        - Build the request
            - Requires the Group ID and Company ID (used for retrieval and for permission checking)
            - Start index and end index are optional parameters, if pagination is desired
    - `DEDataDefinitionListResponse`
        - Build the response;
        - Has a list property, that contains all the data definition registry saved;
- Service
    - `DEDataDefinitionService`
        - Receives a DEDataDefinitionListRequest and returns a DEDataDefinitionListResponse
