# Data Collections API
<!--Should maybe describe the details of what each operation is for in this
article instead of in the next one on building each request, which is getting
long-->

Data Record Collections (`DEDataRecordCollection`s) are used to collect
`DEDataRecord`s. A Data Definition defines the fields present in the Data
Collection, while the Data Record Collection forms a boundary, collecting only
records associated with its Primary Key. The `DEDataRecordCollection` has a
unique ID, a name, and a description. Each individual Data Record Collection is
associated with a single Data Definition's Primary Key. 

It's beneficial to add this extra layer between the records and the backing data
definition because it lets you reuse the data definition.

The Data Collections API allows the following operations:

- Getting a List of Data Collections
- Getting a List of Records in a Data Collection
- Getting a Data Collection
- Getting a Record in a Data Collection
- Searching/Filtering Data Collections
- Searching for Records in a single Data Collection
- Sorting the List of Data Collections
- Add a Record to a Data Collection
- Edit a Record in a Data Collection
- Saving Data Collections
- Deleting Data Collections
- Deleting a Record from a Data Collection
- Defining Permissions on Data Collections

To call the `DEDataRecordCollection` operations in your own code, use these
general steps:

1.  Build a request for the supported operation. For example,
    `DEDataRecordCollectionSaveModelPermissionsRequest`.
2.  Pass the request object to the `DEDataRecordCollectionService`'s overloaded
    `execute` method.
3.  Store the returned response in a variable of the appropriate response type.
    For example, `DEDataRecordCollectionSaveModelPermissionsResponse`.

See a complete example demonstrating for the
`DEDataRecordCollectionSaveModelPermissionsRequest/Response` 
[here](LINK TO HOW-TO ARTICLE)

The following outline describes the general pattern used for the
`DEDataRecordCollectionService` operations.

- Operation Request/Response (`data-engine-api` module)
    - `DEDataRecordCollection[Operation]Request`
        - Build the request.
            - Include required parameters (for example, the Group ID and Company
                ID are required for the List operation, for retrieval and
                permission checking).
            - Include optional parameters, if desired (for the List operation,
                start index and end index parameters, if pagination is desired).
            - Pass the request object to the `DEDataRecordCollectionService`'s
                `execute` method.
    - `DEDataRecordCollection[Operation]Response`
        - Build the response.
        - Contains objects or properties resulting from execute methods.
- Service
    - `DEDataRecordCollectionService`
        - The overloaded `execute` method receives a
            `DEDataRecordCollection[Operation]Request` and returns a
            `DEDataRecordCollection[Operation]Response`.

For each operation you can invoke by building a request, it's important to
understand the required and the optional parameters. The next article discusses
just that.

