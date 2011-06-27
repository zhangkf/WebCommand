package com.thoughtworks.webcommand.exception;

import java.text.MessageFormat;

public class ParameterTypeNotMatchException extends Exception {
    public ParameterTypeNotMatchException(String parameterName, String expectedParameterType, String actualParameterType) {
        super(MessageFormat.format("Parameter []{0} expected type: {1}, actual type: {2}", parameterName, expectedParameterType, actualParameterType));
    }
}
