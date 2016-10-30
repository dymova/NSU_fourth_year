package ru.nsu.ccfit.dymova;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static final String USAGE = "usage: java ru.nsu.ccfit.dymova.Main <pathToDirectory>";

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (args.length != 1) {
            throw new IllegalArgumentException(USAGE);
        }

        Path path = Paths.get(args[0]);

        CustomClassLoader customClassLoader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), path);

        List<Class<?>> classes = customClassLoader.loadClasses();

        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals("getSecurityMessage") && method.getReturnType().equals(String.class)) {
                    Object o = aClass.newInstance();
                    method.setAccessible(true);
                    System.out.println(method.invoke(o));
                }
            }
        }

    }


}



