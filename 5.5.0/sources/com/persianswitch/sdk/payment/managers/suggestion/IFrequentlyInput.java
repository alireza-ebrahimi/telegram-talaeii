package com.persianswitch.sdk.payment.managers.suggestion;

import android.os.Parcelable;
import com.persianswitch.sdk.base.fastkit.FilterableModel;

public interface IFrequentlyInput extends Parcelable, FilterableModel {

    public enum Type {
        CARD(1, TtmlNode.ANONYMOUS_REGION_ID),
        MOBILE(2, "2mobile_numbers"),
        ADSL(3, "3adsl_ids"),
        WIMAX(4, "4wimax_ids"),
        BILL(5, "5bill_ids"),
        MERCHANT(6, "6merchant_codes");
        
        /* renamed from: g */
        private final int f7417g;
        /* renamed from: h */
        private final String f7418h;

        private Type(int i, String str) {
            this.f7417g = i;
            this.f7418h = str;
        }
    }
}
