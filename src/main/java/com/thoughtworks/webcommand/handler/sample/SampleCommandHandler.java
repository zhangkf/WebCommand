package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;

@RequestMapping(urlPattern = "/cmd")
public class SampleCommandHandler {

    @RequestMethod("GET")
    public String get(@RequestParam("username") String username, @RequestParam("pwd") String pwd) {
        System.out.println(username);
        return "zhangkf";
    }


    @RequestMethod("POST")
    public String update() {
        return null;
    }

    @RequestMethod("PUT")
    public String save() {
        return null;
    }

    @RequestMethod("DELETE")
    public String delete() {
        return null;
    }
}
