package com.thoughtworks.webcommand;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CommandHandlerLocatorTest {

    private Class[] classes;

    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        String packageName = "com.thoughtworks.webcommand";
        this.classes = new CommandHandlerFinder(packageName).getClasses();
    }

    @Test
    public void should_return_handler_class_if_url_pattern_matches() {
        try {
            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
            assertThat(handlerClass.getName(), is("com.thoughtworks.webcommand.handler.sample.SamplePostCommandHandler"));
        } catch (ClassNotFoundException e) {
            fail("should find Handler Class");
        }

    }


    @Test(expected = ClassNotFoundException.class)
    public void should_throw_exception_if_no_handler_match_url_pattern() throws ClassNotFoundException {
        new CommandHandlerLocator(this.classes).locate("/sample/wrongurl");
    }


}
