package com.thoughtworks.webcommand;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class DispatchServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(DispatchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String packages = this.getServletConfig().getInitParameter("package");
        System.out.println("package: "+ packages);

        try {
            Class[] handlerClasses = new CommandHandlerFinder(packages).getClasses();
            new CommandHandlerDispatcher(req, handlerClasses).dispatch();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
