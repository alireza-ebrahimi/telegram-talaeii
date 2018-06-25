package org.aspectj.runtime.internal.cflowstack;

import java.util.Stack;

public class ThreadStackFactoryImpl implements ThreadStackFactory {

    /* renamed from: org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl$1 */
    static class C10061 {
    }

    private static class ThreadCounterImpl extends ThreadLocal implements ThreadCounter {

        static class Counter {
            protected int value = 0;

            Counter() {
            }
        }

        private ThreadCounterImpl() {
        }

        ThreadCounterImpl(C10061 x0) {
            this();
        }

        public Object initialValue() {
            return new Counter();
        }

        public Counter getThreadCounter() {
            return (Counter) get();
        }

        public void removeThreadCounter() {
            remove();
        }

        public void inc() {
            Counter threadCounter = getThreadCounter();
            threadCounter.value++;
        }

        public void dec() {
            Counter threadCounter = getThreadCounter();
            threadCounter.value--;
        }

        public boolean isNotZero() {
            return getThreadCounter().value != 0;
        }
    }

    private static class ThreadStackImpl extends ThreadLocal implements ThreadStack {
        private ThreadStackImpl() {
        }

        ThreadStackImpl(C10061 x0) {
            this();
        }

        public Object initialValue() {
            return new Stack();
        }

        public Stack getThreadStack() {
            return (Stack) get();
        }

        public void removeThreadStack() {
            remove();
        }
    }

    public ThreadStack getNewThreadStack() {
        return new ThreadStackImpl(null);
    }

    public ThreadCounter getNewThreadCounter() {
        return new ThreadCounterImpl(null);
    }
}
