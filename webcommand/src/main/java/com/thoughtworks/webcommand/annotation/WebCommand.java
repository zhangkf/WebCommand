package com.thoughtworks.webcommand.annotation;

import com.thoughtworks.webcommand.HttpVerb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WebCommand {

    String uri();
    HttpVerb verb() default HttpVerb.GET;

}
