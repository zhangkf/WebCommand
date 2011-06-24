package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.annotation.WebCommand;
import com.thoughtworks.webcommand.annotation.RequestParam;

import static com.thoughtworks.webcommand.HttpVerb.*;

@WebCommand(uri = "/sample", verb= POST)
public class SamplePostCommandHandler {
    public String handle(@RequestParam("username") String username, @RequestParam("password") String password) {
        return username + password;
    }
}

