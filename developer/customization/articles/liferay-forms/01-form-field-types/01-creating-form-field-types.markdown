---
header-id: creating-form-field-types
---

# Creating Form Field Types

[TOC levels=1-4]

Liferay's Forms application does not contain a dedicated slider field
out-of-the-box. For ease of use and to ensure proper slider data is collected,
you'll develop a slider field and learn how @product@'s field types work at the
same time.

There are several steps involved in creating a form field type:

1.  Creating the Form Field Type's Java class.

2.  Defining the field's behavior in JavaScript and Soy templates.

| **Blade Template:**  To jump-start your project, use
| [Blade CLI](/docs/7-1/tutorials/-/knowledge_base/t/blade-cli)
| or
| [Liferay Dev Studio](/docs/7-1/tutorials/-/knowledge_base/t/creating-modules-with-liferay-ide).
| There's a Blade template for creating form fields. Using the CLI, enter
|
|     blade create -t form-field -v 7.2 -p com.liferay.docs.formfieldtype -c Slider DDMTypeSlider
|
| This gives you a `DDMTypeSlider` module with a similar structure to what's above.
| The Java classes are in the package `com.liferay.docs.formfield` under
| `src/main/java/` and the frontend resources (JavaScript and Soy files) are in
| `src/main/resources/META-INF/resources`.
|
| A known limitation in the form-field template requires the use of camel case in
| the project name (`DDMTypeSlider`). Trying to use kebab case instead
| (`ddm-type-slider`) generates a non-functioning module. This is fixed with the
| release of Blade 3.3. Run `blade version` from the command line to see the
| version of Blade you're running.
|
| Using Blade CLI or Liferay Dev Studio, you get a project skeleton with much of
| the boilerplate filled in, so you can focus immediately on coding.

Start by setting up the project's metadata.

## Specifying OSGi Metadata

First specify the necessary OSGi metadata in a `bnd.bnd` file (see
[here](http://bnd.bndtools.org/chapters/800-headers.html)
for more information).  Here's what it would look like for a module in a folder
called `dynamic-data-mapping-type-slider`:

    Bundle-Name: Liferay Dynamic Data Mapping Type Slider
    Bundle-SymbolicName: com.liferay.dynamic.data.mapping.type.slider
    Bundle-Version: 1.0.0
    Web-ContextPath: /dynamic-data-mapping-type-slider

Path to the modules root folder, so your module's resources are made available
upon module activation.

Next craft the OSGi Component that marks your class as an implementation of
`DDMFormFieldType`.

## Creating a DDMFormFieldType Component

If you're creating a *Slider* field type, define the Component at the top of your
`*DDMFormFieldType` class like this:

    @Component(
      immediate = true,
      property = {
        "ddm.form.field.type.description=slider-field-type-description",
		"ddm.form.field.type.display.order:Integer=10",
		"ddm.form.field.type.group=basic",
        "ddm.form.field.type.icon=control-panel",
		"ddm.form.field.type.label=slider-field-type-label",
		"ddm.form.field.type.name=slider"
      },
      service = DDMFormFieldType.class
    )

Define the field type's properties (`property=...`) and declare that you're
implementing the `DDMFormFieldType` service (`service=...`).

`DDMFormFieldType` Components can have several properties:

`ddm.form.field.type.description`
: An optional property describing the field type. Its localized value appears in
the form builder's sidebar, just below the field's label.

`ddm.form.field.type.display.order`
: An Integer defining the field type's position in the sidebar.

`ddm.form.field.type.icon`
: The icon for the field type. Choosing one of the
[Lexicon icons](https://lexicondesign.io/docs/patterns/icons.html)
makes your form field blend in with the existing form field types.

`ddm.form.field.type.label`
: The field type's label. Its localized value appears in the form builder's
sidebar.

`ddm.form.field.type.name`
: The field type's name must be unique. Each Component in a field type module
references the field type name, and it's used by OSGi service trackers to filter
the field's capabilities (for example, rendering and validation).

Next code the `*DDMFormFieldType` class.

## Implementing DDMFormFieldType

Implementing the field type in Java is made easier because of
`BaseDDMFormFieldType`, an abstract class you can leverage in your code.

After extending `BaseDDMFormFieldType`, override the `getName` method by
specifying the name of your new field type:

    public class SliderDDMFormFieldType extends BaseDDMFormFieldType {

        @Override
        public String getName() {
            return "slider";
        }

    }

That's all there is to defining the field type. Next determine how your field
type is rendered.
