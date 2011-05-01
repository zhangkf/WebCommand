package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.annotation.RequestMapping;

class CommandHandlerLocator {
    private Class[] handlerClasses;

    CommandHandlerLocator(Class[] classes) {
        this.handlerClasses = classes;
    }

    Class locate(String urlPattern) throws ClassNotFoundException {

        for (Class handlerClass : handlerClasses) {

            boolean annotationPresent = handlerClass.isAnnotationPresent(RequestMapping.class);

            if (annotationPresent) {
                RequestMapping mappingAnnotation = (RequestMapping) handlerClass.getAnnotation(RequestMapping.class);
                if (mappingAnnotation.urlPattern().equals(urlPattern)) {
                    return handlerClass;
                }

            }
        }

        throw new ClassNotFoundException("Handler Class not found for url pattern: " + urlPattern);


    }

}
