package com.onesignal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class TrackGooglePurchase {
    private static Class<?> IInAppBillingServiceClass;
    private static int iapEnabled = -99;
    private Context appContext;
    private Method getPurchasesMethod;
    private Method getSkuDetailsMethod;
    private boolean isWaitingForPurchasesRequest = false;
    private Object mIInAppBillingService;
    private ServiceConnection mServiceConn;
    private boolean newAsExisting = true;
    private ArrayList<String> purchaseTokens;

    /* renamed from: com.onesignal.TrackGooglePurchase$1 */
    class C06981 implements ServiceConnection {
        C06981() {
        }

        public void onServiceDisconnected(ComponentName name) {
            TrackGooglePurchase.iapEnabled = -99;
            TrackGooglePurchase.this.mIInAppBillingService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Method asInterfaceMethod = TrackGooglePurchase.getAsInterfaceMethod(Class.forName("com.android.vending.billing.IInAppBillingService$Stub"));
                asInterfaceMethod.setAccessible(true);
                TrackGooglePurchase.this.mIInAppBillingService = asInterfaceMethod.invoke(null, new Object[]{service});
                TrackGooglePurchase.this.QueryBoughtItems();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /* renamed from: com.onesignal.TrackGooglePurchase$2 */
    class C06992 implements Runnable {
        C06992() {
        }

        public void run() {
            TrackGooglePurchase.this.isWaitingForPurchasesRequest = true;
            try {
                if (TrackGooglePurchase.this.getPurchasesMethod == null) {
                    TrackGooglePurchase.this.getPurchasesMethod = TrackGooglePurchase.getGetPurchasesMethod(TrackGooglePurchase.IInAppBillingServiceClass);
                    TrackGooglePurchase.this.getPurchasesMethod.setAccessible(true);
                }
                Bundle ownedItems = (Bundle) TrackGooglePurchase.this.getPurchasesMethod.invoke(TrackGooglePurchase.this.mIInAppBillingService, new Object[]{Integer.valueOf(3), TrackGooglePurchase.this.appContext.getPackageName(), "inapp", null});
                if (ownedItems.getInt("RESPONSE_CODE") == 0) {
                    ArrayList<String> skusToAdd = new ArrayList();
                    ArrayList<String> newPurchaseTokens = new ArrayList();
                    ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                    ArrayList<String> purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                    for (int i = 0; i < purchaseDataList.size(); i++) {
                        String sku = (String) ownedSkus.get(i);
                        String purchaseToken = new JSONObject((String) purchaseDataList.get(i)).getString("purchaseToken");
                        if (!(TrackGooglePurchase.this.purchaseTokens.contains(purchaseToken) || newPurchaseTokens.contains(purchaseToken))) {
                            newPurchaseTokens.add(purchaseToken);
                            skusToAdd.add(sku);
                        }
                    }
                    if (skusToAdd.size() > 0) {
                        TrackGooglePurchase.this.sendPurchases(skusToAdd, newPurchaseTokens);
                    } else if (purchaseDataList.size() == 0) {
                        TrackGooglePurchase.this.newAsExisting = false;
                        OneSignalPrefs.saveBool("GTPlayerPurchases", "ExistingPurchases", false);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            TrackGooglePurchase.this.isWaitingForPurchasesRequest = false;
        }
    }

    TrackGooglePurchase(Context activity) {
        boolean z = true;
        this.appContext = activity;
        this.purchaseTokens = new ArrayList();
        try {
            JSONArray jsonPurchaseTokens = new JSONArray(OneSignalPrefs.getString("GTPlayerPurchases", "purchaseTokens", "[]"));
            for (int i = 0; i < jsonPurchaseTokens.length(); i++) {
                this.purchaseTokens.add(jsonPurchaseTokens.get(i).toString());
            }
            if (jsonPurchaseTokens.length() != 0) {
                z = false;
            }
            this.newAsExisting = z;
            if (this.newAsExisting) {
                this.newAsExisting = OneSignalPrefs.getBool("GTPlayerPurchases", "ExistingPurchases", true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackIAP();
    }

    static boolean CanTrack(Context context) {
        if (iapEnabled == -99) {
            iapEnabled = context.checkCallingOrSelfPermission("com.android.vending.BILLING");
        }
        try {
            if (iapEnabled == 0) {
                IInAppBillingServiceClass = Class.forName("com.android.vending.billing.IInAppBillingService");
            }
            if (iapEnabled == 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            iapEnabled = 0;
            return false;
        }
    }

    void trackIAP() {
        if (this.mServiceConn == null) {
            this.mServiceConn = new C06981();
            Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            serviceIntent.setPackage("com.android.vending");
            this.appContext.bindService(serviceIntent, this.mServiceConn, 1);
        } else if (this.mIInAppBillingService != null) {
            QueryBoughtItems();
        }
    }

    private void QueryBoughtItems() {
        if (!this.isWaitingForPurchasesRequest) {
            new Thread(new C06992()).start();
        }
    }

    private void sendPurchases(ArrayList<String> skusToAdd, ArrayList<String> newPurchaseTokens) {
        try {
            if (this.getSkuDetailsMethod == null) {
                this.getSkuDetailsMethod = getGetSkuDetailsMethod(IInAppBillingServiceClass);
                this.getSkuDetailsMethod.setAccessible(true);
            }
            new Bundle().putStringArrayList("ITEM_ID_LIST", skusToAdd);
            Bundle skuDetails = (Bundle) this.getSkuDetailsMethod.invoke(this.mIInAppBillingService, new Object[]{Integer.valueOf(3), this.appContext.getPackageName(), "inapp", querySkus});
            if (skuDetails.getInt("RESPONSE_CODE") == 0) {
                String sku;
                ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");
                Map<String, JSONObject> currentSkus = new HashMap();
                Iterator it = responseList.iterator();
                while (it.hasNext()) {
                    JSONObject object = new JSONObject((String) it.next());
                    sku = object.getString("productId");
                    BigDecimal price = new BigDecimal(object.getString("price_amount_micros")).divide(new BigDecimal(1000000));
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("sku", sku);
                    jsonItem.put("iso", object.getString("price_currency_code"));
                    jsonItem.put("amount", price.toString());
                    currentSkus.put(sku, jsonItem);
                }
                JSONArray purchasesToReport = new JSONArray();
                it = skusToAdd.iterator();
                while (it.hasNext()) {
                    sku = (String) it.next();
                    if (currentSkus.containsKey(sku)) {
                        purchasesToReport.put(currentSkus.get(sku));
                    }
                }
                if (purchasesToReport.length() > 0) {
                    final ArrayList<String> arrayList = newPurchaseTokens;
                    OneSignal.sendPurchases(purchasesToReport, this.newAsExisting, new ResponseHandler() {
                        public void onFailure(int statusCode, JSONObject response, Throwable throwable) {
                            OneSignal.Log(LOG_LEVEL.WARN, "HTTP sendPurchases failed to send.", throwable);
                            TrackGooglePurchase.this.isWaitingForPurchasesRequest = false;
                        }

                        public void onSuccess(String response) {
                            TrackGooglePurchase.this.purchaseTokens.addAll(arrayList);
                            OneSignalPrefs.saveString("GTPlayerPurchases", "purchaseTokens", TrackGooglePurchase.this.purchaseTokens.toString());
                            OneSignalPrefs.saveBool("GTPlayerPurchases", "ExistingPurchases", true);
                            TrackGooglePurchase.this.newAsExisting = false;
                            TrackGooglePurchase.this.isWaitingForPurchasesRequest = false;
                        }
                    });
                }
            }
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.WARN, "Failed to track IAP purchases", t);
        }
    }

    private static Method getAsInterfaceMethod(Class clazz) {
        for (Method method : clazz.getMethods()) {
            Class<?>[] args = method.getParameterTypes();
            if (args.length == 1 && args[0] == IBinder.class) {
                return method;
            }
        }
        return null;
    }

    private static Method getGetPurchasesMethod(Class clazz) {
        for (Method method : clazz.getMethods()) {
            Class<?>[] args = method.getParameterTypes();
            if (args.length == 4 && args[0] == Integer.TYPE && args[1] == String.class && args[2] == String.class && args[3] == String.class) {
                return method;
            }
        }
        return null;
    }

    private static Method getGetSkuDetailsMethod(Class clazz) {
        for (Method method : clazz.getMethods()) {
            Class<?>[] args = method.getParameterTypes();
            Class<?> returnType = method.getReturnType();
            if (args.length == 4 && args[0] == Integer.TYPE && args[1] == String.class && args[2] == String.class && args[3] == Bundle.class && returnType == Bundle.class) {
                return method;
            }
        }
        return null;
    }
}
