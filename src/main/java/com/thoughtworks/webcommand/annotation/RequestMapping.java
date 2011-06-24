package com.thoughtworks.webcommand.annotation;

import com.thoughtworks.webcommand.HttpVerb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String uri() default "/";
    HttpVerb verb() default HttpVerb.GET;

}
