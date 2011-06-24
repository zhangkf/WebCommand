package com.thoughtworks.webcommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

class CommandHandlerFinder {

    private String packageName;

    CommandHandlerFinder(String packageName) {
        this.packageName = packageName;
    }

    Set<Class<?>> scanPackage() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return getClasses(loader);
    }

    private Set<Class<?>> getClasses(ClassLoader loader) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = loader.getResources(path);
        if (resources != null) {
            while (resources.hasMoreElements()) {
                String filePath = resources.nextElement().getFile();
                // WINDOWS HACK
                if (filePath.indexOf("%20") > 0)
                    filePath = filePath.replaceAll("%20", " ");
                if (isJarFilePath(filePath)) {
                    String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
                    // WINDOWS HACK
                    if (jarPath.contains(":"))
                        jarPath = jarPath.substring(1);
                    classes.addAll(getFromJARFile(jarPath, path));
                } else {
                    classes.addAll(getFromDirectory(new File(filePath), packageName));
                }
            }
        }
        return classes;
    }

    private boolean isJarFilePath(String filePath) {
        return (filePath.indexOf("!") > 0) & (filePath.indexOf(".jar") > 0);
    }

    private Set<Class<?>> getFromDirectory(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String name = packageName + '.' + stripFilenameExtension(fileName);
                    Class<?> clazz = Class.forName(name);
                    classes.add(clazz);
                } else if(file.isDirectory()){
                    classes.addAll(getFromDirectory(file, packageName+"."+fileName));
                }
            }
        }
        return classes;
    }

    private Set<Class<?>> getFromJARFile(String jar, String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jar));
        JarEntry jarEntry;
        do {
            jarEntry = jarFile.getNextJarEntry();
            if (jarEntry != null) {
                String className = jarEntry.getName();
                if (className.endsWith(".class")) {
                    className = stripFilenameExtension(className);
                    if (className.startsWith(packageName))
                        classes.add(Class.forName(className.replace('/', '.')));
                }
            }
        } while (jarEntry != null);
        return classes;
    }

    private static String stripFilenameExtension(String className) {
        return className.substring(0, className.length() - ".class".length());
    }

}
