package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.handler.sample.SamplePostCommandHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CommandHandlerLocatorTest {

    public CommandHandlerLocator commandHandlerLocator;

    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        String packageName = "com.thoughtworks.webcommand";
        Class[] classes = new CommandHandlerFinder(packageName).getClasses();
        commandHandlerLocator = new CommandHandlerLocator(classes);
    }

    @Test
    public void should_return_handler_class_if_url_pattern_matches() throws ClassNotFoundException {
        CommandHandlerInvoker commandHandlerInvoker = commandHandlerLocator.locate("/sample", "POST");
        assertTrue(commandHandlerInvoker.getHandlerClass().equals(SamplePostCommandHandler.class));
    }


    @Test(expected = ClassNotFoundException.class)
    public void should_throw_exception_if_no_handler_match_url_pattern() throws ClassNotFoundException {
        commandHandlerLocator.locate("/sample/wrongurl", "POST");
    }

    @Test(expected = ClassNotFoundException.class)
    public void should_throw_exception_if_handler_match_url_pattern_but_verb_not_match() throws ClassNotFoundException {
        commandHandlerLocator.locate("/sample", "GET");
    }

}
