package com.thoughtworks.webcommand.annotation;

public @interface RequestMapping {

    String urlPattern() default "/";

    RequestMethod method() default RequestMethod.GET;
}
