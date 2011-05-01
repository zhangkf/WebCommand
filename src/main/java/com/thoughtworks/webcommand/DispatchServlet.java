package com.thoughtworks.webcommand;


import com.thoughtworks.webcommand.exception.MethodNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

public class DispatchServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(DispatchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String packages = this.getServletConfig().getInitParameter("package");
        System.out.println("package: " + packages);

        try {
            Class[] handlerClasses = new CommandHandlerFinder(packages).getClasses();

            String pathInfo = req.getRequestURI();


            Class handlerClass = new CommandHandlerLocator(handlerClasses).locate(pathInfo);
            CommandHandlerInvoker commandHandlerInvoker = new CommandHandlerInvoker(handlerClass);
            Method handlerMethod = commandHandlerInvoker.locateMethod(req.getMethod());
            PrintWriter writer = resp.getWriter();

            Enumeration parameterNames = req.getParameterNames();
            Map<String, Object> parameterMap = new Hashtable<String, Object>();
            while (parameterNames.hasMoreElements()) {
                String parameterName = (String) parameterNames.nextElement();
                String parameterValue = req.getParameter(parameterName);
                parameterMap.put(parameterName, parameterValue);
            }

            Object result = commandHandlerInvoker.invokeHandler(handlerMethod, parameterMap);

            writer.println(result);
            writer.flush();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MethodNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
