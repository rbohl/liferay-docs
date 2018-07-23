# Script Examples

Here are some examples to help you use Liferay's script console. Note: These 
originated from this Liferay blog post: [https://www.liferay.com/web/sebastien.lemarchand/blog/-/blogs/5-tips-to-improve-usage-of-the-liferay-script-console](https://www.liferay.com/web/sebastien.lemarchand/blog/-/blogs/5-tips-to-improve-usage-of-the-liferay-script-console).
The following scripts are Groovy scripts but they can be adapted to other
languages.

## Example 1: Using the Predefined Variables [](id=example-1-use-the-predefined-variables)

The following predefined variables are available to scripts executed from
Liferay's script console:

- `out` (`java.io.PrintWriter`)
- `actionRequest` (`javax.portlet.ActionRequest`)
- `actionResponse` (`javax.portlet.ActionReponse`)
- `portletConfig` (`javax.portlet.PortletConfig`)
- `portletContext` (`javax.portlet.PortletContext`)
- `preferences` (`javax.portlet.PortletPreferences`)
- `userInfo` (`java.util.Map<String, String>`)

Note that if you use `System.out.println`, for example, your output will be
printed to Liferay's log file. If you use `out.println` instead (using the
predefined variable), your output will be printed to the script console.

The predefined variables can all be very useful when you're creating scripts.
The `actionRequest` variable can be especially useful, as this script
demonstrates:

    import com.liferay.portal.kernel.util.*

    company = PortalUtil.getCompany(actionRequest)
    out.println("Current Company:${company.getName()}\n")

    out.println("User Info:")
    userInfo.each { 
            k,v -> out.println("${k}:${v}") 
    }

![Figure 1: Here's an example of invoking a Groovy script that uses the predefined `out`, `actionRequest`, and `userInfo` variables to print information about the current user.](../../../images/groovy-script-current-user-info.png)

## Example 2: Embedding HTML Markup in Script Outputs [](id=example-2-embed-html-markup-in-script-outputs)

The output of the script console is rendered as HTML content. Thus, you can
embed HTML markup in your outputs to change their look and feel. Here's an
example:

    import com.liferay.portal.kernel.service.*

    number = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUsersCount();
    out.println(
            """	
                    <div style="background-color:black; text-align: center">
                            <h1 style="color: #37A9CC; font-size:xx-large">${number}</h1>
                    </div>
            """);

![Figure 2: Here's an example of invoking a Groovy script that embeds HTML markup in the output of the script.](../../../images/groovy-script-embed-html-markup.png)

## Example 3: Show Exceptions in the Script Console [](id=example-3-show-exceptions-in-the-script-console)

When any exception occurs during script execution, the error message is always
the same:

    Your request failed to complete.

This message gives no detail about the error. To find information about the
error and what caused it, you usually need to examine the server logs.

However, you can use the following technique to make exception details appear in
the script console. Wrap your code with a try / catch block and print the stack
trace to the console output from the catch clause. Note that even this technique
does not catch script syntax errors. Here's an example:

    try {
            nullVar = null
            out.println(nullVar.length())
    } catch(e) {
            out.println("""<div class="portlet-msg-error">${e}</div>""")
            e.printStackTrace(out)
    }

![Figure 3: Here's an example of a Groovy script that catches exceptions and prints exception information to the script console.](../../../images/groovy-script-show-exception.png)

## Example 4: Implement a Preview Mode [](id=example-4-implement-a-preview-mode)

Since Liferay's script console does not provide an undo feature, it can be very
convenient to set up a kind of preview mode. The purpose of a preview mode is to
determine any permanent effects of a script before any information is actually
saved to the Liferay database. The preview mode consists in using a
`previewMode` flag which determines whether the operations with permanent
effects should be executed or not. If `previewMode` is `true`, all of the data
which would be permanently affected by the script is systematically printed.
This providers the user executing the script with an outline of the data
impacted by the script. If the user determines that everything is OK, the flag
can be switched so that the script can make permanent updates to the database.

Here's an example Groovy script that show the preview mode concept in action:

    import java.util.Calendar
    import com.liferay.portal.kernel.service.*
    import com.liferay.portal.kernel.model.*
    import com.liferay.portal.kernel.dao.orm.*
    import static com.liferay.portal.kernel.workflow.WorkflowConstants.*

    //
    // Deactivate users never logged and created since more than 2 years
    //

    previewMode = true // Update this flag to false to really make changes

    Calendar twoYearsAgo = Calendar.getInstance()
    twoYearsAgo.setTime(new Date())
    twoYearsAgo.add(Calendar.YEAR, -2)

    DynamicQuery query = DynamicQueryFactoryUtil.forClass(User.class)
            .add(PropertyFactoryUtil.forName("lastLoginDate").isNull())
            .add(PropertyFactoryUtil.forName("createDate").lt(twoYearsAgo.getTime()))

    users = UserLocalServiceUtil.dynamicQuery(query)

    users.each { u ->
             if(!u.isDefaultUser() && u.getStatus() != STATUS_INACTIVE) {
                    out.println(u.getEmailAddress())
                    if(!previewMode) {
                            UserLocalServiceUtil.updateStatus(u.getUserId(), STATUS_INACTIVE)
                    }
             }
    }

    if(previewMode) {
            out.println('Preview mode is on: switch off the flag and execute '
                    + 'again this script to make changes to the database') 
    }

## Example 5: Plan a File Output for Long-Running Scripts [](id=example-5-plan-a-file-output-for-long-running-scripts)

When a script has been running for a long time, it's possible for the script
console to return an error even though the script can continue running and
potentially conclude successfully. But it's impossible to know the outcome
without the corresponding output!

To bypass this limitation, you can send the output of the script console to a
custom file instead of to the console itself or to the Liferay log. For example,
consider this script:

    import com.liferay.portal.kernel.service.*
    import com.liferay.portal.kernel.dao.orm.*

    // Output management

    final def SCRIPT_ID = "MYSCRIPT"
    outputFile = new File("""${System.getProperty("liferay.home")}/scripting/out-${SCRIPT_ID}.txt""")
    outputFile.getParentFile().mkdirs()

    def trace(message) {
            out.println(message)
            outputFile << "${message}\n"
    }

    // Main code

    users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS)
    users.each { u ->
            trace(u.getFullName())
    } 

The script above creates a subfolder of
[Liferay Home](/discover/deployment/-/knowledge_base/7-0/installing-product#liferay-home)
called `scripting` and saves the script output to a file in this folder. After
running the script above, it's possible to read the generated file without
direct access to the file system. Here's a second script that demonstrates this:

    final def SCRIPT_ID = "MYSCRIPT"
    outputFile = new File("""${System.getProperty("liferay.home")}/scripting/out-${SCRIPT_ID}.txt""")
    out.println(outputFile.text)

One advantage of using a dedicated output file instead of using a classic logger
is that it's easier to get the script output data back. Getting the script
output data would be more difficult to obtain from the portal log, for example,
because of all the other information there.

Of course, Liferay's script engine has uses beyond the script console. Next,
you'll learn how to leverage Liferay's script engine for designing workflows.

## Related Topics [](id=related-topics)

[Running Scripts From the Script Console](/discover/portal/-/knowledge_base/7-1/running-scripts-from-the-script-console)

[Leveraging the Script Engine in Workflow](/discover/portal/-/knowledge_base/7-1/leveraging-the-script-engine-in-workflow)

[Using Liferay's Script Engine](/discover/portal/-/knowledge_base/7-1/using-liferays-script-engine)
