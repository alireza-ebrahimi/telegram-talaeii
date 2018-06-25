package org.aspectj.internal.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.reflect.Advice;
import org.aspectj.lang.reflect.AdviceKind;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.PointcutExpression;

public class AdviceImpl implements Advice {
    private static final String AJC_INTERNAL = "org.aspectj.runtime.internal";
    private final Method adviceMethod;
    private AjType[] exceptionTypes;
    private Type[] genericParameterTypes;
    private boolean hasExtraParam = false;
    private final AdviceKind kind;
    private AjType[] parameterTypes;
    private PointcutExpression pointcutExpression;

    protected AdviceImpl(Method method, String pointcut, AdviceKind type) {
        this.kind = type;
        this.adviceMethod = method;
        this.pointcutExpression = new PointcutExpressionImpl(pointcut);
    }

    protected AdviceImpl(Method method, String pointcut, AdviceKind type, String extraParamName) {
        this(method, pointcut, type);
    }

    public AjType getDeclaringType() {
        return AjTypeSystem.getAjType(this.adviceMethod.getDeclaringClass());
    }

    public Type[] getGenericParameterTypes() {
        if (this.genericParameterTypes == null) {
            Type[] genTypes = this.adviceMethod.getGenericParameterTypes();
            int syntheticCount = 0;
            for (Type t : genTypes) {
                if ((t instanceof Class) && ((Class) t).getPackage().getName().equals(AJC_INTERNAL)) {
                    syntheticCount++;
                }
            }
            this.genericParameterTypes = new Type[(genTypes.length - syntheticCount)];
            for (int i = 0; i < this.genericParameterTypes.length; i++) {
                if (genTypes[i] instanceof Class) {
                    this.genericParameterTypes[i] = AjTypeSystem.getAjType((Class) genTypes[i]);
                } else {
                    this.genericParameterTypes[i] = genTypes[i];
                }
            }
        }
        return this.genericParameterTypes;
    }

    public AjType<?>[] getParameterTypes() {
        if (this.parameterTypes == null) {
            Class<?>[] ptypes = this.adviceMethod.getParameterTypes();
            int syntheticCount = 0;
            for (Class<?> c : ptypes) {
                if (c.getPackage().getName().equals(AJC_INTERNAL)) {
                    syntheticCount++;
                }
            }
            this.parameterTypes = new AjType[(ptypes.length - syntheticCount)];
            for (int i = 0; i < this.parameterTypes.length; i++) {
                this.parameterTypes[i] = AjTypeSystem.getAjType(ptypes[i]);
            }
        }
        return this.parameterTypes;
    }

    public AjType<?>[] getExceptionTypes() {
        if (this.exceptionTypes == null) {
            Class<?>[] exTypes = this.adviceMethod.getExceptionTypes();
            this.exceptionTypes = new AjType[exTypes.length];
            for (int i = 0; i < exTypes.length; i++) {
                this.exceptionTypes[i] = AjTypeSystem.getAjType(exTypes[i]);
            }
        }
        return this.exceptionTypes;
    }

    public AdviceKind getKind() {
        return this.kind;
    }

    public String getName() {
        String adviceName = this.adviceMethod.getName();
        if (!adviceName.startsWith("ajc$")) {
            return adviceName;
        }
        adviceName = "";
        AdviceName name = (AdviceName) this.adviceMethod.getAnnotation(AdviceName.class);
        if (name != null) {
            return name.value();
        }
        return adviceName;
    }

    public PointcutExpression getPointcutExpression() {
        return this.pointcutExpression;
    }

    public String toString() {
        int i;
        StringBuffer sb = new StringBuffer();
        if (getName().length() > 0) {
            sb.append("@AdviceName(\"");
            sb.append(getName());
            sb.append("\") ");
        }
        if (getKind() == AdviceKind.AROUND) {
            sb.append(this.adviceMethod.getGenericReturnType().toString());
            sb.append(" ");
        }
        switch (getKind()) {
            case AFTER:
                sb.append("after(");
                break;
            case AFTER_RETURNING:
                sb.append("after(");
                break;
            case AFTER_THROWING:
                sb.append("after(");
                break;
            case AROUND:
                sb.append("around(");
                break;
            case BEFORE:
                sb.append("before(");
                break;
        }
        AjType<?>[] ptypes = getParameterTypes();
        int len = ptypes.length;
        if (this.hasExtraParam) {
            len--;
        }
        for (i = 0; i < len; i++) {
            sb.append(ptypes[i].getName());
            if (i + 1 < len) {
                sb.append(",");
            }
        }
        sb.append(") ");
        switch (getKind()) {
            case AFTER_RETURNING:
                sb.append("returning");
                if (this.hasExtraParam) {
                    sb.append("(");
                    sb.append(ptypes[len - 1].getName());
                    sb.append(") ");
                    break;
                }
                break;
            case AFTER_THROWING:
                break;
        }
        sb.append("throwing");
        if (this.hasExtraParam) {
            sb.append("(");
            sb.append(ptypes[len - 1].getName());
            sb.append(") ");
        }
        AjType<?>[] exTypes = getExceptionTypes();
        if (exTypes.length > 0) {
            sb.append("throws ");
            for (i = 0; i < exTypes.length; i++) {
                sb.append(exTypes[i].getName());
                if (i + 1 < exTypes.length) {
                    sb.append(",");
                }
            }
            sb.append(" ");
        }
        sb.append(": ");
        sb.append(getPointcutExpression().asString());
        return sb.toString();
    }
}
