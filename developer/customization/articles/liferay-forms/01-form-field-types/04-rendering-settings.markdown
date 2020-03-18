---
header-id: rendering-form-field-settings
---

# Rendering Form Field Settings

[TOC levels=1-4]

Once the settings are added to the class backing the field's settings, update the front-end code.

## Preparing Settings to the Front-End

Send the new configuration settings to the Soy template so they can be displayed
to the end user. Create a new Java class implementing the interface
`DDMFormFieldTemplateContextContributor`.

The `DDMFormFieldTemplateContextContributor` interface has a single method
named `getParameters`. It gets the new configuration settings, specific for
a form field type, and sends for the resources that need them, like the Soy
template. To get these settings, create a new class,
`SliderDDMFormFieldTemplateContextContributor`. First create its OSGI component
annotation and the class declaration:

    @Component(
        immediate = true,
        property = "ddm.form.field.type.name=slider",
        service = {
            DDMFormFieldTemplateContextContributor.class,
            SliderDDMFormFieldTemplateContextContributor.class
        }
    )
    public class SliderDDMFormFieldTemplateContextContributor
        implements DDMFormFieldTemplateContextContributor {
    }

Then override `getParameters` to get the new configurations settings,
`min` and `max`:

    @Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("min", (String)ddmFormField.getProperty("min"));
        parameters.put("max", (String)ddmFormField.getProperty("max"));

		return parameters;
    }

Next configure the JavaScript component to include the new settings.

## Adding Settings to the JavaScript Component

The JavaScript component must know about the new settings. First configure them
as attributes of the component:

    Slider.STATE = {
        ...

        max: Config.number(),

        min: Config.number(),
    };

These attributes (`max` and `min`) define the bottom value and the top value of the slider range, so the `max` and `min` setting must receive a number.

Now, the field type JavaScript component is configured to include the settings.
All you have left to do is to update the Soy template so the `min` and `max` values can be applied to the Slider field.

## Updating the Soy Template

Add the `min` and `max` settings to your Soy template's logic.

The whole template is included below, but the only additions are in the list of
parameters, and then in the `<input>` tag, where you
use the parameters' values to configure the `min` and `max` HTML properties with the proper values.

    {namespace Slider}

    /**
    * Prints the Slider field.
    */
    {template .render}
        {@param label: string}
        {@param max: any}
        {@param min: any}
        {@param name: string}
        {@param showLabel: bool}
        {@param tip: string}
        {@param value: ?}
        {@param? _handleFieldChanged: any}
        {@param? predefinedValue: any}
        {@param? required: bool}
        {@param? spritemap: string}

        {call FieldBase.render}
            {param contentRenderer kind="html"}
                {call .content}
                    {param name: $name /}
                    {param max: $max /}
                    {param min: $min /}
                    {param value: $value /}
                    {param predefinedValue: $predefinedValue /}
                    {param _handleFieldChanged: $_handleFieldChanged /}
                {/call}
            {/param}
            {param label: $label /}
            {param name: $name /}
            {param required: $required /}
            {param showLabel: $showLabel /}
            {param spritemap: $spritemap /}
            {param tip: $tip /}
        {/call}
    {/template}

    {template .content}
        {@param name: string}
        {@param max: any}
        {@param min: any}
        {@param value: ?}
        {@param? predefinedValue: any}
        {@param? _handleFieldChanged: any}
        {let $attributes kind="attributes"}
            class="ddm-field-slider form-control slider"

            data-oninput="{$_handleFieldChanged}"

            id="myRange"

            max="{$max}"

            min="{$min}"

            name="{$name}"

            type="range"

            {if $value}
                value="{$value}"
            {else}
                value="{$predefinedValue}"
            {/if}
        {/let}

        <input {$attributes}>
    {/template}

The `min` and `max` set by the form builder is captured in the rendering of the Slider itself.

Now when you build the project and deploy your slider field, you have a fully
developed *slider* form field type, complete with the proper JavaScript behavior and with additional settings.

