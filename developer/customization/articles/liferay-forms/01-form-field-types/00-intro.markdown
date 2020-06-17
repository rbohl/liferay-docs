---
header-id: form-field-types
---

# Form Field Types

[TOC levels=1-4]

The Forms application contains many highly configurable field types
out-of-the-box. Most use cases are met with one of the existing field types. 

![Figure 1: The Forms application has useful out-of-the-box field types, but you can add your own if you need to.](../../../images/forms-field-types.png)

If you're reading this, however, your use case probably wasn't met with the
default field types. For example, perhaps you need a color picker field. You
could create a select field that lists color names. Some users don't, however,
know that *Gamboge* is the color of spicy mustard (maybe a little darker), and
anyway, seeing colors is better than reading their names, so you can create
a field that shows colors. Or, for instance, maybe you need to add a Slider (https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/range) to measure the level of satisfaction of a product.

These tutorials show you how to

- create a module that adds a *Slider* form field type

- add custom configuration options to your field types

| **Example project:** The source code for the example *slider* project developed in
| these tutorials can be downloaded for your convenience. Click
| [here](https://portal.liferay.dev/documents/113763090/114000653/dynamic-data-mapping-type-slider.zip) to begin downloading the source code zip file.


Before getting started, learn the structure of a form field type.

## Anatomy of a Field Type Module

All form field type modules have a similar structure. Here's the directory
structure of the `dynamic-data-mapping-type-slider` module developed in these
tutorials:

    .babelrc
    .npmbundlerrc
    bnd.bnd
    build.gradle
    package-lock.json
    package.json
    src
    └── main
        ├── java
        │   └── com
        │       └── liferay
        │           └── dynamic
        │               └── data
        │                   └── mapping
        │                       └── type
        │                           └── slider
        │                               ├── SliderDDMFormFieldTemplateContextContributor.java
        │                               ├── SliderDDMFormFieldType.java
        │                               └── SliderDDMFormFieldTypeSettings.java
        └── resources
            ├── content
            │   └── Language.properties
            └── META-INF
                └── resources
                    ├── Slider
                        ├── Slider.es.js
                        ├── Slider.soy
                        └── SliderRegister.soy

You don't need `*TemplateContextContributor.java` or `*TypeSettings.java` in the
initial module (see [Rendering Form Field Settings](/docs/7-1/tutorials/-/knowledge_base/t/rendering-form-field-settings) 
to learn more about these classes). The initial module consists of these Java
classes and resources:

`*DDMFormFieldType.java`: Defines the form field type in the back-end. If you
extend the abstract class that implements the interface, you automatically
include the default form configuration options for your form field type. In
that case, override the interface's `getName` method and you're done. To see
the default configuration options your form field type inherits, look at
the `DefaultDDMFormFieldTypeSettings` class in the `dynamic-data-mapping-api`
module.

`[name-of-field-type].es.js`: The JavaScript file that configures the template
rendering (the `[name-of-field-type].soy` rendering).

`[name-of-field-type].soy`: The template that defines the appearance of the field.

`[name-of-field-type]Register.soy`: The template that calls the template of field soy.

`Language_xx_XX.properties`: Define any terms that must be 
[translated into different languages](/docs/7-1/tutorials/-/knowledge_base/t/localizing-your-application).

In addition to the Java classes, Soy templates, and JavaScript files, a form
field type contains the following files:

`.babelrc`: The [Babel](https://babeljs.io/) configuration file.

`.npmbundlerrc`: The
[liferay-npm-bundler](/docs/7-1/reference/-/knowledge_base/r/liferay-npm-bundler) 
configuration file.

`bnd.bnd`: The module's metadata.

`build.gradle`: The module's dependencies and build properties.

`package.json`: The npm module manager.

`package-lock.json`: Automatically generated to track the npm modules dependencies.

Get started creating the time field in the next tutorial.
