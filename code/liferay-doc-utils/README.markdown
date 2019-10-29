# Liferay Doc Utils

The Liferay Doc Utils consist of utility classes and Ant Task classes that allow the documentation to be checked in various ways and, ultimately, to be built into a publishable ZIP file in the Liferay Knowledge Base where the documentation is currently published. See <https://portal.liferay.dev/docs> for the published documentation.

## Working on the Source

If you edit any of the classes in this project, you'll need to compile the `liferay-doc-utils` JAR and place it in `liferay-docs/lib`. This can be done conveniently with one Ant Task.

> At the root of the project, run `ant deploy`.

The JAR is built and placed in the `lib` folder at the root of `liferay-docs`. Now, running an Ant Task as usual takes advantage of your code updates.

## Debugging Ant Tasks

These steps have been successfully used to debug Ant Tasks in Linux/Eclipse:

1.  Export these Ant Options in the `ANT_OPTS` environment variable:

    ```bash
    export ANT_OPTS=-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=y
    ```


2.  In Eclipse, create a debug configuration listening on port 8787.

3.  Set a breakpoint in the class of interest.

4.  From the command line (make sure you're in a shell where the modified `ANT_OPTS` are set), run one of the ant tasks:

    ```bash
    ant check-links-dxp
    ```

    You'll see this in the log:

    ```bash
    Listening for transport dt_socket at address: 8787
    ```

5.  In Eclipse, run the debugger.

The `ANT_OPTS` set things up to suspend the Ant Task, so you can hook up the debugger in Eclipse. Once you run the debugger, the processing will continue and hit your breakpoint. 

