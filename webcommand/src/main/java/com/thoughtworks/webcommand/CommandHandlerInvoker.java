package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.annotation.RequestParam;
import com.thoughtworks.webcommand.exception.MethodNotFoundException;
import com.thoughtworks.webcommand.exception.ParameterNotFoundException;
import com.thoughtworks.webcommand.exception.ParameterTypeNotMatchException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CommandHandlerInvoker {
    private Class handlerClass;

    CommandHandlerInvoker(Class handlerClass) {
        this.handlerClass = handlerClass;
    }

    Object invoke(Map parameterMap) throws Exception {
        Method handlerMethod = getHandlerMethod();

        Annotation[][] parameterAnnotations = handlerMethod.getParameterAnnotations();
        Class<?>[] parameterTypes = handlerMethod.getParameterTypes();

        List<Object> values = new ArrayList<Object>();

        for (int i = 0; i < parameterTypes.length; i++) {
            RequestParam paramAnnotation = (RequestParam) parameterAnnotations[i][0];
            String parameterName = paramAnnotation.value();

            assertParameterPresent(parameterMap, parameterName);

            Object requestParameter = parameterMap.get(parameterName);
            assertParameterTypeMatch(parameterTypes[i], parameterName, requestParameter);

            values.add(requestParameter);
        }

        return handlerMethod.invoke(handlerClass.newInstance(), values.toArray(new Object[values.size()]));
    }

    private void assertParameterTypeMatch(Class<?> parameterType, String parameterName, Object requestParameter) throws ParameterTypeNotMatchException {
        if (!parameterType.getName().equals(requestParameter.getClass().getName())) {
            throw new ParameterTypeNotMatchException(parameterName, parameterType.getName(), requestParameter.getClass().getName());
        }
    }

    private void assertParameterPresent(Map parameterMap, String parameterName) throws ParameterNotFoundException {
        boolean containParameterName = parameterMap.containsKey(parameterName);

        if (!containParameterName) {
            throw new ParameterNotFoundException("Parameter ["+parameterName+"] not found in request parameters");
        }
    }

    Class getHandlerClass(){
        return handlerClass;
    }

    private Method getHandlerMethod() throws MethodNotFoundException {
        Method[] methods = handlerClass.getMethods();
        for (Method method : methods) {
            if(method.getModifiers()== Modifier.PUBLIC){
                return method;
            }
        }

        throw new MethodNotFoundException("NO public method defined in handler class "+handlerClass.getName());
    }
}
