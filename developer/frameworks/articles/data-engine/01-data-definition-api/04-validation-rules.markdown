# Validation Rules

A Data Definition can contain validation rules to make sure proper data is
entered in its fields. These rules are found in the `data-engine-service`
module, and they implement the `DataDefinitionRuleFunction` class. Keys for
referring to these rules are found in the `DEDataDefinitionRuleConstants` class
(of the `data-engine-api` module). They're include below, with the function they
represent, in the format `DERuleFunction` (`RULE_KEY`): 

`DEDecimalLiteralRuleFunction`s (`DECIMAL_LITERAL_RULE`) enforce that the field
is a valid decimal. To refer to these rules in your code, use the 

`DEEmailAddressRuleFunction`s (`EMAIL_ADDRESS_RULE`) enforce that the field is a
valid email address.

`DEEmptyRuleFunction`s (`EMPTY_RULE` or `REQUIRED_RULE`) make the field required by enforcing that
it's not empty.
<!-- Which Key is correct?-->

`DEIntegerLiteralRuleFunction`s (`INTEGER_LITERAL_RULE`) enforce that the field
is a valid integer.

`DEMatchExpressionRuleFunction`s (`MATCH_EXPRESSION_RULE`) enforce that the
field matches an expression rule passed as a parameter to the
`DataDefinitionRule`.

`DEURLRuleFunction`s (`URL_RULE`) enforce that the field is a valid URL.

Each rule is specified as a parameter of the Data Definition. The same rule can
be applied to multiple fields in the Data Definition. Inside a Data Definition,
there's a list of Data Definition rules.

## Creating a `DEDataDefinitionRule`

A `DEDataDefinitionRule` is build by passing in the names of the fields to apply
the rule to, the rule name (using the constant that represents its String
literal), the rule type (currently, only `VALIDATION_RULE_TYPE` is supported),
and any parameters required for the rule you're using.

### Step 1: Create the Data Definition Rule

    DEDataDefinitionRule deDataDefinitionRule = new
        DEDataDefinitionRule();


### Step 2: Set the Rule Name and Type

    deDataDefinitionRule.setName(
            DEDataDefinitionRuleConstants.EMAIL_ADDRESS_RULE);

    deDataDefinitionRule.setRuleType(
        DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE);

### Step 3: Set the Fields to Validate

    deDataDefinitionRule.getDEDataDefinitionFieldNames();
    
    deDataDefinitionRule.add("email");

### Step 4: Add the Rule to the Data Definition

    DEDataDefinition deDataDefinition = new
        DEDataDefinitionField("email", "string");

    deDataDefinition.setDEDataDefinitionRules(
        Arrays.asList(deDataDefinitionRule));

<!-- The Google doc draft has test code, if needed: https://docs.google.com/document/d/1cH0XP4R7TBAj9MoDlrZHbui0zMGyW53SZO_nozYXBEk/edit#-->
