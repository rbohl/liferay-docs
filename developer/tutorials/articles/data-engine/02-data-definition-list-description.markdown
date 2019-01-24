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
- List Executor
    - `DEDataDefinitionListRequestExecutor`
        - Invokes the list operation of the `DDMStructure` API;
        - According to the Group ID and Company ID in the list request, the `execute` method returns a response with a list of `DEDataDefinitions`. 
        - The caller can specify indexes for pagination; 
        - Two auxiliary methods are implemented to parse ddmStructures to DEDataDefinition objects;
            - `map` maps `DDMStructure`s and `DEDataDefinition`s
            - `deserialize` Deserialize the `DDMStructure` fields (from JSON to Java objects)
- Service implementation
    - `DEDataDefinitionRequestBuilder`
        - Calls operations builder methods
    - `DEDataDefinitionServiceImpl`
        - Invokes the execute method given in the executor
