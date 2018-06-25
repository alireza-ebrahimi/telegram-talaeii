package org.telegram.customization.dynamicadapter;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1771l;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;
import org.telegram.customization.dynamicadapter.data.ObjBase;

public class GsonAdapterFactory implements C1668x {

    private static class ObjBaseTypeAdapter extends C1670w<ObjBase> {
        private final C1768f gson;
        private final GsonAdapterFactory gsonAdapterFactory;
        private final C1670w<C1771l> jsonElementAdapter;

        ObjBaseTypeAdapter(GsonAdapterFactory gsonAdapterFactory, C1768f c1768f, C1670w<C1771l> c1670w) {
            this.jsonElementAdapter = c1670w;
            this.gson = c1768f;
            this.gsonAdapterFactory = gsonAdapterFactory;
        }

        public ObjBase read(C1678a c1678a) {
            C1771l k = ((C1771l) this.jsonElementAdapter.read(c1678a)).m8415k();
            try {
                return (ObjBase) this.gson.m8388a(this.gsonAdapterFactory, C1748a.m8358b(Helper.getModel(k.m8426a(Param.TYPE).mo1296e()))).fromJsonTree(k);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void write(C1681c c1681c, ObjBase objBase) {
        }
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        return !ObjBase.class.isAssignableFrom(c1748a.m8359a()) ? null : new ObjBaseTypeAdapter(this, c1768f, c1768f.m8389a(C1771l.class)).nullSafe();
    }
}
