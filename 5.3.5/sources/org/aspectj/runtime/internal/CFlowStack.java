package org.aspectj.runtime.internal;

import io.fabric.sdk.android.services.common.IdManager;
import java.util.Stack;
import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.runtime.CFlow;
import org.aspectj.runtime.internal.cflowstack.ThreadStack;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactory;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl11;

public class CFlowStack {
    private static ThreadStackFactory tsFactory;
    private ThreadStack stackProxy = tsFactory.getNewThreadStack();

    static {
        selectFactoryForVMVersion();
    }

    private Stack getThreadStack() {
        return this.stackProxy.getThreadStack();
    }

    public void push(Object obj) {
        getThreadStack().push(obj);
    }

    public void pushInstance(Object obj) {
        getThreadStack().push(new CFlow(obj));
    }

    public void push(Object[] obj) {
        getThreadStack().push(new CFlowPlusState(obj));
    }

    public void pop() {
        Stack s = getThreadStack();
        s.pop();
        if (s.isEmpty()) {
            this.stackProxy.removeThreadStack();
        }
    }

    public Object peek() {
        Stack stack = getThreadStack();
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        throw new NoAspectBoundException();
    }

    public Object get(int index) {
        CFlow cf = peekCFlow();
        return cf == null ? null : cf.get(index);
    }

    public Object peekInstance() {
        CFlow cf = peekCFlow();
        if (cf != null) {
            return cf.getAspect();
        }
        throw new NoAspectBoundException();
    }

    public CFlow peekCFlow() {
        Stack stack = getThreadStack();
        if (stack.isEmpty()) {
            return null;
        }
        return (CFlow) stack.peek();
    }

    public CFlow peekTopCFlow() {
        Stack stack = getThreadStack();
        if (stack.isEmpty()) {
            return null;
        }
        return (CFlow) stack.elementAt(0);
    }

    public boolean isValid() {
        return !getThreadStack().isEmpty();
    }

    private static ThreadStackFactory getThreadLocalStackFactory() {
        return new ThreadStackFactoryImpl();
    }

    private static ThreadStackFactory getThreadLocalStackFactoryFor11() {
        return new ThreadStackFactoryImpl11();
    }

    private static void selectFactoryForVMVersion() {
        boolean useThreadLocalImplementation;
        String override = getSystemPropertyWithoutSecurityException("aspectj.runtime.cflowstack.usethreadlocal", "unspecified");
        if (!override.equals("unspecified")) {
            useThreadLocalImplementation = override.equals("yes") || override.equals("true");
        } else if (System.getProperty("java.class.version", IdManager.DEFAULT_VERSION_NAME).compareTo("46.0") >= 0) {
            useThreadLocalImplementation = true;
        } else {
            useThreadLocalImplementation = false;
        }
        if (useThreadLocalImplementation) {
            tsFactory = getThreadLocalStackFactory();
        } else {
            tsFactory = getThreadLocalStackFactoryFor11();
        }
    }

    private static String getSystemPropertyWithoutSecurityException(String aPropertyName, String aDefaultValue) {
        try {
            aDefaultValue = System.getProperty(aPropertyName, aDefaultValue);
        } catch (SecurityException e) {
        }
        return aDefaultValue;
    }

    public static String getThreadStackFactoryClassName() {
        return tsFactory.getClass().getName();
    }
}
