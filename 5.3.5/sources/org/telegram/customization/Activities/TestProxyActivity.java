package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.ProxyServerModel;
import utils.view.Constants;
import utils.view.ToastUtil;

public class TestProxyActivity extends Activity implements IResponseReceiver {
    TextView tvInfo;
    TextView tvNetwork;

    /* renamed from: org.telegram.customization.Activities.TestProxyActivity$1 */
    class C11091 implements OnClickListener {
        C11091() {
        }

        public void onClick(View view) {
            TestProxyActivity.this.findViewById(R.id.pb_loading).setVisibility(0);
            TestProxyActivity.this.tvInfo.setText("");
            TestProxyActivity.this.tvNetwork.setText("");
            HandleRequest.getNew(TestProxyActivity.this.getApplicationContext(), TestProxyActivity.this).proxyGetServerTest(true);
        }
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        ProxyServerModel model;

        LongOperation(ProxyServerModel proxyServerModel) {
            this.model = proxyServerModel;
        }

        protected String doInBackground(String... params) {
            TestProxyActivity.checkSocket(this.model);
            return "Executed";
        }

        protected void onPostExecute(String result) {
        }

        protected void onPreExecute() {
        }

        protected void onProgressUpdate(Void... values) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_proxy);
        this.tvInfo = (TextView) findViewById(R.id.tv_info);
        this.tvNetwork = (TextView) findViewById(R.id.tv_connction);
        findViewById(R.id.btn_get_proxy).setOnClickListener(new C11091());
    }

    public void onResult(Object object, int StatusCode) {
        findViewById(R.id.pb_loading).setVisibility(8);
        switch (StatusCode) {
            case Constants.ERROR_GET_PROXY /*-24*/:
                ToastUtil.AppToast(getApplicationContext(), "ERROR GET PROXY").show();
                return;
            case 24:
                ProxyServerModel current = (ProxyServerModel) ((ArrayList) object).get(0);
                this.tvInfo.setText("ip : " + current.getIp() + " \n " + " username : " + current.getUserName() + " \n " + " pass : " + current.getPassWord());
                return;
            default:
                return;
        }
    }

    public boolean testConnection(String ip) {
        boolean connectionStatus = false;
        try {
            connectionStatus = InetAddress.getByName(ip).isReachable(3000);
        } catch (Exception e) {
            e.printStackTrace();
            this.tvNetwork.setText(e.toString());
            System.out.println(e.toString());
        }
        this.tvNetwork.setText(connectionStatus + "");
        return connectionStatus;
    }

    public static void checkSocket(ProxyServerModel proxyServerModel) {
        try {
            Socket socket = new Socket(new Proxy(Type.SOCKS, new InetSocketAddress(proxyServerModel.getIp(), proxyServerModel.getPort())));
            socket.connect(new InetSocketAddress("www.google.com", 80));
            socket.getOutputStream().write("GET /index.html HTTP/1.1\r\nHost: www.google.com\r\n\r\n".getBytes("UTF-8"));
            byte[] result = new byte[1024];
            socket.getInputStream().read(result);
            socket.close();
            System.out.println(new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
