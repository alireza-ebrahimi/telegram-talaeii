package com.google.p098a.p100b;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* renamed from: com.google.a.b.b */
public final class C1713b {
    /* renamed from: a */
    static final Type[] f5264a = new Type[0];

    /* renamed from: com.google.a.b.b$a */
    private static final class C1710a implements Serializable, GenericArrayType {
        /* renamed from: a */
        private final Type f5258a;

        public C1710a(Type type) {
            this.f5258a = C1713b.m8293d(type);
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && C1713b.m8287a((Type) this, (GenericArrayType) obj);
        }

        public Type getGenericComponentType() {
            return this.f5258a;
        }

        public int hashCode() {
            return this.f5258a.hashCode();
        }

        public String toString() {
            return C1713b.m8295f(this.f5258a) + "[]";
        }
    }

    /* renamed from: com.google.a.b.b$b */
    private static final class C1711b implements Serializable, ParameterizedType {
        /* renamed from: a */
        private final Type f5259a;
        /* renamed from: b */
        private final Type f5260b;
        /* renamed from: c */
        private final Type[] f5261c;

        public C1711b(Type type, Type type2, Type... typeArr) {
            int i = 0;
            if (type2 instanceof Class) {
                Class cls = (Class) type2;
                int i2 = (Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null) ? 1 : 0;
                boolean z = (type == null && i2 == 0) ? false : true;
                C1709a.m8276a(z);
            }
            this.f5259a = type == null ? null : C1713b.m8293d(type);
            this.f5260b = C1713b.m8293d(type2);
            this.f5261c = (Type[]) typeArr.clone();
            while (i < this.f5261c.length) {
                C1709a.m8275a(this.f5261c[i]);
                C1713b.m8298i(this.f5261c[i]);
                this.f5261c[i] = C1713b.m8293d(this.f5261c[i]);
                i++;
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && C1713b.m8287a((Type) this, (ParameterizedType) obj);
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.f5261c.clone();
        }

        public Type getOwnerType() {
            return this.f5259a;
        }

        public Type getRawType() {
            return this.f5260b;
        }

        public int hashCode() {
            return (Arrays.hashCode(this.f5261c) ^ this.f5260b.hashCode()) ^ C1713b.m8288b(this.f5259a);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.f5261c.length + 1) * 30);
            stringBuilder.append(C1713b.m8295f(this.f5260b));
            if (this.f5261c.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append(C1713b.m8295f(this.f5261c[0]));
            for (int i = 1; i < this.f5261c.length; i++) {
                stringBuilder.append(", ").append(C1713b.m8295f(this.f5261c[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    /* renamed from: com.google.a.b.b$c */
    private static final class C1712c implements Serializable, WildcardType {
        /* renamed from: a */
        private final Type f5262a;
        /* renamed from: b */
        private final Type f5263b;

        public C1712c(Type[] typeArr, Type[] typeArr2) {
            boolean z = true;
            C1709a.m8276a(typeArr2.length <= 1);
            C1709a.m8276a(typeArr.length == 1);
            if (typeArr2.length == 1) {
                C1709a.m8275a(typeArr2[0]);
                C1713b.m8298i(typeArr2[0]);
                if (typeArr[0] != Object.class) {
                    z = false;
                }
                C1709a.m8276a(z);
                this.f5263b = C1713b.m8293d(typeArr2[0]);
                this.f5262a = Object.class;
                return;
            }
            C1709a.m8275a(typeArr[0]);
            C1713b.m8298i(typeArr[0]);
            this.f5263b = null;
            this.f5262a = C1713b.m8293d(typeArr[0]);
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && C1713b.m8287a((Type) this, (WildcardType) obj);
        }

        public Type[] getLowerBounds() {
            if (this.f5263b == null) {
                return C1713b.f5264a;
            }
            return new Type[]{this.f5263b};
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.f5262a};
        }

        public int hashCode() {
            return (this.f5263b != null ? this.f5263b.hashCode() + 31 : 1) ^ (this.f5262a.hashCode() + 31);
        }

        public String toString() {
            return this.f5263b != null ? "? super " + C1713b.m8295f(this.f5263b) : this.f5262a == Object.class ? "?" : "? extends " + C1713b.m8295f(this.f5262a);
        }
    }

    /* renamed from: a */
    private static int m8278a(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    /* renamed from: a */
    private static Class<?> m8279a(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return genericDeclaration instanceof Class ? (Class) genericDeclaration : null;
    }

    /* renamed from: a */
    public static GenericArrayType m8280a(Type type) {
        return new C1710a(type);
    }

    /* renamed from: a */
    public static ParameterizedType m8281a(Type type, Type type2, Type... typeArr) {
        return new C1711b(type, type2, typeArr);
    }

    /* renamed from: a */
    public static Type m8282a(Type type, Class<?> cls) {
        Type b = C1713b.m8289b(type, cls, Collection.class);
        if (b instanceof WildcardType) {
            b = ((WildcardType) b).getUpperBounds()[0];
        }
        return b instanceof ParameterizedType ? ((ParameterizedType) b).getActualTypeArguments()[0] : Object.class;
    }

    /* renamed from: a */
    static Type m8283a(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return C1713b.m8283a(cls.getGenericInterfaces()[i], interfaces[i], (Class) cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            Class cls3;
            while (cls3 != Object.class) {
                Class superclass = cls3.getSuperclass();
                if (superclass == cls2) {
                    return cls3.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return C1713b.m8283a(cls3.getGenericSuperclass(), superclass, (Class) cls2);
                }
                cls3 = superclass;
            }
        }
        return cls2;
    }

    /* renamed from: a */
    public static Type m8284a(Type type, Class<?> cls, Type type2) {
        Type type3 = type2;
        while (type3 instanceof TypeVariable) {
            type3 = (TypeVariable) type3;
            type2 = C1713b.m8285a(type, (Class) cls, (TypeVariable) type3);
            if (type2 == type3) {
                return type2;
            }
            type3 = type2;
        }
        Type componentType;
        Type a;
        if ((type3 instanceof Class) && ((Class) type3).isArray()) {
            Class cls2 = (Class) type3;
            componentType = cls2.getComponentType();
            a = C1713b.m8284a(type, (Class) cls, componentType);
            return componentType != a ? C1713b.m8280a(a) : cls2;
        } else if (type3 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type3;
            componentType = genericArrayType.getGenericComponentType();
            a = C1713b.m8284a(type, (Class) cls, componentType);
            return componentType != a ? C1713b.m8280a(a) : genericArrayType;
        } else if (type3 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type3;
            componentType = parameterizedType.getOwnerType();
            Type a2 = C1713b.m8284a(type, (Class) cls, componentType);
            int i = a2 != componentType ? 1 : 0;
            r4 = parameterizedType.getActualTypeArguments();
            int length = r4.length;
            int i2 = i;
            r1 = r4;
            for (int i3 = 0; i3 < length; i3++) {
                Type a3 = C1713b.m8284a(type, (Class) cls, r1[i3]);
                if (a3 != r1[i3]) {
                    if (i2 == 0) {
                        r1 = (Type[]) r1.clone();
                        i2 = 1;
                    }
                    r1[i3] = a3;
                }
            }
            return i2 != 0 ? C1713b.m8281a(a2, parameterizedType.getRawType(), r1) : parameterizedType;
        } else if (!(type3 instanceof WildcardType)) {
            return type3;
        } else {
            WildcardType wildcardType = (WildcardType) type3;
            r1 = wildcardType.getLowerBounds();
            r4 = wildcardType.getUpperBounds();
            if (r1.length == 1) {
                a = C1713b.m8284a(type, (Class) cls, r1[0]);
                return a != r1[0] ? C1713b.m8292c(a) : wildcardType;
            } else if (r4.length != 1) {
                return wildcardType;
            } else {
                componentType = C1713b.m8284a(type, (Class) cls, r4[0]);
                return componentType != r4[0] ? C1713b.m8290b(componentType) : wildcardType;
            }
        }
    }

    /* renamed from: a */
    static Type m8285a(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class a = C1713b.m8279a((TypeVariable) typeVariable);
        if (a == null) {
            return typeVariable;
        }
        Type a2 = C1713b.m8283a(type, (Class) cls, a);
        if (!(a2 instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) a2).getActualTypeArguments()[C1713b.m8278a(a.getTypeParameters(), (Object) typeVariable)];
    }

    /* renamed from: a */
    static boolean m8286a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* renamed from: a */
    public static boolean m8287a(Type type, Type type2) {
        boolean z = true;
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            if (!(C1713b.m8286a(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments()))) {
                z = false;
            }
            return z;
        } else if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            return C1713b.m8287a(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
        } else if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            if (!(Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds()))) {
                z = false;
            }
            return z;
        } else if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            if (!(typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName()))) {
                z = false;
            }
            return z;
        }
    }

    /* renamed from: b */
    private static int m8288b(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    /* renamed from: b */
    static Type m8289b(Type type, Class<?> cls, Class<?> cls2) {
        C1709a.m8276a(cls2.isAssignableFrom(cls));
        return C1713b.m8284a(type, (Class) cls, C1713b.m8283a(type, (Class) cls, (Class) cls2));
    }

    /* renamed from: b */
    public static WildcardType m8290b(Type type) {
        return new C1712c(new Type[]{type}, f5264a);
    }

    /* renamed from: b */
    public static Type[] m8291b(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type b = C1713b.m8289b(type, cls, Map.class);
        if (b instanceof ParameterizedType) {
            return ((ParameterizedType) b).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    /* renamed from: c */
    public static WildcardType m8292c(Type type) {
        return new C1712c(new Type[]{Object.class}, new Type[]{type});
    }

    /* renamed from: d */
    public static Type m8293d(Type type) {
        if (type instanceof Class) {
            C1710a c1710a;
            Class cls = (Class) type;
            if (cls.isArray()) {
                c1710a = new C1710a(C1713b.m8293d(cls.getComponentType()));
            } else {
                Object obj = cls;
            }
            return c1710a;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new C1711b(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new C1710a(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (!(type instanceof WildcardType)) {
                return type;
            }
            WildcardType wildcardType = (WildcardType) type;
            return new C1712c(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
    }

    /* renamed from: e */
    public static Class<?> m8294e(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            C1709a.m8276a(rawType instanceof Class);
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(C1713b.m8294e(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return C1713b.m8294e(((WildcardType) type).getUpperBounds()[0]);
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
        }
    }

    /* renamed from: f */
    public static String m8295f(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    /* renamed from: g */
    public static Type m8296g(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }

    /* renamed from: i */
    private static void m8298i(Type type) {
        boolean z = ((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true;
        C1709a.m8276a(z);
    }
}
