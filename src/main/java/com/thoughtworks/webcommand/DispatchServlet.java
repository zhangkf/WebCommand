package com.thoughtworks.webcommand;


import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String packages = req.getServletContext().getInitParameter("package");

        try {
            Class[] handlerClasses = new ClassesFinder().getClasses(packages);
            for (Class handlerClass : handlerClasses) {
                checkHandlerClass(handlerClass, req);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void checkHandlerClass(Class handlerClass, HttpServletRequest req) {
        if (handlerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mappingAnnotation = (RequestMapping) handlerClass.getAnnotation(RequestMapping.class);
            String pathInfo = req.getPathInfo();
            if (mappingAnnotation.urlPattern().equals(pathInfo)) {
                Method[] methods = handlerClass.getMethods();
                for (Method method : methods) {
                    checkRequestMethod(handlerClass, method, req);
                }
            }

        }
    }

    private void checkRequestMethod(Class handlerClass, Method method, HttpServletRequest request) {
        String requestMethod = request.getMethod();
        Map parameterMap = request.getParameterMap();
        if (method.isAnnotationPresent(RequestMethod.class)) {
            RequestMethod methodAnnotation = method.getAnnotation(RequestMethod.class);
            if (methodAnnotation.value().equals(requestMethod)) {
                invokeHandlerMethodWithRequestParams(handlerClass, method, parameterMap);
            }
        }

    }

    private void invokeHandlerMethodWithRequestParams(Class handlerClass, Method method, Map parameterMap) {
        try {
            Object o = handlerClass.newInstance();
            method.invoke(o, parameterMap);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
