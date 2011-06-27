package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.annotation.WebCommand;

import java.util.Set;

class CommandHandlerLocator {
    private Set<Class<?>> handlerClasses;

    CommandHandlerLocator(CommandHandlerFinder commandHandlerFinder) throws Exception {
        this.handlerClasses = commandHandlerFinder.scanPackage();
    }

    CommandHandlerInvoker locate(String urlPattern, String httpVerb) throws ClassNotFoundException {
        for (Class handlerClass : handlerClasses) {
            boolean annotationPresent = handlerClass.isAnnotationPresent(WebCommand.class);
            if (annotationPresent) {
                WebCommand mappingAnnotation = (WebCommand) handlerClass.getAnnotation(WebCommand.class);
                if (mappingAnnotation.uri().equals(urlPattern) && mappingAnnotation.verb().toString().equals(httpVerb.toUpperCase())) {
                    return new CommandHandlerInvoker(handlerClass);
                }
            }
        }
        throw new ClassNotFoundException("No handler register for uri: " + urlPattern);
    }

}
