package utils;

import java.util.ArrayList;
import java.util.Collections;
import org.telegram.customization.Model.RequestLog;

/* renamed from: utils.a */
public class C5313a {
    /* renamed from: a */
    private static volatile ArrayList<RequestLog> f10220a = new ArrayList();

    /* renamed from: a */
    public static ArrayList<RequestLog> m14122a() {
        Collections.reverse(f10220a);
        return f10220a;
    }
}
