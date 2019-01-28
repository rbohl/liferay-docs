# Listing Data Definitions

To demonstrate the use of the Data Definitions API, consider the operation for
getting a `List` of `DEDataDefinition` objects.

The API for listing Data Definitions does not support filtering (use the search
operation for that). To get an unfiltered list of all the `DEDataDefinition`s
in the database, first build a `DEDataDefionitionRequest` that includes the
`CompanyId` and `GroupId` (pagination start and end parameters are optional),
and then pass the request to the `DEDataDefinitionService`'s `execute` method.

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
    `DEDatadefinitionService`' `execute` method:

        DEDataDefinitionListResponse deDataDefinitionListResponse =
                    _deDataDefinitionService.execute(deDataDefinitionListRequest);

    The service's `execute` method is overloaded. Passing it a
    `DEDataDefinitionListRequest` is enough to ensure that a
    `DEDataDefinitionListResponse` is returned. 

The response you receive is a `List` of all the `DEDataDefinitions` that match
the request parameters.
