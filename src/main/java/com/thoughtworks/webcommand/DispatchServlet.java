package com.thoughtworks.webcommand;


import com.thoughtworks.webcommand.exception.MethodNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class DispatchServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(DispatchServlet.class.getName());
    public CommandHandlerLocator commandHandlerLocator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String packages = this.getServletConfig().getInitParameter("package");
        logger.info("Get package from web.xml:" + packages);
        try {
            Set<Class<?>> handlerClasses = new CommandHandlerFinder(packages).scanPackage();
            commandHandlerLocator = new CommandHandlerLocator(handlerClasses);
        } catch (Exception e) {
            logger.throwing("DispatchServlet", "init", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getRequestURI();

            CommandHandlerInvoker commandHandlerInvoker = commandHandlerLocator.locate(pathInfo, req.getMethod());
            Object result = commandHandlerInvoker.invoke(extractParameters(req));

            PrintWriter writer = resp.getWriter();
            writer.println(result);
            writer.flush();

        } catch (ClassNotFoundException e) {
            logger.throwing("DispatchServlet", "doGet", e);
        } catch (MethodNotFoundException e) {
            logger.throwing("DispatchServlet", "doGet", e);
        } catch (Exception e) {
            logger.throwing("DispatchServlet", "doGet", e);
        }
    }

    private Map<String, Object> extractParameters(HttpServletRequest req) {
        Enumeration parameterNames = req.getParameterNames();
        Map<String, Object> parameterMap = new Hashtable<String, Object>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            String parameterValue = req.getParameter(parameterName);
            parameterMap.put(parameterName, parameterValue);
        }
        return parameterMap;
    }
}
