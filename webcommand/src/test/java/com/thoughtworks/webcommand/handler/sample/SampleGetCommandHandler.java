package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.annotation.RequestParam;
import com.thoughtworks.webcommand.annotation.WebCommand;

import static com.thoughtworks.webcommand.HttpVerb.GET;

@WebCommand(uri = "/sample", verb= GET)
public class SampleGetCommandHandler {
    public String handle(@RequestParam("username") String username, @RequestParam("password") String password) {
        return username + password;
    }
}
