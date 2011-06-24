package com.thoughtworks.webcommand;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;

public class CommandHandlerFinderTest {
    @Test
    public void should_return_handler_class_array_by_package() throws IOException, ClassNotFoundException {
        String packageName = "com.thoughtworks.webcommand.handler.sample";
        Class[] classes = new CommandHandlerFinder(packageName).getClasses();

        assertThat(classes.length, is(1));
        assertThat(classes[0].getName(), is("com.thoughtworks.webcommand.handler.sample.SamplePostCommandHandler"));

    }

    @Test
    public void should_return_empty_array_if_no_class_under_package() {
        String packageName = "com.thoughtworks.webcommand.notexist";
        Class[] classes = new Class[0];

        try {
            classes = new CommandHandlerFinder(packageName).getClasses();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(classes.length, is(0));
    }


}
