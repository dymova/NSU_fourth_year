package ru.nsu.ccfit.dymova;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Lab6_1 {
    public static void main(String[] args) throws CannotCompileException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.makeClass("MyClass");
        ctClass.addMethod(CtNewMethod.make("public String sayHello() {return \"Hello!\";}", ctClass));

        Object myClass = ctClass.toClass().newInstance();

        Method[] methods = myClass.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("sayHello") && method.getReturnType().equals(String.class)) {
                method.setAccessible(true);
                System.out.println(method.invoke(myClass));
            }
        }


    }
}
