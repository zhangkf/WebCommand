package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CommandHandlerDispatcher {


    private Class[] handlerClasses;
    private HttpServletRequest request;

    CommandHandlerDispatcher(HttpServletRequest req, Class[] handlerClasses) {
        this.request = req;
        this.handlerClasses = handlerClasses;
    }

    void dispatch() {

        for (Class handlerClass : handlerClasses) {
            System.out.println("Current Class: " + handlerClass.getName());
            checkHandlerClass(handlerClass, request);
        }
    }


    private void checkHandlerClass(Class handlerClass, HttpServletRequest req) {
        boolean annotationPresent = handlerClass.isAnnotationPresent(RequestMapping.class);
        System.out.println(annotationPresent);

        if (annotationPresent) {
            RequestMapping mappingAnnotation = (RequestMapping) handlerClass.getAnnotation(RequestMapping.class);
            String pathInfo = req.getRequestURI();
            System.out.println("request pathInfo: " + pathInfo);
            String urlPattern = mappingAnnotation.urlPattern();
            System.out.println("handler: " + handlerClass.getName() + ", urlPattern: " + urlPattern);
            if (urlPattern.equals(pathInfo)) {
                Method[] methods = handlerClass.getMethods();
                for (Method method : methods) {
                    if (checkRequestMethod(handlerClass, method, req)) {
                        return;
                    }
                }
            }

        }
    }

    private boolean checkRequestMethod(Class handlerClass, Method method, HttpServletRequest request) {
        String requestMethod = request.getMethod();
        if (method.isAnnotationPresent(RequestMethod.class)) {
            RequestMethod methodAnnotation = method.getAnnotation(RequestMethod.class);
            String methodName = methodAnnotation.value();
            System.out.println("requestMethod: " + requestMethod);
            System.out.println("handler: " + handlerClass.getName() + ", acceptMethod: " + methodName);
            if (methodName.equals(requestMethod)) {
                invokeHandlerMethodWithRequestParams(handlerClass, method, request);
            }

            return true;
        }

        return false;

    }

    private void invokeHandlerMethodWithRequestParams(Class handlerClass, Method method, HttpServletRequest request) {
        try {


            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Class<?>[] parameterTypes = method.getParameterTypes();

            List<Object> values = new ArrayList<Object>();

            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                RequestParam paramAnnotaion = (RequestParam) parameterAnnotations[i][0];

                String parameterName = paramAnnotaion.value();

                System.out.println(parameterName + " : " + parameterType.getName());
                String parameterValue = request.getParameter(parameterName);
                values.add(parameterValue);
            }

            Object o = handlerClass.newInstance();
            Object result = method.invoke(o, values.toArray(new Object[0]));

            System.out.println(result.getClass().getName() + ": " + result);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
