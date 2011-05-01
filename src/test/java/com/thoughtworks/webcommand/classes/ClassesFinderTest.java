package com.thoughtworks.webcommand.classes;

import com.thoughtworks.webcommand.ClassesFinder;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertThat;

public class ClassesFinderTest {


    @Test
    public void should_find_class_under_package() {

        String packageName = "com.thoughtworks.webcommand.classes";
        Class[] classes = new Class[0];

        try {
            classes = new ClassesFinder().getClasses(packageName);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(classes[0].getName(), Is.is("com.thoughtworks.webcommand.classes.ClassesFinderTest"));

    }


}
