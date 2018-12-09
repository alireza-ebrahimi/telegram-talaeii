package com.google.android.gms.common.server;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DeviceProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseApi {

    public static abstract class BaseApiaryOptions<DerivedClassType extends BaseApiaryOptions<DerivedClassType>> {
        private final ArrayList<String> zzvt = new ArrayList();
        private final HashMap<String, String> zzvu = new HashMap();
        private String zzvv;
        private final Collector zzvw = new Collector(this);

        public final class Collector {
            private boolean zzvx;
            private boolean zzvy;
            private int zzvz;
            private StringBuilder zzwa = new StringBuilder();
            private final /* synthetic */ BaseApiaryOptions zzwb;

            public Collector(BaseApiaryOptions baseApiaryOptions) {
                this.zzwb = baseApiaryOptions;
            }

            private final void append(String str) {
                if (this.zzvx) {
                    this.zzvx = false;
                    this.zzwa.append(",");
                } else if (this.zzvy) {
                    this.zzvy = false;
                    this.zzwa.append("/");
                }
                this.zzwa.append(str);
            }

            public final void addPiece(String str) {
                append(str);
                this.zzvy = true;
            }

            public final void beginSubCollection(String str) {
                append(str);
                this.zzwa.append("(");
                this.zzvz++;
            }

            public final void endSubCollection() {
                this.zzwa.append(")");
                this.zzvz--;
                if (this.zzvz == 0) {
                    this.zzwb.addField(this.zzwa.toString());
                    this.zzwa.setLength(0);
                    this.zzvx = false;
                    this.zzvy = false;
                    return;
                }
                this.zzvx = true;
            }

            public final void finishPiece(String str) {
                append(str);
                if (this.zzvz == 0) {
                    this.zzwb.addField(this.zzwa.toString());
                    this.zzwa.setLength(0);
                    return;
                }
                this.zzvx = true;
            }
        }

        private static String zzcy() {
            return String.valueOf(!DeviceProperties.isUserBuild());
        }

        public final DerivedClassType addField(String str) {
            this.zzvt.add(str);
            return this;
        }

        @Deprecated
        public final String appendParametersToUrl(String str) {
            String append = BaseApi.append(str, "prettyPrint", zzcy());
            if (this.zzvv != null) {
                append = BaseApi.append(append, "trace", getTrace());
            }
            return !this.zzvt.isEmpty() ? BaseApi.append(append, "fields", TextUtils.join(",", getFields().toArray())) : append;
        }

        public void appendParametersToUrl(StringBuilder stringBuilder) {
            BaseApi.append(stringBuilder, "prettyPrint", zzcy());
            if (this.zzvv != null) {
                BaseApi.append(stringBuilder, "trace", getTrace());
            }
            if (!this.zzvt.isEmpty()) {
                BaseApi.append(stringBuilder, "fields", TextUtils.join(",", getFields().toArray()));
            }
        }

        public final DerivedClassType buildFrom(BaseApiaryOptions<?> baseApiaryOptions) {
            if (baseApiaryOptions.zzvv != null) {
                this.zzvv = baseApiaryOptions.zzvv;
            }
            if (!baseApiaryOptions.zzvt.isEmpty()) {
                this.zzvt.clear();
                this.zzvt.addAll(baseApiaryOptions.zzvt);
            }
            return this;
        }

        protected final Collector getCollector() {
            return this.zzvw;
        }

        public final List<String> getFields() {
            return this.zzvt;
        }

        public final Map<String, String> getHeaders() {
            return this.zzvu;
        }

        public final String getTrace() {
            return this.zzvv;
        }

        public final DerivedClassType setEtag(String str) {
            return setHeader("ETag", str);
        }

        public final DerivedClassType setHeader(String str, String str2) {
            this.zzvu.put(str, str2);
            return this;
        }

        public final DerivedClassType setTraceByLdap(String str) {
            String valueOf = String.valueOf("email:");
            String valueOf2 = String.valueOf(str);
            this.zzvv = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            return this;
        }

        public final DerivedClassType setTraceByToken(String str) {
            String valueOf = String.valueOf("token:");
            String valueOf2 = String.valueOf(str);
            this.zzvv = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            return this;
        }
    }

    public static class FieldCollection<Parent> {
        private final Collector zzvw;
        private final Parent zzwc;

        protected FieldCollection(Parent parent, Collector collector) {
            Object obj;
            if (parent == null) {
                obj = this;
            }
            this.zzwc = obj;
            this.zzvw = collector;
        }

        protected Collector getCollector() {
            return this.zzvw;
        }

        protected Parent getParent() {
            return this.zzwc;
        }
    }

    @Deprecated
    public static String append(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        if (str.indexOf("?") != -1) {
            stringBuilder.append('&');
        } else {
            stringBuilder.append('?');
        }
        stringBuilder.append(str2);
        stringBuilder.append('=');
        stringBuilder.append(str3);
        return stringBuilder.toString();
    }

    public static void append(StringBuilder stringBuilder, String str, String str2) {
        if (stringBuilder.indexOf("?") != -1) {
            stringBuilder.append('&');
        } else {
            stringBuilder.append('?');
        }
        stringBuilder.append(str);
        stringBuilder.append('=');
        stringBuilder.append(str2);
    }

    public static String enc(String str) {
        Preconditions.checkNotNull(str, "Encoding a null parameter!");
        return Uri.encode(str);
    }

    protected static List<String> enc(List<String> list) {
        int size = list.size();
        List arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(enc((String) list.get(i)));
        }
        return arrayList;
    }
}
