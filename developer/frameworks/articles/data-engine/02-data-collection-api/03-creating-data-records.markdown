# Creating Data Records

Data Records contain the data whose structure is defined by a DE Data Definition
and whose boundary is defined by a DE Data Record collection. Only a User with
[permission](LINK TO PERMISSIONS ARTICLE) to save records in the Data Record
collection can execute this operation.

First create a new `DEDataRecord`:

    DEDataRecord deDataRecord = new DEDataRecord();

Then set the record collection to which it belongs:

    deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

Put the values to save in a `HashMap` (`fieldName, value`), and then set the
values in the data record:

    Map<String, Object> values = new HashMap() {
        {
            put("name", "Liferay");
            put("email", "test@liferay.com");
        }
    };
        
    deDataRecord.setValues(values);

A valid Data Record must be associated with a `DEDataRecordCollection`, and it
must match the fields required by the backing data definition. Otherwise the
Data Record can't be saved. 

Speaking of saving the Data Record, that's the next step. Once you have the
`DEDataRecord` object created in memory, create a [save request](LINK TO THE
SAVE OPERATION SECTION) and execute it to persist the data

1.  Build the `DEDataRecordCollectionSaveRecordRequest`:

        DEDataRecordCollectionSaveRecordRequest
                    deDataRecordCollectionSaveRecordRequest =
                        DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
                            deDataRecord
                        ).inGroup(
                            group.getGroupId()
                        ).onBehalfOf(
                            user.getUserId()
                        ).build();


2.  Execute the request. If you need to retain the saved record in memory, store
    the executed request in a `DEDataRecordCollectionSaveRecordResponse`. This
    is useful if you need to do more processing on the Data Record after the
    save is executed. 

        DEDataRecordCollectionSaveRecordResponse
        deDataRecordCollectionSaveRecordResponse =
            deDataRecordCollectionService.execute(
                deDataRecordCollectionSaveRecordRequest);

Now you can create and save `DEDataRecord`s.
