package com.thoughtworks.webcommand;

import com.thoughtworks.webcommand.exception.ParameterNotFoundException;
import com.thoughtworks.webcommand.exception.ParameterTypeNotMatchException;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommandHandlerInvokerTest {

    public CommandHandlerInvoker commandHandlerInvoker;

    @Before
    public void setUp() throws Exception {
        String packageName = "com.thoughtworks.webcommand";
        CommandHandlerLocator commandHandlerLocator = new CommandHandlerLocator(new CommandHandlerScanner(packageName));

        commandHandlerInvoker = commandHandlerLocator.locate("/sample", "POST");
    }

    @Test
    public void should_invoke_if_method_parameters_name_and_type_match() throws Exception {

        Map<String, String> paramterMap = new Hashtable<String, String>();
        paramterMap.put("username", "username");
        paramterMap.put("password", "password");

        assertThat(commandHandlerInvoker.invoke(paramterMap).toString(), is("usernamepassword"));
    }

    @Test(expected = ParameterNotFoundException.class)
    public void should_throw_exception_if_method_parameter_names_not_match() throws Exception {
        Map<String, String> paramterMap = new Hashtable<String, String>();
        paramterMap.put("username", "zhangkf");

        commandHandlerInvoker.invoke(paramterMap);
    }

    @Test(expected = ParameterTypeNotMatchException.class)
    public void should_throw_exception_if_parameter_type_not_match() throws Exception {
        Map<String, Object> paramterMap = new Hashtable<String, Object>();
        paramterMap.put("username", "zhangkf");
        paramterMap.put("password", 12345d);

        commandHandlerInvoker.invoke(paramterMap);
    }
}
