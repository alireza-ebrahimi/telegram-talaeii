package com.onesignal;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class OSObservable<ObserverType, StateType> {
    private boolean fireOnMainThread;
    private String methodName;
    private List<Object> observers = new ArrayList();

    OSObservable(String methodName, boolean fireOnMainThread) {
        this.methodName = methodName;
        this.fireOnMainThread = fireOnMainThread;
    }

    void addObserver(ObserverType observer) {
        this.observers.add(new WeakReference(observer));
    }

    void addObserverStrong(ObserverType observer) {
        this.observers.add(observer);
    }

    void removeObserver(ObserverType observer) {
        for (int i = 0; i < this.observers.size(); i++) {
            if (((WeakReference) this.observers.get(i)).get().equals(observer)) {
                this.observers.remove(i);
                return;
            }
        }
    }

    boolean notifyChange(final StateType state) {
        boolean notified = false;
        for (Object observer : this.observers) {
            Object strongRefObserver;
            if (observer instanceof WeakReference) {
                strongRefObserver = ((WeakReference) observer).get();
            } else {
                strongRefObserver = observer;
            }
            if (strongRefObserver != null) {
                try {
                    final Method method = strongRefObserver.getClass().getDeclaredMethod(this.methodName, new Class[]{state.getClass()});
                    method.setAccessible(true);
                    if (this.fireOnMainThread) {
                        OSUtils.runOnMainUIThread(new Runnable() {
                            public void run() {
                                try {
                                    method.invoke(strongRefObserver, new Object[]{state});
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        });
                    } else {
                        method.invoke(strongRefObserver, new Object[]{state});
                    }
                    notified = true;
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        return notified;
    }
}
