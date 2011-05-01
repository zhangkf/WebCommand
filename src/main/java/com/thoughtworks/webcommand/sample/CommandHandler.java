package com.thoughtworks.webcommand.sample;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;

@RequestMapping(urlPattern = "/cmd")
public class CommandHandler {

    @RequestMethod("GET")
    public String get(@RequestParam("username") String username) {
        return null;
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
