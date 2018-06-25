package org.aspectj.runtime.reflect;

import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ClassUtils;

class StringMaker {
    static StringMaker longStringMaker = new StringMaker();
    static StringMaker middleStringMaker = new StringMaker();
    static StringMaker shortStringMaker = new StringMaker();
    int cacheOffset;
    boolean includeArgs = true;
    boolean includeEnclosingPoint = true;
    boolean includeJoinPointTypeName = true;
    boolean includeModifiers = false;
    boolean includeThrows = false;
    boolean shortKindName = true;
    boolean shortPrimaryTypeNames = false;
    boolean shortTypeNames = true;

    StringMaker() {
    }

    static {
        shortStringMaker.shortTypeNames = true;
        shortStringMaker.includeArgs = false;
        shortStringMaker.includeThrows = false;
        shortStringMaker.includeModifiers = false;
        shortStringMaker.shortPrimaryTypeNames = true;
        shortStringMaker.includeJoinPointTypeName = false;
        shortStringMaker.includeEnclosingPoint = false;
        shortStringMaker.cacheOffset = 0;
        middleStringMaker.shortTypeNames = true;
        middleStringMaker.includeArgs = true;
        middleStringMaker.includeThrows = false;
        middleStringMaker.includeModifiers = false;
        middleStringMaker.shortPrimaryTypeNames = false;
        shortStringMaker.cacheOffset = 1;
        longStringMaker.shortTypeNames = false;
        longStringMaker.includeArgs = true;
        longStringMaker.includeThrows = false;
        longStringMaker.includeModifiers = true;
        longStringMaker.shortPrimaryTypeNames = false;
        longStringMaker.shortKindName = false;
        longStringMaker.cacheOffset = 2;
    }

    String makeKindName(String name) {
        int dash = name.lastIndexOf(45);
        return dash == -1 ? name : name.substring(dash + 1);
    }

    String makeModifiersString(int modifiers) {
        if (!this.includeModifiers) {
            return "";
        }
        String str = Modifier.toString(modifiers);
        if (str.length() == 0) {
            return "";
        }
        return new StringBuffer().append(str).append(" ").toString();
    }

    String stripPackageName(String name) {
        int dot = name.lastIndexOf(46);
        return dot == -1 ? name : name.substring(dot + 1);
    }

    String makeTypeName(Class type, String typeName, boolean shortName) {
        if (type == null) {
            return "ANONYMOUS";
        }
        if (type.isArray()) {
            Class componentType = type.getComponentType();
            return new StringBuffer().append(makeTypeName(componentType, componentType.getName(), shortName)).append("[]").toString();
        } else if (shortName) {
            return stripPackageName(typeName).replace('$', ClassUtils.PACKAGE_SEPARATOR_CHAR);
        } else {
            return typeName.replace('$', ClassUtils.PACKAGE_SEPARATOR_CHAR);
        }
    }

    public String makeTypeName(Class type) {
        return makeTypeName(type, type.getName(), this.shortTypeNames);
    }

    public String makePrimaryTypeName(Class type, String typeName) {
        return makeTypeName(type, typeName, this.shortPrimaryTypeNames);
    }

    public void addTypeNames(StringBuffer buf, Class[] types) {
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(makeTypeName(types[i]));
        }
    }

    public void addSignature(StringBuffer buf, Class[] types) {
        if (types != null) {
            if (this.includeArgs) {
                buf.append("(");
                addTypeNames(buf, types);
                buf.append(")");
            } else if (types.length == 0) {
                buf.append("()");
            } else {
                buf.append("(..)");
            }
        }
    }

    public void addThrows(StringBuffer buf, Class[] types) {
        if (this.includeThrows && types != null && types.length != 0) {
            buf.append(" throws ");
            addTypeNames(buf, types);
        }
    }
}
