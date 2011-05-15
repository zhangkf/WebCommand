package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;

@RequestMapping(urlPattern = "/sample")
public class SampleCommandHandler {

    @RequestMethod("GET")
    public String get(@RequestParam("username") String username, @RequestParam("password") String password) {
        return username + password;
    }


    @RequestMethod("POST")
    public String update(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "update";
    }

    @RequestMethod("PUT")
    public String save(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "put";
    }

    @RequestMethod("DELETE")
    public String delete(@RequestParam("username") String username) {
        return "deleted";
    }
}
