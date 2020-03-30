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
available in your workflow notification context.

## Discovering the Workflow Notification Template Variables

Definitively discover the variables available in your workflow notification
context by enabling DEBUG level logging for the
`TemplateNotificationMessageGenerator` class:

1. Control Panel &rarr; Configuration &rarr; Server Administration.
 
1. Click the Log Levels tab.
 
1. Add a Log Level with this configuration:

   *Logger Name*: `com.liferay.portal.workflow.kaleo.runtime.internal.notification.TemplateNotificationMessageGenerator`
   *Log Level*: `DEBUG`

1. [Activate a workflow
   definition](https://help.liferay.com/hc/en-us/articles/360029042051-Activating-Workflow#workflow-assets)
   (e.g., the Single Approver definition) on an asset (e.g., Blogs Entry).

1. Submit a test entry, and you'll see log messages appear that reveal what
   workflow notification template variables are available in the notification
   context of the workflow you're interested in.

When you do this for the Single Approver on initial submission of an asset,
Liferay DXP prints log messages exposing this list of available variables:

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
```

### Understanding the Workflow Notification Variables

It's nice to have a list of variables, but what are they all for?

| Variable Name                | Description                                  |
| ---------------------------- | -------------------------------------------- |
| ${saxReaderUtil} |
| ${getterUtil} |
| ${portalPermission} |
| ${entryClassPK} |
| ${layoutPermission} |
| ${expandoTableLocalService} |
| ${localeUtil} |
| ${groupId} |
| ${portalUtil} |
| ${validator} |
| ${dateUtil} |
| ${serviceLocator} |
| ${serviceContext} |
| ${jsonFactoryUtil} |
| ${stringUtil} |
| ${freeMarkerPortletPreferences} |
| ${dateFormats} |
| ${dateFormatFactory} |
| ${userPortraitURL} |
| ${portal} |
| ${commonPermission} |
| ${userURL} |
| ${expandoValueLocalService} |
| ${entryType} |
| ${organizationPermission} |
| ${passwordPolicyPermission} |
| ${expandoColumnLocalService} |
| ${taskComments} |
| ${staticFieldGetter} |
| ${htmlUtil} |
| ${languageUtil} |
| ${enumUtil} |
| ${windowStateFactory} |
| ${companyId} |
| ${unicodeFormatter} |
| ${propsUtil} |
| ${browserSniffer} |
| ${portletProviderAction} |
| ${taskName} |
| ${httpUtil} |
| ${portletURLFactory} |
| ${imageToken} |
| ${groupPermission} |
| ${timeZoneUtil} |
| ${unicodeLanguageUtil} |
| ${expandoRowLocalService} |
| ${auditRouterUtil} |
| ${kaleoTaskInstanceToken} |
| ${accountPermission} |
| ${httpUtilUnsafe} |
| ${workflowTaskAssignees} |
| ${random} |
| ${rolePermission} |
| ${portletPermission} |
| ${paramUtil} |
| ${locationPermission} |
| ${calendarFactory} |
| ${webServerToken} |
| ${sessionClicks} |
| ${userPermission} |
| ${entryClassName} |
| ${userGroupPermission} |
| ${arrayUtil} |
| ${userName} |
| ${userId} |
| ${prefsPropsUtil} |
| ${url} |
| ${kaleoInstanceToken} |
| ${utilLocator} |
| ${objectUtil} |
| ${urlCodec} |
| ${portletModeFactory} |
| ${imageToolUtil} |
| ${auditMessageFactoryUtil} |
| ${staticUtil} |


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
