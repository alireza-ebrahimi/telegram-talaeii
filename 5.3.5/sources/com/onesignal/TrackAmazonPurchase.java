package com.onesignal;

import android.content.Context;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.Product;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.ProductDataResponse.RequestStatus;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserDataResponse;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

class TrackAmazonPurchase {
    private boolean canTrack = false;
    private Context context;
    private Field listenerHandlerField;
    private Object listenerHandlerObject;
    private OSPurchasingListener osPurchasingListener;

    /* renamed from: com.onesignal.TrackAmazonPurchase$1 */
    static /* synthetic */ class C06971 {
        /* renamed from: $SwitchMap$com$amazon$device$iap$model$ProductDataResponse$RequestStatus */
        static final /* synthetic */ int[] f40x4eb80c9f = new int[RequestStatus.values().length];

        static {
            try {
                f40x4eb80c9f[RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private class OSPurchasingListener implements PurchasingListener {
        private String currentMarket;
        private RequestId lastRequestId;
        PurchasingListener orgPurchasingListener;

        private OSPurchasingListener() {
        }

        private String marketToCurrencyCode(String market) {
            Object obj = -1;
            switch (market.hashCode()) {
                case 2100:
                    if (market.equals("AU")) {
                        obj = 9;
                        break;
                    }
                    break;
                case 2128:
                    if (market.equals("BR")) {
                        obj = 8;
                        break;
                    }
                    break;
                case 2142:
                    if (market.equals("CA")) {
                        obj = 7;
                        break;
                    }
                    break;
                case 2177:
                    if (market.equals("DE")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 2222:
                    if (market.equals("ES")) {
                        obj = 4;
                        break;
                    }
                    break;
                case 2252:
                    if (market.equals("FR")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 2267:
                    if (market.equals("GB")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 2347:
                    if (market.equals("IT")) {
                        obj = 5;
                        break;
                    }
                    break;
                case 2374:
                    if (market.equals("JP")) {
                        obj = 6;
                        break;
                    }
                    break;
                case 2718:
                    if (market.equals("US")) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return "USD";
                case 1:
                    return "GBP";
                case 2:
                case 3:
                case 4:
                case 5:
                    return "EUR";
                case 6:
                    return "JPY";
                case 7:
                    return "CDN";
                case 8:
                    return "BRL";
                case 9:
                    return "AUD";
                default:
                    return "";
            }
        }

        public void onProductDataResponse(ProductDataResponse response) {
            if (this.lastRequestId != null && this.lastRequestId.toString().equals(response.getRequestId().toString())) {
                try {
                    switch (C06971.f40x4eb80c9f[response.getRequestStatus().ordinal()]) {
                        case 1:
                            JSONArray purchasesToReport = new JSONArray();
                            Map<String, Product> products = response.getProductData();
                            for (String key : products.keySet()) {
                                Product product = (Product) products.get(key);
                                JSONObject jsonItem = new JSONObject();
                                jsonItem.put("sku", product.getSku());
                                jsonItem.put("iso", marketToCurrencyCode(this.currentMarket));
                                String price = product.getPrice();
                                if (!price.matches("^[0-9]")) {
                                    price = price.substring(1);
                                }
                                jsonItem.put("amount", price);
                                purchasesToReport.put(jsonItem);
                            }
                            OneSignal.sendPurchases(purchasesToReport, false, null);
                            return;
                        default:
                            return;
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                t.printStackTrace();
            } else if (this.orgPurchasingListener != null) {
                this.orgPurchasingListener.onProductDataResponse(response);
            }
        }

        public void onPurchaseResponse(PurchaseResponse response) {
            try {
                if (response.getRequestStatus() == PurchaseResponse.RequestStatus.SUCCESSFUL) {
                    this.currentMarket = response.getUserData().getMarketplace();
                    Set<String> productSkus = new HashSet();
                    productSkus.add(response.getReceipt().getSku());
                    this.lastRequestId = PurchasingService.getProductData(productSkus);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            if (this.orgPurchasingListener != null) {
                this.orgPurchasingListener.onPurchaseResponse(response);
            }
        }

        public void onPurchaseUpdatesResponse(PurchaseUpdatesResponse response) {
            if (this.orgPurchasingListener != null) {
                this.orgPurchasingListener.onPurchaseUpdatesResponse(response);
            }
        }

        public void onUserDataResponse(UserDataResponse response) {
            if (this.orgPurchasingListener != null) {
                this.orgPurchasingListener.onUserDataResponse(response);
            }
        }
    }

    TrackAmazonPurchase(Context context) {
        this.context = context;
        try {
            Class<?> listenerHandlerClass = Class.forName("com.amazon.device.iap.internal.d");
            this.listenerHandlerObject = listenerHandlerClass.getMethod("d", new Class[0]).invoke(null, new Object[0]);
            this.listenerHandlerField = listenerHandlerClass.getDeclaredField("f");
            this.listenerHandlerField.setAccessible(true);
            this.osPurchasingListener = new OSPurchasingListener();
            this.osPurchasingListener.orgPurchasingListener = (PurchasingListener) this.listenerHandlerField.get(this.listenerHandlerObject);
            this.canTrack = true;
            setListener();
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error adding Amazon IAP listener.", t);
        }
    }

    private void setListener() {
        PurchasingService.registerListener(this.context, this.osPurchasingListener);
    }

    void checkListener() {
        if (this.canTrack) {
            try {
                PurchasingListener curPurchasingListener = (PurchasingListener) this.listenerHandlerField.get(this.listenerHandlerObject);
                if (curPurchasingListener != this.osPurchasingListener) {
                    this.osPurchasingListener.orgPurchasingListener = curPurchasingListener;
                    setListener();
                }
            } catch (Throwable th) {
            }
        }
    }
}
