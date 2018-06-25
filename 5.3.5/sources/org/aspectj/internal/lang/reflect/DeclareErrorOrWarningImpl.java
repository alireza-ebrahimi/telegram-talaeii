package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.DeclareErrorOrWarning;
import org.aspectj.lang.reflect.PointcutExpression;

public class DeclareErrorOrWarningImpl implements DeclareErrorOrWarning {
    private AjType declaringType;
    private boolean isError;
    private String msg;
    private PointcutExpression pc;

    public DeclareErrorOrWarningImpl(String pointcut, String message, boolean isError, AjType decType) {
        this.pc = new PointcutExpressionImpl(pointcut);
        this.msg = message;
        this.isError = isError;
        this.declaringType = decType;
    }

    public AjType getDeclaringType() {
        return this.declaringType;
    }

    public PointcutExpression getPointcutExpression() {
        return this.pc;
    }

    public String getMessage() {
        return this.msg;
    }

    public boolean isError() {
        return this.isError;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("declare ");
        sb.append(isError() ? "error : " : "warning : ");
        sb.append(getPointcutExpression().asString());
        sb.append(" : ");
        sb.append("\"");
        sb.append(getMessage());
        sb.append("\"");
        return sb.toString();
    }
}
