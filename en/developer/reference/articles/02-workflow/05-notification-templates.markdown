# Workflow Notification Templates

There are a number of variables auto-injected into the workflow notification
context. These are handy to use in your workflow notification templates.

Normally in Freemarker, you must declare variables explicitly:

```markup
<#assign variableName = "Variable Name" />
```

The injected variables let you skip this declaration and move directly to using
the variable in the template.

```markup
${variableName}
```

To use these variables, you must first ascertain the list of variables
available in your workflow notification context. The precise variables
available change depending on the workflow definition details and the asset
being operated on in the workflow. The method presented here shows you how to
get the list of variables for your specific context, so you don't have to do
any guesswork.

## Discovering the Workflow Notification Template Variables

Definitively discover the variables available in your workflow notification
context by enabling DEBUG level logging for the
`TemplateNotificationMessageGenerator` class:

1. Go to Control Panel &rarr; Configuration &rarr; Server Administration.
 
1. Click the Log Levels tab.
 
1. Add a Log Level with this configuration:

   - *Logger Name*: `com.liferay.portal.workflow.kaleo.runtime.internal.notification.TemplateNotificationMessageGenerator`
   - *Log Level*: `DEBUG`

1. [Activate a workflow
   definition](https://help.liferay.com/hc/en-us/articles/360029042051-Activating-Workflow#workflow-assets)
   (e.g., the Single Approver definition) on an asset (e.g., Blogs Entry).

1. Submit a test entry, and you'll see log messages appear that reveal what
   workflow notification template variables are available in the notification
   context of the workflow you're interested in.

When you do this for the Single Approver on initial submission of an asset,
Liferay DXP prints log messages exposing the list of available variables:

```bash
2020-03-30 14:21:42.089 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] saxReaderUtil (class com.sun.proxy.$Proxy447)
2020-03-30 14:21:42.097 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] getterUtil (class com.liferay.portal.kernel.util.GetterUtil_IW)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portalPermission (class com.liferay.portal.service.permission.PortalPermissionImpl)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] entryClassPK (class java.lang.String)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] layoutPermission (class com.liferay.portal.service.permission.LayoutPermissionImpl)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] expandoTableLocalService (class com.sun.proxy.$Proxy43)
2020-03-30 14:21:42.098 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] localeUtil (class com.liferay.portal.kernel.util.LocaleUtil)
2020-03-30 14:21:42.099 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] groupId (class java.lang.String)
2020-03-30 14:21:42.099 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portalUtil (class com.liferay.portal.util.PortalImpl)
...
```
The snipped output above shows that `saxReaderUtil`, `getterUtil`,
`portalPermission`, `entryClassPK`, `layoutPermission`,
`expandoTableLocalService`, `localeUtil`, `groupId`, and `portalUtil` are
available to the context that caused these messages to be printed in the log.

### Using the Workflow Notification Variables

It's nice to have a list of variables, but how do you use them? They can be
categorized into two main types:

1. **Value** variables provide a single value. If a variable provides
   a single value, it's mainly useful for displaying that value in the
   notification, or passing as a parameter to an operation that retrieves some
   other useful information (that will be displayed in the notification message).

   _Example:_ The Single Approver definition provides this notification in a
   FreeMarker template:

   ```markup
   ${userName} sent you a ${entryType} for review in the workflow.
   ```

   The `userName` and `entryType` provide values, so the notification might
   render like this:

   _Joe Bloggs sent you a Blogs Entry for review in the workflow._


   The value type variables can also be checked for content. The Single
   Approver definition also contains this notification template:

   ```markup
   Your submission was reviewed<#if taskComments?has_content> and the reviewer applied the following ${taskComments}</#if>.
   ```

   If the reviewer provided task comments, they're shown. Otherwise, the notification recipient only sees

   _Your submission was reviewed_

2. **Operation** variables expose a Liferay DXP Java class, so you can access
   its operations in the notification template. For these variables, you must
   familiarize yourself with the [Javadoc]() of the class or look into the source code
   to understand operations you have access to.

   _Example:_ This FreeMarker gets you the current date, using the default
   locale, in a specific pattern (_Month/Day/Year, Hour:Minute_): 

   ```markup
   ${dateUtil.getCurrentDate("MM/dd/yyyy, HH:mm",  localeUtil.getDefault())}`
   ```

| Variable Name                | Value    | Operation | Description or Link                       |
| ---------------------------- | -------- | --------- |--------------------------------------------- |
| ${saxReaderUtil}             |          | &#10004;  | [SaxReaderUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/xml/SAXReaderUtil.html) |
| ${getterUtil}                |          | &#10004;  | [GetterUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/GetterUtil.html) |
| ${portalPermission}          |          | &#10004;  | [PortalPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PortalPermission.html) |
| ${entryClassPK}              | &#10004; |           | The primary key of the Entry Class           |
| ${layoutPermission}          |          | &#10004;  | [LayoutPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/LayoutPermission.html) |
| ${expandoTableLocalService}  |          | &#10004;  | [ExpandoTableLocalService](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoTableLocalService.html) |
| ${localeUtil}                |          | &#10004;  | [LocaleUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/LocaleUtil.html) |
| ${groupId}                   | &#10004; |           | The ID of the group                          |
| ${portalUtil}                |          | &#10004;  | [PortalUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PortalUtil.html) |
| ${validator}                 |          | &#10004;  | [Validator](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/Validator.html) |
| ${dateUtil}                  |          | &#10004;  | [DateUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/DateUtil.html) |
| ${serviceLocator}            |          | &#10004;  | [ServiceLocator](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-impl/com/liferay/portal/template/ServiceLocator.html) |
| ${serviceContext}            |          | &#10004;  | [ServiceContext](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/ServiceContext.html) |
| ${jsonFactoryUtil}           |          | &#10004;  | [JSONFactoryUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/json/JSONFactoryUtil.html) |
| ${stringUtil}                |          | &#10004;  | [StringUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/StringUtil.html) |
| ${freeMarkerPortletPreferences} |       | &#10004;  | [TemplatePortletPreferences](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-impl/com/liferay/portal/template/TemplatePortletPreferences.html) |
| ${dateFormats}              | &#10004;  |           | DESCRIBE |
| ${dateFormatFactory}         |          | &#10004;  | [DateFormatFactory](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/DateFormatFactory.html) |
| ${userPortraitURL}           | &#10004; |           | DESCRIBE |
| ${portal}                    |          | &#10004;  | [Portal](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/Portal.html) |
| ${commonPermission}          |          | &#10004;  | [CommonPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/CommonPermission.html) |
| ${userURL}                   | &#10004; |           | DESCRIBE |
| ${expandoValueLocalService}  |          | &#10004;  | [ExpandoValueLocalService](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoValueLocalService.html) |
| ${entryType}                 | &#10004; |           | The type of entry in the workflow (e.g., Blogs Entry) |
| ${organizationPermission}    |          | &#10004;  | [OrganizationPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/OrganizationPermission.html) |
| ${passwordPolicyPermission}  |          | &#10004;  | [PasswordPolicyPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PasswordPolicyPermission.html) |
| ${expandoColumnLocalService} |          | &#10004;  | [ExpandoColumnLocalService](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoColumnLocalService.html) |
| ${taskComments}              | &#10004; |           | The comments, if any, left by the reviewer |
| ${staticFieldGetter}         |          | &#10004;  | [StaticFieldGetter](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/StaticFieldGetter.html) |
| ${htmlUtil}                  |          | &#10004;  | [HtmlUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/HtmlUtil.html) |
| ${languageUtil}              |          | &#10004;  | [LanguageUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/language/LanguageUtil.html) |
| ${enumUtil}                  | &#10004; |           | DESCRIBE |
| ${windowStateFactory}        |          | &#10004;  | [WindowStateFactory](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/WindowStateFactory.html) |
| ${companyId}                 | &#10004; |           | The ID of the portal's Company/Virtual Instance |
| ${unicodeFormatter}          |          | &#10004;  | [UnicodeFormatter](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/UnicodeFormatter.html) |
| ${propsUtil}                 |          | &#10004;  | [PropsUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PropsUtil.html) |
| ${browserSniffer}            |          | &#10004;  | [BrowserSniffer](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/servlet/BrowserSniffer.html) |
| ${portletProviderAction}     | &#10004; |           | Returns the outcome of the [PortletProvider#Action](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletProvider.Action.html) call |
| ${taskName}                  | &#10004; |           | The name of the task the notification is in |
| ${httpUtil}                  |          | &#10004;  | [HttpUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/HttpUtil.html) |
| ${portletURLFactory}         |          | &#10004;  | [PortletURLFactory](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletURLFactory.html) |
| ${imageToken}                | &#10004; |           | DESCRIBE |
| ${groupPermission}           |          | &#10004;  | [GroupPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/GroupPermission.html) |
| ${timeZoneUtil}              |          | &#10004;  | [TimeZoneUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/TimeZoneUtil.html) |
| ${unicodeLanguageUtil}       |          | &#10004;  | [UnicodeLanguageUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/language/UnicodeLanguageUtil.html) |
| ${expandoRowLocalService}    |          | &#10004;  | [ExpandoRowLocalService](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/expando/kernel/service/ExpandoRowLocalService.html) |
| ${auditRouterUtil}           |          | &#10004;  | [AuditRouterUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/audit/AuditRouterUtil.html) |
| ${kaleoTaskInstanceToken}    |          | &#10004;  | [See the documentation on workflow scripting](/docs/7-2/user/-/knowledge_base/u/leveraging-the-script-engine-in-workflow) |
| ${accountPermission}         |          | &#10004;  | [AccountPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/AccountPermission.html) |
| ${httpUtilUnsafe}            |          | &#10004;  | NEED HELP, does this access restricted variables somehow? [HttpUtilUnsafe]() |
| ${workflowTaskAssignees}     | &#10004; |          | [See the documentation on workflow scripting](/docs/7-2/user/-/knowledge_base/u/leveraging-the-script-engine-in-workflow) |
| ${random}                   |           |           | I DONT KNOW  |
| ${rolePermission}           |           | &#10004;  | [RolePermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/RolePermission.html) |
| ${portletPermission}        |           | &#10004;  | [PortletPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/PortletPermission.html) |
| ${paramUtil}                |           | &#10004;  | [ParamUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/ParamUtil.html) |
| ${locationPermission}       | &#10004;  |           | Returns the outcome of `OrganizationPermissionUtil.getOrganizationPermission()` |
| ${calendarFactory}          |           | &#10004;  | [CalendarFactory](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/CalendarFactory.html) |
| ${webServerToken}           | &#10004;  |           | DESCRIBE |
| ${sessionClicks}            |           | &#10004;  | [SessionClicks](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/SessionClicks.html) |
| ${userPermission}           |           | &#10004;  | [UserPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/UserPermission.html) |
| ${entryClassName}           | &#10004;  |
| ${userGroupPermission}      |           | &#10004;  | [UserGroupPermission](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/service/permission/UserGroupPermission.html) |
| ${arrayUtil}                |           | &#10004;  | [ArrayUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/ArrayUtil.html) |
| ${userName}                 | &#10004;  |           | The User Name of the last user to intervene in the workflow |
| ${userId}                   | &#10004;  |           | The User ID of the last user to intervene in the workflow. [See the Workflow Scripting article to understand the logic.](/docs/7-2/user/-/knowledge_base/u/leveraging-the-script-engine-in-workflow)
| ${prefsPropsUtil}           |           | &#10004;  | [PrefsPropsUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/PrefsPropsUtil.html) |
| ${url}                      | &#10004;  |           | DESCRIBE
| ${kaleoInstanceToken}       | &#10004;  |           | [See the Workflow Scripting article.](/docs/7-2/user/-/knowledge_base/u/leveraging-the-script-engine-in-workflow#variables-that-are-always-available)
| ${utilLocator}              |           | &#10004;  | [UtilLocator](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-impl/com/liferay/portal/template/UtilLocator.html) |
| ${objectUtil}               |           | &#10004;  | Returns a `new LiferayObjectConstructor()` |
| ${urlCodec}                 |           | &#10004;  | [URLCodec](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/util/URLCodec.html) |
| ${portletModeFactory}       |           | &#10004;  | [PortletModeFactory](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/portlet/PortletModeFactory.html) |
| ${imageToolUtil}            |           | &#10004;  | [ImageToolUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/image/ImageToolUtil.html) |
| ${auditMessageFactoryUtil}  |           | &#10004;  | [AuditMessageFactoryUtil](https://docs.liferay.com/dxp/portal/7.2-latest/javadocs/portal-kernel/com/liferay/portal/kernel/audit/AuditMessageFactoryUtil.html) |
| ${staticUtil}               |           | &#10004;  | Returns the result of `beansWrapper.getStaticModels()` |



<!-- 
The ones listed in the LPS-102221 comment:


workflowTaskAssignees
random
rolePermission
portletPermission
paramUtil
locationPermission
calendarFactory
webServerToken
sessionClicks
userPermission
entryClassName
userGroupPermission
arrayUtil
userName
userId
prefsPropsUtil
url
kaleoInstanceToken
-->


<!-- Remaining log output 
2020-03-30 14:21:42.105 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] validator (class com.liferay.portal.kernel.util.Validator_IW)
2020-03-30 14:21:42.106 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] dateUtil (class com.liferay.portal.kernel.util.DateUtil_IW)
2020-03-30 14:21:42.106 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] serviceLocator (class com.liferay.portal.template.ServiceLocator)
2020-03-30 14:21:42.107 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] serviceContext (class com.liferay.portal.kernel.service.ServiceContext)
2020-03-30 14:21:42.108 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] jsonFactoryUtil (class com.liferay.portal.json.JSONFactoryImpl)
2020-03-30 14:21:42.109 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] stringUtil (class com.liferay.portal.kernel.util.StringUtil_IW)
2020-03-30 14:21:42.110 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] freeMarkerPortletPreferences (class com.liferay.portal.template.TemplatePortletPreferences)
2020-03-30 14:21:42.110 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] dateFormats (class com.liferay.portal.util.FastDateFormatFactoryImpl)
2020-03-30 14:21:42.110 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] dateFormatFactory (class com.liferay.portal.util.FastDateFormatFactoryImpl)
2020-03-30 14:21:42.111 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userPortraitURL (class java.lang.String)
2020-03-30 14:21:42.111 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portal (class com.liferay.portal.util.PortalImpl)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] commonPermission (class com.liferay.portal.service.permission.CommonPermissionImpl)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userURL (class java.lang.String)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] expandoValueLocalService (class com.sun.proxy.$Proxy45)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] entryType (class java.lang.String)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] organizationPermission (class com.liferay.portal.service.permission.OrganizationPermissionImpl)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] passwordPolicyPermission (class com.liferay.portal.service.permission.PasswordPolicyPermissionImpl)
2020-03-30 14:21:42.112 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] expandoColumnLocalService (class com.sun.proxy.$Proxy41)
2020-03-30 14:21:42.113 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] taskComments (class java.lang.String)
2020-03-30 14:21:42.113 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] staticFieldGetter (class com.liferay.portal.kernel.util.StaticFieldGetter)
2020-03-30 14:21:42.113 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] htmlUtil (class com.liferay.portal.util.HtmlImpl)
2020-03-30 14:21:42.113 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] languageUtil (class com.liferay.portal.language.LanguageImpl)
2020-03-30 14:21:42.113 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] enumUtil (class freemarker.ext.beans._EnumModels)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] windowStateFactory (class com.liferay.portal.kernel.portlet.WindowStateFactory_IW)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] companyId (class java.lang.String)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] unicodeFormatter (class com.liferay.portal.kernel.util.UnicodeFormatter_IW)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] propsUtil (class com.liferay.portal.util.PropsImpl)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] browserSniffer (class com.liferay.portal.servlet.BrowserSnifferImpl)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portletProviderAction (class java.util.HashMap)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] taskName (class java.lang.String)
2020-03-30 14:21:42.114 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] httpUtil (class com.liferay.portal.template.TemplateContextHelper$HttpWrapper)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portletURLFactory (class com.liferay.portlet.internal.PortletURLFactoryImpl)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] imageToken (class com.liferay.portal.webserver.WebServerServletTokenImpl)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] groupPermission (class com.liferay.portal.service.permission.GroupPermissionImpl)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] timeZoneUtil (class com.liferay.portal.kernel.util.TimeZoneUtil_IW)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] unicodeLanguageUtil (class com.liferay.portal.language.UnicodeLanguageImpl)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] expandoRowLocalService (class com.sun.proxy.$Proxy42)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] auditRouterUtil (class com.sun.proxy.$Proxy253)
2020-03-30 14:21:42.115 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] kaleoTaskInstanceToken (class com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] accountPermission (class com.liferay.portal.service.permission.AccountPermissionImpl)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] httpUtilUnsafe (class com.liferay.portal.template.TemplateContextHelper$HttpWrapper)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] workflowTaskAssignees (class java.util.ArrayList)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] random (class java.util.Random)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] rolePermission (class com.liferay.portal.service.permission.RolePermissionImpl)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portletPermission (class com.liferay.portal.service.permission.PortletPermissionImpl)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] paramUtil (class com.liferay.portal.kernel.util.ParamUtil_IW)
2020-03-30 14:21:42.116 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] locationPermission (class com.liferay.portal.service.permission.OrganizationPermissionImpl)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] calendarFactory (class com.liferay.portal.util.CalendarFactoryImpl)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] webServerToken (class com.liferay.portal.webserver.WebServerServletTokenImpl)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] sessionClicks (class com.liferay.portal.kernel.util.SessionClicks_IW)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userPermission (class com.liferay.portal.service.permission.UserPermissionImpl)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] entryClassName (class java.lang.String)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userGroupPermission (class com.liferay.portal.service.permission.UserGroupPermissionImpl)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] arrayUtil (class com.liferay.portal.kernel.util.ArrayUtil_IW)
2020-03-30 14:21:42.117 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userName (class java.lang.String)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] userId (class java.lang.Long)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] prefsPropsUtil (class com.liferay.portal.util.PrefsPropsImpl)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] url (class java.lang.String)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] kaleoInstanceToken (class com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenImpl)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] utilLocator (class com.liferay.portal.template.UtilLocator)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] objectUtil (class com.liferay.portal.template.freemarker.internal.LiferayObjectConstructor)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] urlCodec (class com.liferay.portal.kernel.util.URLCodec_IW)
2020-03-30 14:21:42.118 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] portletModeFactory (class com.liferay.portal.kernel.portlet.PortletModeFactory_IW)
2020-03-30 14:21:42.119 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] imageToolUtil (class com.liferay.portal.image.ImageToolImpl)
2020-03-30 14:21:42.119 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] auditMessageFactoryUtil (class com.liferay.portal.security.audit.internal.AuditMessageFactoryImpl)
2020-03-30 14:21:42.119 DEBUG [liferay/kaleo_graph_walker-2][TemplateNotificationMessageGenerator:94] staticUtil (class freemarker.ext.beans.StaticModels)
-->
