package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;
import com.thoughtworks.webcommand.exception.MethodNotFoundException;
import com.thoughtworks.webcommand.exception.ParameterNotFoundException;
import com.thoughtworks.webcommand.exception.ParameterTypeNotMatchException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CommandHandlerInvoker {
    private Class handlerClass;

    CommandHandlerInvoker(Class handlerClass) {
        this.handlerClass = handlerClass;
    }

    Method locateMethod(String requestMethod) throws MethodNotFoundException {

        Method[] methods = handlerClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMethod.class)) {
                RequestMethod methodAnnotation = method.getAnnotation(RequestMethod.class);
                String methodName = methodAnnotation.value();
                if (methodName.equals(requestMethod)) {
                    return method;
                }

            }
        }

        throw new MethodNotFoundException("Method not found for request method: " + requestMethod);


    }

    Object invokeHandler(Method method, Map parameterMap) throws Exception {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();

        List<Object> values = new ArrayList<Object>();

        for (int i = 0; i < parameterTypes.length; i++) {


            RequestParam paramAnnotaion = (RequestParam) parameterAnnotations[i][0];
            String parameterName = paramAnnotaion.value();

            boolean containParameterName = parameterMap.keySet().contains(parameterName);

            if (!containParameterName) {
                throw new ParameterNotFoundException("");
            }

            Object parameterValue = parameterMap.get(parameterName);
            Class<?> parameterType = parameterTypes[i];

            if (!parameterType.getName().equals(parameterValue.getClass().getName())) {
                System.out.println(parameterName);
                System.out.println(parameterType.getName());
                System.out.println(parameterValue.getClass().getName());

                throw new ParameterTypeNotMatchException("");
            }

            values.add(parameterValue);


        }

        Object o = handlerClass.newInstance();
        Object result = method.invoke(o, values.toArray(new Object[0]));

        return result;
    }
}
