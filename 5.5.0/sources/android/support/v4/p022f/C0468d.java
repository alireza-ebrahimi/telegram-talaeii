package android.support.v4.p022f;

/* renamed from: android.support.v4.f.d */
public class C0468d {
    /* renamed from: a */
    public static void m2014a(Object obj, StringBuilder stringBuilder) {
        if (obj == null) {
            stringBuilder.append("null");
            return;
        }
        String simpleName = obj.getClass().getSimpleName();
        if (simpleName == null || simpleName.length() <= 0) {
            simpleName = obj.getClass().getName();
            int lastIndexOf = simpleName.lastIndexOf(46);
            if (lastIndexOf > 0) {
                simpleName = simpleName.substring(lastIndexOf + 1);
            }
        }
        stringBuilder.append(simpleName);
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(obj)));
    }
}
