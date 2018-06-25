package com.crashlytics.android.answers;

import android.os.Bundle;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FirebaseAnalyticsEventMapper {
    private static final Set<String> EVENT_NAMES = new HashSet(Arrays.asList(new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter"}));
    private static final String FIREBASE_LEVEL_NAME = "level_name";
    private static final String FIREBASE_METHOD = "method";
    private static final String FIREBASE_RATING = "rating";
    private static final String FIREBASE_SUCCESS = "success";

    public FirebaseAnalyticsEvent mapEvent(SessionEvent event) {
        boolean isCustomEvent;
        boolean isPredefinedEvent;
        if (!Type.CUSTOM.equals(event.type) || event.customType == null) {
            isCustomEvent = false;
        } else {
            isCustomEvent = true;
        }
        if (!Type.PREDEFINED.equals(event.type) || event.predefinedType == null) {
            isPredefinedEvent = false;
        } else {
            isPredefinedEvent = true;
        }
        if (!isCustomEvent && !isPredefinedEvent) {
            return null;
        }
        Bundle bundle;
        String eventName;
        if (isPredefinedEvent) {
            bundle = mapPredefinedEvent(event);
        } else {
            bundle = new Bundle();
            if (event.customAttributes != null) {
                mapCustomEventAttributes(bundle, event.customAttributes);
            }
        }
        if (isPredefinedEvent) {
            boolean wasFailedEvent;
            String successBoolean = (String) event.predefinedAttributes.get("success");
            if (successBoolean == null || Boolean.parseBoolean(successBoolean)) {
                wasFailedEvent = false;
            } else {
                wasFailedEvent = true;
            }
            eventName = mapPredefinedEventName(event.predefinedType, wasFailedEvent);
        } else {
            eventName = mapCustomEventName(event.customType);
        }
        Fabric.getLogger().mo4381d(Answers.TAG, "Logging event into firebase...");
        return new FirebaseAnalyticsEvent(eventName, bundle);
    }

    private String mapCustomEventName(String eventName) {
        if (eventName == null || eventName.length() == 0) {
            return "fabric_unnamed_event";
        }
        if (EVENT_NAMES.contains(eventName)) {
            return "fabric_" + eventName;
        }
        eventName = eventName.replaceAll("[^\\p{Alnum}_]+", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (eventName.startsWith("ga_") || eventName.startsWith("google_") || eventName.startsWith("firebase_") || !Character.isLetter(eventName.charAt(0))) {
            eventName = "fabric_" + eventName;
        }
        if (eventName.length() > 40) {
            eventName = eventName.substring(0, 40);
        }
        return eventName;
    }

    private String mapAttribute(String attributeName) {
        if (attributeName == null || attributeName.length() == 0) {
            return "fabric_unnamed_parameter";
        }
        attributeName = attributeName.replaceAll("[^\\p{Alnum}_]+", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (attributeName.startsWith("ga_") || attributeName.startsWith("google_") || attributeName.startsWith("firebase_") || !Character.isLetter(attributeName.charAt(0))) {
            attributeName = "fabric_" + attributeName;
        }
        return attributeName.length() > 40 ? attributeName.substring(0, 40) : attributeName;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String mapPredefinedEventName(java.lang.String r6, boolean r7) {
        /*
        r5 = this;
        r3 = 2;
        r2 = 1;
        r0 = 0;
        r1 = -1;
        if (r7 == 0) goto L_0x0011;
    L_0x0006:
        r4 = r6.hashCode();
        switch(r4) {
            case -902468296: goto L_0x002c;
            case 103149417: goto L_0x0037;
            case 1743324417: goto L_0x0021;
            default: goto L_0x000d;
        };
    L_0x000d:
        r4 = r1;
    L_0x000e:
        switch(r4) {
            case 0: goto L_0x0042;
            case 1: goto L_0x0046;
            case 2: goto L_0x004a;
            default: goto L_0x0011;
        };
    L_0x0011:
        r4 = r6.hashCode();
        switch(r4) {
            case -2131650889: goto L_0x00cd;
            case -1183699191: goto L_0x00b3;
            case -938102371: goto L_0x008f;
            case -906336856: goto L_0x0079;
            case -902468296: goto L_0x009a;
            case -389087554: goto L_0x006e;
            case 23457852: goto L_0x0058;
            case 103149417: goto L_0x00a6;
            case 109400031: goto L_0x0084;
            case 196004670: goto L_0x00c0;
            case 1664021448: goto L_0x0063;
            case 1743324417: goto L_0x004e;
            default: goto L_0x0018;
        };
    L_0x0018:
        r0 = r1;
    L_0x0019:
        switch(r0) {
            case 0: goto L_0x00da;
            case 1: goto L_0x00df;
            case 2: goto L_0x00e4;
            case 3: goto L_0x00e9;
            case 4: goto L_0x00ee;
            case 5: goto L_0x00f3;
            case 6: goto L_0x00f8;
            case 7: goto L_0x00fd;
            case 8: goto L_0x0102;
            case 9: goto L_0x0107;
            case 10: goto L_0x010c;
            case 11: goto L_0x0111;
            default: goto L_0x001c;
        };
    L_0x001c:
        r0 = r5.mapCustomEventName(r6);
    L_0x0020:
        return r0;
    L_0x0021:
        r4 = "purchase";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x002a:
        r4 = r0;
        goto L_0x000e;
    L_0x002c:
        r4 = "signUp";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x0035:
        r4 = r2;
        goto L_0x000e;
    L_0x0037:
        r4 = "login";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x0040:
        r4 = r3;
        goto L_0x000e;
    L_0x0042:
        r0 = "failed_ecommerce_purchase";
        goto L_0x0020;
    L_0x0046:
        r0 = "failed_sign_up";
        goto L_0x0020;
    L_0x004a:
        r0 = "failed_login";
        goto L_0x0020;
    L_0x004e:
        r2 = "purchase";
        r2 = r6.equals(r2);
        if (r2 == 0) goto L_0x0018;
    L_0x0057:
        goto L_0x0019;
    L_0x0058:
        r0 = "addToCart";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0061:
        r0 = r2;
        goto L_0x0019;
    L_0x0063:
        r0 = "startCheckout";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x006c:
        r0 = r3;
        goto L_0x0019;
    L_0x006e:
        r0 = "contentView";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0077:
        r0 = 3;
        goto L_0x0019;
    L_0x0079:
        r0 = "search";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0082:
        r0 = 4;
        goto L_0x0019;
    L_0x0084:
        r0 = "share";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x008d:
        r0 = 5;
        goto L_0x0019;
    L_0x008f:
        r0 = "rating";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0098:
        r0 = 6;
        goto L_0x0019;
    L_0x009a:
        r0 = "signUp";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00a3:
        r0 = 7;
        goto L_0x0019;
    L_0x00a6:
        r0 = "login";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00af:
        r0 = 8;
        goto L_0x0019;
    L_0x00b3:
        r0 = "invite";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00bc:
        r0 = 9;
        goto L_0x0019;
    L_0x00c0:
        r0 = "levelStart";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00c9:
        r0 = 10;
        goto L_0x0019;
    L_0x00cd:
        r0 = "levelEnd";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00d6:
        r0 = 11;
        goto L_0x0019;
    L_0x00da:
        r0 = "ecommerce_purchase";
        goto L_0x0020;
    L_0x00df:
        r0 = "add_to_cart";
        goto L_0x0020;
    L_0x00e4:
        r0 = "begin_checkout";
        goto L_0x0020;
    L_0x00e9:
        r0 = "select_content";
        goto L_0x0020;
    L_0x00ee:
        r0 = "search";
        goto L_0x0020;
    L_0x00f3:
        r0 = "share";
        goto L_0x0020;
    L_0x00f8:
        r0 = "rate_content";
        goto L_0x0020;
    L_0x00fd:
        r0 = "sign_up";
        goto L_0x0020;
    L_0x0102:
        r0 = "login";
        goto L_0x0020;
    L_0x0107:
        r0 = "invite";
        goto L_0x0020;
    L_0x010c:
        r0 = "level_start";
        goto L_0x0020;
    L_0x0111:
        r0 = "level_end";
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.answers.FirebaseAnalyticsEventMapper.mapPredefinedEventName(java.lang.String, boolean):java.lang.String");
    }

    private Bundle mapPredefinedEvent(SessionEvent event) {
        Bundle bundle = new Bundle();
        if ("purchase".equals(event.predefinedType)) {
            putString(bundle, Param.ITEM_ID, (String) event.predefinedAttributes.get("itemId"));
            putString(bundle, Param.ITEM_NAME, (String) event.predefinedAttributes.get("itemName"));
            putString(bundle, Param.ITEM_CATEGORY, (String) event.predefinedAttributes.get("itemType"));
            putDouble(bundle, Param.VALUE, mapPriceValue(event.predefinedAttributes.get("itemPrice")));
            putString(bundle, Param.CURRENCY, (String) event.predefinedAttributes.get(Param.CURRENCY));
        } else if ("addToCart".equals(event.predefinedType)) {
            putString(bundle, Param.ITEM_ID, (String) event.predefinedAttributes.get("itemId"));
            putString(bundle, Param.ITEM_NAME, (String) event.predefinedAttributes.get("itemName"));
            putString(bundle, Param.ITEM_CATEGORY, (String) event.predefinedAttributes.get("itemType"));
            putDouble(bundle, Param.PRICE, mapPriceValue(event.predefinedAttributes.get("itemPrice")));
            putDouble(bundle, Param.VALUE, mapPriceValue(event.predefinedAttributes.get("itemPrice")));
            putString(bundle, Param.CURRENCY, (String) event.predefinedAttributes.get(Param.CURRENCY));
            bundle.putLong(Param.QUANTITY, 1);
        } else if ("startCheckout".equals(event.predefinedType)) {
            putLong(bundle, Param.QUANTITY, Long.valueOf((long) ((Integer) event.predefinedAttributes.get("itemCount")).intValue()));
            putDouble(bundle, Param.VALUE, mapPriceValue(event.predefinedAttributes.get("totalPrice")));
            putString(bundle, Param.CURRENCY, (String) event.predefinedAttributes.get(Param.CURRENCY));
        } else if ("contentView".equals(event.predefinedType)) {
            putString(bundle, Param.CONTENT_TYPE, (String) event.predefinedAttributes.get("contentType"));
            putString(bundle, Param.ITEM_ID, (String) event.predefinedAttributes.get("contentId"));
            putString(bundle, Param.ITEM_NAME, (String) event.predefinedAttributes.get("contentName"));
        } else if (Event.SEARCH.equals(event.predefinedType)) {
            putString(bundle, Param.SEARCH_TERM, (String) event.predefinedAttributes.get(SearchIntents.EXTRA_QUERY));
        } else if (Event.SHARE.equals(event.predefinedType)) {
            putString(bundle, "method", (String) event.predefinedAttributes.get("method"));
            putString(bundle, Param.CONTENT_TYPE, (String) event.predefinedAttributes.get("contentType"));
            putString(bundle, Param.ITEM_ID, (String) event.predefinedAttributes.get("contentId"));
            putString(bundle, Param.ITEM_NAME, (String) event.predefinedAttributes.get("contentName"));
        } else if (FIREBASE_RATING.equals(event.predefinedType)) {
            putString(bundle, FIREBASE_RATING, String.valueOf(event.predefinedAttributes.get(FIREBASE_RATING)));
            putString(bundle, Param.CONTENT_TYPE, (String) event.predefinedAttributes.get("contentType"));
            putString(bundle, Param.ITEM_ID, (String) event.predefinedAttributes.get("contentId"));
            putString(bundle, Param.ITEM_NAME, (String) event.predefinedAttributes.get("contentName"));
        } else if ("signUp".equals(event.predefinedType)) {
            putString(bundle, "method", (String) event.predefinedAttributes.get("method"));
        } else if (Event.LOGIN.equals(event.predefinedType)) {
            putString(bundle, "method", (String) event.predefinedAttributes.get("method"));
        } else if ("invite".equals(event.predefinedType)) {
            putString(bundle, "method", (String) event.predefinedAttributes.get("method"));
        } else if ("levelStart".equals(event.predefinedType)) {
            putString(bundle, "level_name", (String) event.predefinedAttributes.get("levelName"));
        } else if ("levelEnd".equals(event.predefinedType)) {
            putDouble(bundle, Param.SCORE, mapDouble(event.predefinedAttributes.get(Param.SCORE)));
            putString(bundle, "level_name", (String) event.predefinedAttributes.get("levelName"));
            putInt(bundle, "success", mapBooleanValue((String) event.predefinedAttributes.get("success")));
        }
        mapCustomEventAttributes(bundle, event.customAttributes);
        return bundle;
    }

    private void putLong(Bundle bundle, String param, Long longValue) {
        if (longValue != null) {
            bundle.putLong(param, longValue.longValue());
        }
    }

    private void putInt(Bundle bundle, String param, Integer intValue) {
        if (intValue != null) {
            bundle.putInt(param, intValue.intValue());
        }
    }

    private void putString(Bundle bundle, String param, String stringValue) {
        if (stringValue != null) {
            bundle.putString(param, stringValue);
        }
    }

    private void putDouble(Bundle bundle, String param, Double doubleValue) {
        Double mappedDouble = mapDouble(doubleValue);
        if (mappedDouble != null) {
            bundle.putDouble(param, mappedDouble.doubleValue());
        }
    }

    private Double mapDouble(Object doubleObj) {
        String doubleString = String.valueOf(doubleObj);
        if (doubleString == null) {
            return null;
        }
        return Double.valueOf(doubleString);
    }

    private Integer mapBooleanValue(String truthyString) {
        if (truthyString == null) {
            return null;
        }
        return Integer.valueOf(truthyString.equals("true") ? 1 : 0);
    }

    private Double mapPriceValue(Object o) {
        if (((Long) o) == null) {
            return null;
        }
        return Double.valueOf(new BigDecimal(((Long) o).longValue()).divide(AddToCartEvent.MICRO_CONSTANT).doubleValue());
    }

    private void mapCustomEventAttributes(Bundle mutatedBundle, Map<String, Object> customAttributes) {
        for (Entry<String, Object> o : customAttributes.entrySet()) {
            Object value = o.getValue();
            String attributeKey = mapAttribute((String) o.getKey());
            if (value instanceof String) {
                mutatedBundle.putString(attributeKey, o.getValue().toString());
            } else if (value instanceof Double) {
                mutatedBundle.putDouble(attributeKey, ((Double) o.getValue()).doubleValue());
            } else if (value instanceof Long) {
                mutatedBundle.putLong(attributeKey, ((Long) o.getValue()).longValue());
            } else if (value instanceof Integer) {
                mutatedBundle.putInt(attributeKey, ((Integer) o.getValue()).intValue());
            }
        }
    }
}
