package ru.nsu.ccfit.dymova;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

public class Lab4 {

    static Calculator createCalculator() throws Exception {
        ProxyFactory f = new ProxyFactory();
        f.setSuperclass(Calculator.class);
        Class c = f.createClass();
        MethodHandler mi = (self, m, proceed, args) -> {
            if ("sum".equals(m.getName())) {
                int result = (int) proceed.invoke(self, args);
                return result + 1;
            }
            return proceed.invoke(self, args);
        };
        Calculator calculator = (Calculator) c.newInstance();
        ((Proxy) calculator).setHandler(mi);
        return calculator;
    }

    public static void main(String[] args) throws Exception {
        Calculator cal = createCalculator();
        System.out.println("2 + 2 = " + cal.sum(2, 2));
    }
}
