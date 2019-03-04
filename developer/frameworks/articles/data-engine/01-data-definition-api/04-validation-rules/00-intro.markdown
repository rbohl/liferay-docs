# Validation Rules

A Data Definition can contain validation rules to make sure proper data is
entered in its fields. These rules are found in the `data-engine-service`
module, and they implement the `DataDefinitionRuleFunction` class. Keys for
referring to these rules are found in the They include: 

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

## Creating of a `DEDataDefinitionRule`

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






	public static final String DECIMAL_LITERAL_RULE = "decimalLiteral";

	public static final String EMAIL_ADDRESS_RULE = "emailAddress";

	public static final String EMPTY_RULE = "empty";

	public static final String EXPRESSION_PARAMETER = "expression";

	public static final String INTEGER_LITERAL_RULE = "integerLiteral";

	public static final String INVALID_EMAIL_ADDRESS_ERROR =
		"invalid-email-address";

	public static final String INVALID_URL_ERROR = "invalid-url";

	public static final String MATCH_EXPRESSION_RULE = "matchExpression";

	public static final String REQUIRED_RULE = "required";

	public static final String URL_RULE = "url";

	public static final String VALIDATION_RULE_TYPE = "validation";

	public static final String VALUE_MUST_BE_DECIMAL_ERROR =
		"value-must-be-a-decimal-value";

	public static final String VALUE_MUST_BE_INTEGER_ERROR =
		"value-must-be-an-integer-value";

	public static final String VALUE_MUST_MATCH_EXPRESSION_ERROR =
		"value-must-match-expression";

	public static final String VALUE_MUST_NOT_BE_EMPTY_ERROR =
		"value-must-not-be-empty";
