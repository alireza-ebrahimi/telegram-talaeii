package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import android.os.Build.VERSION;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.CertificateUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import org.json.JSONObject;

public class SendActivationRequest extends AbsRequest {
    /* renamed from: a */
    private String f7617a;
    /* renamed from: b */
    private String f7618b;
    /* renamed from: c */
    private String f7619c;
    /* renamed from: d */
    private boolean f7620d;
    /* renamed from: e */
    private String f7621e;

    public SendActivationRequest() {
        super(OpCode.SEND_ACTIVATION_CODE);
    }

    /* renamed from: a */
    public IWebServiceDescriptor mo3304a(final Context context) {
        return new IWebServiceDescriptor(this) {
            /* renamed from: b */
            final /* synthetic */ SendActivationRequest f7616b;

            /* renamed from: a */
            public WSRequest mo3300a() {
                WSRequest wSRequest = new WSRequest();
                wSRequest.m10915a(this.f7616b.m11298f().m10839a());
                wSRequest.m10928d(this.f7616b.f7617a);
                wSRequest.m10926c(DeviceInfo.m10721a(context, new SDKConfig()));
                wSRequest.m10932f(SDKConfig.m11029a(context));
                wSRequest.m10923b(DeviceInfo.m10722a(context, this.f7616b.f7618b, this.f7616b.f7619c));
                wSRequest.m10934g(CertificateUtils.m10757a(context));
                wSRequest.m10921b(this.f7616b.m11299g());
                wSRequest.m10918a(new HostDataRequestField(this.f7616b.m11289b(), this.f7616b.m11292c(), this.f7616b.m11286a(), AbsRequest.m11282b(context)).m11152a());
                wSRequest.m10924b(this.f7616b.mo3301d());
                wSRequest.m10919a(this.f7616b.mo3302e());
                return wSRequest;
            }
        };
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        return new JSONObject();
    }

    /* renamed from: e */
    public String[] mo3302e() {
        String[] strArr = new String[5];
        strArr[0] = "1";
        strArr[1] = VERSION.RELEASE;
        strArr[2] = this.f7620d ? "2" : "1";
        strArr[3] = this.f7621e;
        strArr[4] = new SDKConfig().mo3294d();
        return strArr;
    }
}
