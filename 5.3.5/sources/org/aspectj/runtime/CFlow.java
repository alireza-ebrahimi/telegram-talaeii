package org.aspectj.runtime;

public class CFlow {
    private Object _aspect;

    public CFlow() {
        this(null);
    }

    public CFlow(Object _aspect) {
        this._aspect = _aspect;
    }

    public Object getAspect() {
        return this._aspect;
    }

    public void setAspect(Object _aspect) {
        this._aspect = _aspect;
    }

    public Object get(int index) {
        return null;
    }
}
