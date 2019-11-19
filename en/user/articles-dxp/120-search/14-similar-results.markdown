# Similar Results

DON'T FORGET TO UPDATE THE CONFIGURATION ARTICLE AND THE SOLR LIST

There are numerous ways to tune the search experience, and Similar Results is
one of them. Add a widget to a page and display results that are similar to the
"main asset" [EXPLAIN] that's selected on the page.

## Configuring the Similar Results Widget

1.  Go to the Add menu (![Add](../../images/icon-add-widget.png)) &rarr; Widgets
    &rarr; Search section, and drag the Similar Results widget onto the page.

2.  To configure it, open the widget Options menu
    (![Options](../../images/icon-app-options.png)) and click _Configuration_.

The full list of available properties is found
[below](#similar-results-configurations).

## Using the Similar Results Widget

What happens when a Similar Results widget is placed on a page depends on the
context and the assets currently being displayed by the page. If there is no
main asset selected on the page or the backend search infrastructure can't find
its indexed document [EXPLAIN WHEN THIS MIGHT OCCUR: search engine not
connected?], the Similar Results widget won't display anything to the end user;
its space on the page remains blank. Site administrators see the following
informational message: 

_There are no similar results available._

When a main asset's document is detected, the widget shows similar results of
the same asset type, displayed in a format dictated by the configure [Widget
Template](#similar-results-widget-templates).

Learn more by considering two use cases.

## Use Case 1: Similar Results on Asset Publisher Pages

    Create a widget page with an Asset Publisher widget and a Similar Results widget.
    Go to Asset Publisher's  Configuration and set Display Settings > Asset Link Behavior to Show Full Content. (This will display the content of the asset inside the Asset Publisher.)
    Create multiple similar assets of the types listed below. (similar enough that the Similar Results widget would populate results)
        Blog Entries
        Documents and Media files
        Documents and Media folders
        Web Content
        Wiki Pages
        Message Boards threads
        Message Boards messages
        Message Boards categories
    When clicking on an asset within the Asset Publisher widget, users should see results appear in the Similar Results widget.
    When clicking on an asset within the Similar Results widget, users should see that asset rendered on the Asset Publisher widget on the current page.

## Use Case 2: Similar Results on Asset-specific Display Pages

    Create a widget page with a Blogs widget and a Similar Results widget.
    Create multiple similar blogs. (similar enough that the Similar Results widget would populate results)
    When clicking on a blog within the Blogs widget, users should see results appear in the Similar Results widget.
    When clicking on a blog within the Similar Results widget, users should see that blog rendered on the Blogs widget on the current page.
    Repeat the above substituting the following widgets and assets
        Widget: Blogs, Asset: Blog Entry
        Widget: Documents and Media, Assets: File, Folder
        Widget: Web Content Display, Asset: Web Content (decided not to implement due to reason stated in note below)
            Note: This may not be possible via URL detection because the widget does not provide context of the selected asset in the URL.
        Widget: Wiki, Asset: Wiki Page
        Widget: Wiki Display, Asset: Wiki Page (decided not to implement due to reason stated in note below)
            Note: This may not be possible via URL detection because the widget does not provide context of the selected asset in the URL.
        Widget: Message Boards, Asset: Threads, Messages, Categories

Configuration Options

    General configuration section showing:
        The maximum number of items to display via an input field.
        Display Template drop down list with the following options:
            Three default display templates: Title, Detail, Card. (Figma)
            All Widget Templates created by administrator
        Link to "Manage Templates" which allows administrators to CRUD Widget Templates
    Advanced configuration section showing Elasticsearch's More Like This query parameters

## Similar Results Widget Templates

## Similar Results Configurations

The first configuration options are contained ina  section called _Display
Settings_.

**Display Template**
: 

**Minimum Item Display**
: 

The Advanced Configuration section contains configurations to tweak the behavior
of the widget.

**Fields**
: 

**Maximum Query Terms**
:

**Minimum Term Frequency**
:

**Minimum Document Frequency**
:

**Maximum Document Frequency**
:

**Minimum Word Length**
:

**Maximum Word Length**
:

**Stop Words**
:

**Analyzer**
:

**Minimum Should Match**
:

**Term Boost**
:

**Federated Search Key** 
:


![Figure x: Configure the Similar Results widget's display settings.](../../images/search-similar-results-display-settings.png)


![Figure x: Configure the Similar Results widget's more advanced settings to determine the behavior the end user experiences.](../../images/search-similar-results-advanced-settings.png)
