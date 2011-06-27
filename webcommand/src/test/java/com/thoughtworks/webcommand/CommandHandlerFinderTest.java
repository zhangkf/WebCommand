package com.thoughtworks.webcommand;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommandHandlerFinderTest {
    @Test
    public void should_return_handler_class_array_by_package() throws Exception {
        String packageName = "com.thoughtworks.webcommand.handler";
        Set<Class<?>> classes = new CommandHandlerFinder(packageName).scanPackage();

        assertThat(classes.size(), is(1));
        assertThat(((Class)classes.toArray()[0]).getName(), is("com.thoughtworks.webcommand.handler.sample.SamplePostCommandHandler"));

    }

    @Test
    public void should_return_empty_array_if_no_class_under_package() throws Exception {
        String packageName = "com.thoughtworks.webcommand.notexist";
        Set<Class<?>> classes = new CommandHandlerFinder(packageName).scanPackage();

        assertThat(classes.size(), is(0));
    }


}
