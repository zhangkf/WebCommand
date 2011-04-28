package com.thoughtworks.webcommand;

@RequestMapping("/cmd")
public class CommandHandler {

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam("username") String username) {
        return null;
    }

    public String update() {
        return null;
    }

    public String save() {
        return null;
    }

    public String delete() {
        return null;
    }
}
