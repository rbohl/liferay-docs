---
header-id: rendering-form-field-settings
---

# Rendering Form Field Settings

[TOC levels=1-4]

Once the settings are added to the class backing the field's settings,update the front-end code.

## Passing Settings to the Renderer Class

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

		parameters.put(
            "min", ddmFormField.getProperty("min"));
        parameters.put("max", ddmFormField.getProperty("max"));

		return parameters;
    }

Next configure the JavaScript component to include the new settings.

## Adding Settings to the JavaScript Component

The JavaScript component must know about the new settings. First configure them
as attributes of the component:

    Slider.STATE = {

        max: Config.oneOfType([Config.number(), Config.string()]),

        min: Config.oneOfType([Config.number(), Config.string()]),

    }

These attributes (max and min) define the the bottom value and the top value of the slider range, so the max and min setting must receive a number.

Now the field type JavaScript component is configured to include the settings.
All you have left to do is to update the Soy template so the min and max value can be applied to the Slider field.

## Updating the Soy Template

Add the placeholder setting to your Soy template's logic.

The whole template is included below, but the only additions are in the list of
parameters, and then in the `<input>` tag, where you
use the parameter min and max to configure the min and max HTML property with the proper value.

    {namespace Slider}

    /**
    * Prints the Slider field.
    */
    {template .render}
        {@param? _handleFieldChanged: any}
        {@param label: string}
        {@param max: any}
        {@param min: any}
        {@param name: string}
        {@param showLabel: bool}
        {@param? predefinedValue: any}
        {@param? required: bool}
        {@param? spritemap: string}
        {@param tip: string}
        {@param value: ?}

        {call FieldBase.render}
            {param contentRenderer kind="html"}
                {call .content}
                    {param _handleFieldChanged: $_handleFieldChanged /}
                    {param max: $max /}
                    {param min: $min /}
                    {param name: $name /}
                    {param predefinedValue: $predefinedValue /}
                    {param value: $value /}
                {/call}
            {/param}
            {param label: $label /}
            {param name: $name /}
            {param tip: $tip /}
            {param required: $required /}
            {param showLabel: $showLabel /}
            {param spritemap: $spritemap /}
        {/call}
    {/template}

    {template .content}
        {@param? _handleFieldChanged: any}
        {@param max: any}
        {@param min: any}
        {@param name: string}
        {@param? predefinedValue: any}
        {@param value: ?}
        {let $attributes kind="attributes"}
            class="ddm-field-slider form-control slider"

            data-oninput="{$_handleFieldChanged}"

            id="myRange"

            min="{$min}"

            max="{$max}"

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

The min and max set by the form builder is captured in the rendering of the Slider itself.

Now when you build the project and deploy your time field, you have a fully
developed *slider* form field type, complete with the proper JavaScript behavior and with additional settings.

