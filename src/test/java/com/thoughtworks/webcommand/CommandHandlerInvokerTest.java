package com.thoughtworks.webcommand;

import org.junit.Before;

import java.io.IOException;

public class CommandHandlerInvokerTest {

    private Class[] classes;

    @Before
    public void setUp() {
        String packageName = "com.thoughtworks.webcommand";
        this.classes = new Class[0];

        try {
            this.classes = new CommandHandlerFinder(packageName).getClasses();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void should_return_method_if_request_method_matched() {
//        try {
//            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
//            Method method = new CommandHandlerInvoker(handlerClass).locateMethod("GET");
//            assertThat(method.getName(), is("get"));
//
//        } catch (ClassNotFoundException e) {
//            fail("should find Handler Class");
//        } catch (MethodNotFoundException e) {
//            fail("should find get method for GET.");
//        }
//
//    }
//
//
//    @Test
//    public void should_throw_exception_if_no_method_match_request_method() {
//        try {
//            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
//            Method method = new CommandHandlerInvoker(handlerClass).locateMethod("FIND");
//            fail("should throw MethodNotFoundException");
//
//        } catch (ClassNotFoundException e) {
//            fail("should find Handler Class");
//        } catch (MethodNotFoundException e) {
//
//        }
//
//    }
//
//
//    @Test
//    public void should_invoke_if_method_parameters_name_and_type_match() {
//        try {
//            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
//            CommandHandlerInvoker commandHandlerInvoker = new CommandHandlerInvoker(handlerClass);
//            Method method = commandHandlerInvoker.locateMethod("GET");
//
//            Map<String, String> paramterMap = new Hashtable<String, String>();
//            paramterMap.put("username", "zhangkf");
//            paramterMap.put("password", "zhangkf");
//
//            assertThat(commandHandlerInvoker.invokeHandler(method, paramterMap).toString(), is("zhangkfzhangkf"));
//
//        } catch (ClassNotFoundException e) {
//            fail("should find Handler Class");
//        } catch (MethodNotFoundException e) {
//            fail("should find Handler Method");
//        } catch (ParameterNotFoundException e) {
//            fail("should find all request parameters");
//        } catch (Exception e) {
//            fail("should invoke properly");
//        }
//
//    }
//
//
//    @Test
//    public void should_throw_exception_if_method_parameter_names_not_match() {
//        try {
//            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
//            CommandHandlerInvoker commandHandlerInvoker = new CommandHandlerInvoker(handlerClass);
//            Method method = commandHandlerInvoker.locateMethod("GET");
//
//            Map<String, String> paramterMap = new Hashtable<String, String>();
//            paramterMap.put("username", "zhangkf");
//
//            commandHandlerInvoker.invokeHandler(method, paramterMap);
//            fail("should throw ParameterNotFoundException");
//
//        } catch (ClassNotFoundException e) {
//            fail("should find Handler Class");
//        } catch (MethodNotFoundException e) {
//            fail("should find Handler Method");
//        } catch (ParameterNotFoundException e) {
//
//        } catch (Exception e) {
//
//        }
//
//    }
//
//    @Test
//    public void should_throw_exception_if_parameter_type_not_match() {
//        try {
//            Class handlerClass = new CommandHandlerLocator(this.classes).locate("/sample");
//            CommandHandlerInvoker commandHandlerInvoker = new CommandHandlerInvoker(handlerClass);
//            Method method = commandHandlerInvoker.locateMethod("GET");
//
//            Map<String, Object> paramterMap = new Hashtable<String, Object>();
//            paramterMap.put("username", "zhangkf");
//            paramterMap.put("password", 12345d);
//
//            commandHandlerInvoker.invokeHandler(method, paramterMap);
//            fail("should throw ParameterTypeNotMatchException");
//
//        } catch (ClassNotFoundException e) {
//            fail("should find Handler Class");
//        } catch (MethodNotFoundException e) {
//            fail("should find Handler Method");
//        } catch (ParameterTypeNotMatchException e) {
//
//        } catch (Exception e) {
//
//        }
//
//    }


}
