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
        Method method = locateMethod();

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
    Class getHandlerClass(){
        return handlerClass;
    }

    private Method locateMethod() throws MethodNotFoundException {
        Method[] methods = handlerClass.getMethods();
        for (Method method : methods) {
            if(method.getModifiers()== Modifier.PUBLIC){
                return method;
            }
        }

        throw new MethodNotFoundException("NO public method defined in handler class "+handlerClass.getName());
    }
}
