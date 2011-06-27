How to use WebCommand?
1. Configure DispatchServlet in web.xml, with init-param "package"

        <servlet>
            <servlet-name>dispatchServlet</servlet-name>
            <servlet-class>com.thoughtworks.webcommand.DispatchServlet</servlet-class>
        <init-param>
            <param-name>package</param-name>
            <param-value>com.thoughtworks.webcommand.handler.sample</param-value>
        </init-param>
        </servlet>

        <servlet-mapping>
        <servlet-name>dispatchServlet</servlet-name>
           <url-pattern>/command/*</url-pattern>
        </servlet-mapping>

2. Create your own command handler annotated with RequestMapping, RequestMethod, RequestParam.

        @WebCommand(uri = "/sample", verb= POST)
        public class SamplePostCommandHandler {
            public String handle(@RequestParam("username") String username, @RequestParam("password") String password) {
            // handle the command
            }
        }

Contracts:

1. One command handler should handle one and only one command. So each command handler should have one and only one public method. The name of the method can be variable but it must be public.
2. You can specify the same uri to different command handler, as long as their _verb_ is different


TODO:
1. Configure multiple packages in web.xml
2. Throw exception when multiple command handlers annotated with same RequestMapping
3. Default method will be invoked in command handle if no corresponding method exists.
4.
