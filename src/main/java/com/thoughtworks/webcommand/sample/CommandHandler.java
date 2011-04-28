package com.thoughtworks.webcommand.sample;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestMethod;
import com.thoughtworks.webcommand.annotation.RequestParam;

@RequestMapping(urlPattern = "/cmd")
public class CommandHandler {

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam("username") String username) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update() {
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String save() {
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete() {
        return null;
    }
}
