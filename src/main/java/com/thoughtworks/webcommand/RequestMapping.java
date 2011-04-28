package com.thoughtworks.webcommand;

public @interface RequestMapping {

    String urlPattern() default "/";

    RequestMethod method() default RequestMethod.GET;
}
