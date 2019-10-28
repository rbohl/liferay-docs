---
header-id: implementing-methods-to-get-and-count-entities
---

# Implementing Methods to Get and Count Entities

[TOC levels=1-4]

Service Builder generates `findBy*` methods and `countBy*` methods in your
[`*Persistence` classes](/docs/7-2/appdev/-/knowledge_base/a/understanding-the-code-generated-by-service-builder)
based on your  `service.xml` file's
[finders](/docs/7-2/appdev/-/knowledge_base/a/defining-service-entity-finder-methods).
You can leverage finder methods in your local services to get and count
entities.

-   [Getters](#getter-methods): `get*` methods return entity instances matching 
    criteria.
-   [Counters](#counter-methods): `get*Count` methods return the number of 
    instances matching criteria

Start with getting entities that match criteria. 

## Getter Methods

The `findByPrimaryKey` methods and `findBy*` methods search for and return
entity instances based on criteria. Your local service implementation must only
wrap calls to the finder methods that get what you want.

Here's how to create a method that gets an entity based on an ID (primary key):

1.  Create a method using this format:

        public [ENTITY] get[ENTITY_NAME](long id) {
            return [ENTITY]Persistence.findByPrimaryKey(id);
        }

2.  Replace `[ENTITY]` and `[ENTITY_NAME]` with the respective entity type and
    entity name (or nickname).

3.  Run Service Builder to propagate the method to your local service 
    interface. 

Here's how to get entities based on criteria:

1.  Identify the criteria for finding the entity instance(s). 

2.  If there is no
    [`finder` element](/docs/7-2/appdev/-/knowledge_base/a/defining-service-entity-finder-methods)
    for the criteria, create one for it and run Service Builder. 

3.  Determine the
    [`*Persistence` class](/docs/7-2/appdev/-/knowledge_base/a/understanding-the-code-generated-by-service-builder)
    `findBy*` method you want to call.  Depending on your `finder` element
    columns, Service Builder might overload the method to include these
    parameters:
 
    -   `int start` and `int end` parameters for specifying a range of 
        entities.
    -   `com.liferay.portal.kernel.util.OrderByComparator orderByComparator` 
        parameter for arranging the matching entities. 

4.  Specify your `get*` method signature, making sure to account for the 
    `*Persistence` class `findBy*` method parameters you must satisfy. Use this 
    method format: 

        public List<[ENTITY]> get[DESCRIBE_THE_ENTITIES](...) {
            
        }

    Replace `[ENTITY]` with the entity type. Replace `[DESCRIBE_THE_ENTITIES]`
    with a descriptive name for the entities you're getting. 

6.  Call the `*Persistence` class `findBy*` method and return the list of 
    matching entities. 

7.  Run Service Builder. 

For example, `getGuestbookEntries` from `GuestbookEntryLocalServiceImpl`
returns a range of `GuestbookEntry`s associated with a `Group` primary key: 

```java
public List<GuestbookEntry> getGuestbookEntries(long groupId, long guestbookId) {
    return guestbookEntryPersistence.findByG_G(groupId, guestbookId);
}
```

Now you know how to leverage finder methods to get entities. Methods that count
entities are next.

## Counter Methods

Counting entities is just as easy as getting them. Your `*Persistence` class
`countBy*` methods do all the work. Service Builder generates `countBy*` methods
based on each finder and its columns. 

1.  Identify the criteria for entity instances you're counting and determine
    the
    [`*Persistence` class](/docs/7-2/appdev/-/knowledge_base/a/understanding-the-code-generated-by-service-builder)
    `countBy*` method that satisfies the criteria.

2.  Create a `get*Count` method signature following this format:

        public int get[DESCRIBE_THE_ENTITIES]Count(...) {
            
        }

    Replace `[DESCRIBE_THE_ENTITIES]` with a descriptive name for the entities
    you're counting.

3.  Call the `*Persistence` class' `countBy` method and return the value. For 
    example, the method `getEntriesCount` from
    `GuestbookEntryLocalServiceImpl`
    returns the number of `GuestbookEntry`s that are associated with a group
    (matching `groupId`) and a guestbook (matching `guestbookId`). 

```java
public int getGuestbookEntriesCount(long groupId, long guestbookId) {
    return guestbookEntryPersistence.countByG_G(groupId, guestbookId);
}
```

Now your local service can get entities matching your criteria and return quick
entity counts. 

## Related Topics

[Creating Local Services](/docs/7-2/appdev/-/knowledge_base/a/business-logic-with-service-builder)

[Implementing an Add Method](/docs/7-2/appdev/-/knowledge_base/a/implementing-an-add-method)

[Defining Service Entity Finder Methods](/docs/7-2/appdev/-/knowledge_base/a/defining-service-entity-finder-methods)

[Understanding the Code Generated by Service Builder](/docs/7-2/appdev/-/knowledge_base/a/understanding-the-code-generated-by-service-builder)