package org.aspectj.runtime.reflect;

import org.aspectj.lang.JoinPoint.EnclosingStaticPart;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

class JoinPointImpl implements ProceedingJoinPoint {
    Object _this;
    private AroundClosure arc;
    Object[] args;
    StaticPart staticPart;
    Object target;

    static class StaticPartImpl implements StaticPart {
        private int id;
        String kind;
        Signature signature;
        SourceLocation sourceLocation;

        public StaticPartImpl(int id, String kind, Signature signature, SourceLocation sourceLocation) {
            this.kind = kind;
            this.signature = signature;
            this.sourceLocation = sourceLocation;
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public String getKind() {
            return this.kind;
        }

        public Signature getSignature() {
            return this.signature;
        }

        public SourceLocation getSourceLocation() {
            return this.sourceLocation;
        }

        String toString(StringMaker sm) {
            StringBuffer buf = new StringBuffer();
            buf.append(sm.makeKindName(getKind()));
            buf.append("(");
            buf.append(((SignatureImpl) getSignature()).toString(sm));
            buf.append(")");
            return buf.toString();
        }

        public final String toString() {
            return toString(StringMaker.middleStringMaker);
        }

        public final String toShortString() {
            return toString(StringMaker.shortStringMaker);
        }

        public final String toLongString() {
            return toString(StringMaker.longStringMaker);
        }
    }

    static class EnclosingStaticPartImpl extends StaticPartImpl implements EnclosingStaticPart {
        public EnclosingStaticPartImpl(int count, String kind, Signature signature, SourceLocation sourceLocation) {
            super(count, kind, signature, sourceLocation);
        }
    }

    public JoinPointImpl(StaticPart staticPart, Object _this, Object target, Object[] args) {
        this.staticPart = staticPart;
        this._this = _this;
        this.target = target;
        this.args = args;
    }

    public Object getThis() {
        return this._this;
    }

    public Object getTarget() {
        return this.target;
    }

    public Object[] getArgs() {
        if (this.args == null) {
            this.args = new Object[0];
        }
        Object[] argsCopy = new Object[this.args.length];
        System.arraycopy(this.args, 0, argsCopy, 0, this.args.length);
        return argsCopy;
    }

    public StaticPart getStaticPart() {
        return this.staticPart;
    }

    public String getKind() {
        return this.staticPart.getKind();
    }

    public Signature getSignature() {
        return this.staticPart.getSignature();
    }

    public SourceLocation getSourceLocation() {
        return this.staticPart.getSourceLocation();
    }

    public final String toString() {
        return this.staticPart.toString();
    }

    public final String toShortString() {
        return this.staticPart.toShortString();
    }

    public final String toLongString() {
        return this.staticPart.toLongString();
    }

    public void set$AroundClosure(AroundClosure arc) {
        this.arc = arc;
    }

    public Object proceed() throws Throwable {
        if (this.arc == null) {
            return null;
        }
        return this.arc.run(this.arc.getState());
    }

    public Object proceed(Object[] adviceBindings) throws Throwable {
        int i = 1;
        if (this.arc == null) {
            return null;
        }
        boolean thisTargetTheSame;
        boolean hasThis;
        boolean bindsThis;
        boolean hasTarget;
        int i2;
        int flags = this.arc.getFlags();
        boolean unset;
        if ((1048576 & flags) != 0) {
            unset = true;
        } else {
            unset = false;
        }
        if ((65536 & flags) != 0) {
            thisTargetTheSame = true;
        } else {
            thisTargetTheSame = false;
        }
        if ((flags & 4096) != 0) {
            hasThis = true;
        } else {
            hasThis = false;
        }
        if ((flags & 256) != 0) {
            bindsThis = true;
        } else {
            bindsThis = false;
        }
        if ((flags & 16) != 0) {
            hasTarget = true;
        } else {
            hasTarget = false;
        }
        boolean bindsTarget;
        if ((flags & 1) != 0) {
            bindsTarget = true;
        } else {
            bindsTarget = false;
        }
        Object[] state = this.arc.getState();
        int firstArgumentIndexIntoAdviceBindings = 0;
        if (hasThis) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        int firstArgumentIndexIntoState = 0 + i2;
        if (!hasTarget || thisTargetTheSame) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        firstArgumentIndexIntoState += i2;
        if (hasThis && bindsThis) {
            firstArgumentIndexIntoAdviceBindings = 1;
            state[0] = adviceBindings[0];
        }
        if (hasTarget && bindsTarget) {
            if (thisTargetTheSame) {
                firstArgumentIndexIntoAdviceBindings = (bindsThis ? 1 : 0) + 1;
                if (!bindsThis) {
                    i = 0;
                }
                state[0] = adviceBindings[i];
            } else {
                if (hasThis) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                firstArgumentIndexIntoAdviceBindings = i2 + 1;
                if (hasThis) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                if (!hasThis) {
                    i = 0;
                }
                state[i2] = adviceBindings[i];
            }
        }
        for (int i3 = firstArgumentIndexIntoAdviceBindings; i3 < adviceBindings.length; i3++) {
            state[(i3 - firstArgumentIndexIntoAdviceBindings) + firstArgumentIndexIntoState] = adviceBindings[i3];
        }
        return this.arc.run(state);
    }
}
