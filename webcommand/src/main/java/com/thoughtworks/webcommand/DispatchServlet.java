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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class DispatchServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(DispatchServlet.class.getName());
    public CommandHandlerLocator commandHandlerLocator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initLogger();
        String packages = this.getServletConfig().getInitParameter("package");
        logger.info("Get package of commanders from web.xml:" + packages);
        try {
            commandHandlerLocator = new CommandHandlerLocator(new CommandHandlerScanner(packages));
        } catch (Throwable e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void initLogger() {
        Logger.getLogger("").addHandler(new ConsoleHandler());
        Logger.getLogger("com.thoughtworks.webcommand").setLevel(Level.INFO);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String commandUri = req.getPathInfo();
            logger.info("Serve request for command: "+commandUri);

            Object result = commandHandlerLocator.locate(commandUri, req.getMethod()).invoke(extractParameters(req));
            PrintWriter writer = resp.getWriter();
            writer.println(result);
            writer.flush();

        } catch (ClassNotFoundException e) {
            resp.sendError(SC_BAD_REQUEST, e.getMessage());
            logger.log(Level.SEVERE,e.getMessage(), e);
        } catch (MethodNotFoundException e) {
            resp.sendError(SC_BAD_REQUEST, e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (Throwable e) {
            resp.sendError(SC_BAD_REQUEST, e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
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
