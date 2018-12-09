package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.customization.p151g.C2497d;
import utils.view.ToastUtil;

public class TestProxyActivity extends Activity implements C2497d {
    /* renamed from: a */
    TextView f8449a;
    /* renamed from: b */
    TextView f8450b;

    /* renamed from: org.telegram.customization.Activities.TestProxyActivity$1 */
    class C25201 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ TestProxyActivity f8448a;

        C25201(TestProxyActivity testProxyActivity) {
            this.f8448a = testProxyActivity;
        }

        public void onClick(View view) {
            this.f8448a.findViewById(R.id.pb_loading).setVisibility(0);
            this.f8448a.f8449a.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f8448a.f8450b.setText(TtmlNode.ANONYMOUS_REGION_ID);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_test_proxy);
        this.f8449a = (TextView) findViewById(R.id.tv_info);
        this.f8450b = (TextView) findViewById(R.id.tv_connction);
        findViewById(R.id.btn_get_proxy).setOnClickListener(new C25201(this));
    }

    public void onResult(Object obj, int i) {
        findViewById(R.id.pb_loading).setVisibility(8);
        switch (i) {
            case -24:
                ToastUtil.a(getApplicationContext(), "ERROR GET PROXY").show();
                return;
            case 24:
                ProxyServerModel proxyServerModel = (ProxyServerModel) ((ArrayList) obj).get(0);
                this.f8449a.setText("ip : " + proxyServerModel.getIp() + " \n  username : " + proxyServerModel.getUserName() + " \n  pass : " + proxyServerModel.getPassWord());
                return;
            default:
                return;
        }
    }
}
