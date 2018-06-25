package com.persianswitch.sdk.payment.managers.suggestion;

import android.os.Parcelable;
import com.persianswitch.sdk.base.fastkit.FilterableModel;

public interface IFrequentlyInput extends FilterableModel, Parcelable {

    public enum Type {
        CARD(1, ""),
        MOBILE(2, "2mobile_numbers"),
        ADSL(3, "3adsl_ids"),
        WIMAX(4, "4wimax_ids"),
        BILL(5, "5bill_ids"),
        MERCHANT(6, "6merchant_codes");
        
        private final int id;
        private final String preferenceName;

        private Type(int id, String preferenceName) {
            this.id = id;
            this.preferenceName = preferenceName;
        }

        public static Type getInstance(int id) {
            for (Type type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        public int getId() {
            return this.id;
        }

        public String getPreferenceName() {
            return this.preferenceName;
        }
    }

    String getName(boolean z);

    String getValue();

    boolean isDefault();

    void setDefault(boolean z);

    void setName(String str, boolean z);
}
