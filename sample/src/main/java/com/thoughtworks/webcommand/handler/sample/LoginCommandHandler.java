package com.thoughtworks.webcommand.handler.sample;

import com.thoughtworks.webcommand.HttpVerb;
import com.thoughtworks.webcommand.annotation.RequestParam;
import com.thoughtworks.webcommand.annotation.WebCommand;

@WebCommand(uri = "/login", verb= HttpVerb.POST)
public class LoginCommandHandler {
    public String handle(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "Hi Mr. "+username+", welcome back.";
    }
}