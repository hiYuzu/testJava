import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * This program demonstrates the use of proxies
 *
 * @author Cay Horstmann
 * @version 1.00 2000-04-13
 */
public class ProxyTest {

    /**
     * An invocation handler that prints out the method name and parameters,
     * then invokes the original method
     */
    static class TraceHandler implements InvocationHandler {
        private Object target;

        /**
         * Constructs a TraceHandler
         */
        TraceHandler(Object t) {
            target = t;
        }

        @Override
        public Object invoke(Object proxy, Method m, Object[] args) {
            // print implicit argument
            System.out.print(target);
            // print method name
            System.out.print("." + m.getName() + "(");
            // print explicit argument
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    System.out.print(args[i]);
                    if (i < args.length - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println(")");

            Object object = null;
            try {
                // invoke actual method
                object = m.invoke(target, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return object;
        }
    }

    public static void main(String[] args) {
        Object[] elements = new Object[1000];

        // fill elements with proxies for the integers 1 ... 1000
        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
            elements[i] = proxy;
        }

        // construct a random integer
        Integer key = new Random().nextInt(elements.length) + 1;

        // search for the key
        int result = Arrays.binarySearch(elements, key);

        //print match if found
        if (result >= 0) {
            System.out.println(elements[result]);
        }
    }
}
