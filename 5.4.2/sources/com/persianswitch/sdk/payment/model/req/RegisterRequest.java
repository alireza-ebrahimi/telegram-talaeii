package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import java.util.HashMap;
import org.json.JSONObject;

public class RegisterRequest extends AbsRequest {
    /* renamed from: a */
    private String f7602a;
    /* renamed from: b */
    private String f7603b;
    /* renamed from: c */
    private String f7604c;
    /* renamed from: d */
    private String f7605d;
    /* renamed from: e */
    private int f7606e;
    /* renamed from: f */
    private String f7607f;
    /* renamed from: g */
    private boolean f7608g;
    /* renamed from: h */
    private String f7609h;
    /* renamed from: i */
    private String f7610i;
    /* renamed from: j */
    private String f7611j;
    /* renamed from: k */
    private String f7612k;

    public RegisterRequest() {
        super(OpCode.REGISTER_APPLICATION);
    }

    RegisterRequest(OpCode opCode) {
        super(opCode);
    }

    /* renamed from: p */
    private String m11322p() {
        return (StringUtils.m10803a(this.f7612k) || StringUtils.m10803a(this.f7611j)) ? "3;" : "1;" + this.f7611j;
    }

    /* renamed from: a */
    public IWebServiceDescriptor mo3304a(final Context context) {
        return new IWebServiceDescriptor(this) {
            /* renamed from: b */
            final /* synthetic */ RegisterRequest f7614b;

            /* renamed from: a */
            public WSRequest mo3300a() {
                WSRequest a = super.mo3304a(context).mo3300a();
                a.m10928d(this.f7614b.f7602a);
                return a;
            }
        };
    }

    /* renamed from: a */
    public void m11324a(boolean z) {
        this.f7608g = z;
    }

    /* renamed from: b */
    public void m11325b(int i) {
        this.f7606e = i;
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        return Json.m10761a(new HashMap());
    }

    /* renamed from: e */
    public void m11327e(String str) {
        this.f7602a = str;
    }

    /* renamed from: e */
    public String[] mo3302e() {
        String[] strArr = new String[15];
        strArr[0] = m11322p();
        strArr[1] = StringUtils.m10800a(this.f7612k);
        strArr[2] = m11333i() + TtmlNode.ANONYMOUS_REGION_ID;
        strArr[3] = "1";
        strArr[4] = m11335j();
        strArr[5] = m11338k() ? "2" : "1";
        strArr[6] = m11339l();
        strArr[7] = m11341m();
        strArr[8] = TtmlNode.ANONYMOUS_REGION_ID;
        strArr[9] = TtmlNode.ANONYMOUS_REGION_ID;
        strArr[10] = new SDKConfig().mo3294d();
        strArr[11] = m11343n();
        strArr[12] = m11344o();
        strArr[13] = TtmlNode.ANONYMOUS_REGION_ID;
        strArr[14] = RootUtils.m10764a() ? "1" : "0";
        return strArr;
    }

    /* renamed from: f */
    public void m11329f(String str) {
        this.f7607f = str;
    }

    /* renamed from: g */
    public void m11330g(String str) {
        this.f7609h = str;
    }

    /* renamed from: h */
    public String m11331h() {
        return this.f7602a;
    }

    /* renamed from: h */
    public void m11332h(String str) {
        this.f7605d = str;
    }

    /* renamed from: i */
    public int m11333i() {
        return this.f7606e;
    }

    /* renamed from: i */
    public void m11334i(String str) {
        this.f7604c = str;
    }

    /* renamed from: j */
    public String m11335j() {
        return this.f7607f;
    }

    /* renamed from: j */
    public void m11336j(String str) {
        this.f7610i = str;
    }

    /* renamed from: k */
    public void m11337k(String str) {
        this.f7603b = str;
    }

    /* renamed from: k */
    public boolean m11338k() {
        return this.f7608g;
    }

    /* renamed from: l */
    public String m11339l() {
        return this.f7609h;
    }

    /* renamed from: l */
    public void m11340l(String str) {
        this.f7611j = str;
    }

    /* renamed from: m */
    public String m11341m() {
        return this.f7610i;
    }

    /* renamed from: m */
    public void m11342m(String str) {
        this.f7612k = str;
    }

    /* renamed from: n */
    public String m11343n() {
        return this.f7605d;
    }

    /* renamed from: o */
    public String m11344o() {
        return this.f7604c;
    }
}
