package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.annotation.RequestMapping;
import com.thoughtworks.webcommand.annotation.RequestParam;

import static com.thoughtworks.webcommand.HttpVerb.*;

@RequestMapping(uri = "/sample", verb= POST)
public class SamplePostCommandHandler {
    public String validateLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return username + password;
    }
}

