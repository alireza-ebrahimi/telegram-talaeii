package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.model.CVV2Status;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.repo.CardRepo;
import org.json.JSONObject;

public class InquiryMerchantResponse extends AbsResponse {
    private String mAmount;
    private String mAmountStatus;
    private String mAutoSettle;
    private CVV2Status mCVV2Status;
    private UserCard mDefaultCard;
    private String mDescription;
    private String mDistributorMobileNo;
    private String mMerchantCode;
    private String mMerchantLogoURL;
    private String mMerchantName;
    private String mPaymentId;
    private String mPaymentIdStatus;
    private String mServerData;
    private JSONObject mSyncData;
    private String mTransactionToken;

    public InquiryMerchantResponse(Context context, WSResponse response) throws JsonParseException {
        super(context, response);
    }

    void onConsumeExtraData(Context context, JSONObject extraData) throws JsonParseException {
        try {
            if (extraData.has("sd")) {
                this.mServerData = extraData.getString("sd");
            }
            if (extraData.has("cv")) {
                this.mCVV2Status = CVV2Status.getInstance(extraData.getInt("cv"));
            }
            if (extraData.has("mc")) {
                this.mMerchantCode = extraData.getInt("mc") + "";
            }
            if (extraData.has("mn")) {
                this.mMerchantName = extraData.getString("mn");
            }
            if (extraData.has("lg")) {
                this.mMerchantLogoURL = extraData.getString("lg");
            }
            if (extraData.has("ds")) {
                this.mDescription = extraData.getString("ds");
            }
            if (extraData.has("ps")) {
                this.mPaymentIdStatus = extraData.getInt("ps") + "";
            }
            if (extraData.has("pi")) {
                this.mPaymentId = extraData.getString("pi");
            }
            if (extraData.has("dm")) {
                this.mDistributorMobileNo = extraData.getString("dm");
            }
            if (extraData.has("as")) {
                this.mAmountStatus = extraData.getInt("as") + "";
            }
            if (extraData.has("am")) {
                this.mAmount = extraData.getLong("am") + "";
            }
            if (extraData.has("tk")) {
                this.mTransactionToken = extraData.getString("tk");
            }
            if (extraData.has("ia")) {
                this.mAutoSettle = extraData.getInt("ia") + "";
            }
            if (extraData.has("sy")) {
                this.mSyncData = extraData.getJSONObject("sy");
            }
            if (extraData.has("ca")) {
                Long defaultCardId = StringUtils.toLong(extraData.getString("ca"));
                if (defaultCardId != null && defaultCardId.longValue() > 0) {
                    this.mDefaultCard = (UserCard) new CardRepo(context).findById(defaultCardId);
                }
            }
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public String getServerData() {
        return this.mServerData;
    }

    public CVV2Status getCVV2Status() {
        return this.mCVV2Status;
    }

    public UserCard getDefaultCard() {
        return this.mDefaultCard;
    }

    public String getMerchantCode() {
        return this.mMerchantCode;
    }

    public String getMerchantName() {
        return this.mMerchantName;
    }

    public String getMerchantLogoURL() {
        return this.mMerchantLogoURL;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getAmountStatus() {
        return this.mAmountStatus;
    }

    public String getAmount() {
        return this.mAmount;
    }

    public String getPaymentIdStatus() {
        return this.mPaymentIdStatus;
    }

    public String getPaymentId() {
        return this.mPaymentId;
    }

    public String getDistributorMobileNo() {
        return this.mDistributorMobileNo;
    }

    public String getTransactionToken() {
        return this.mTransactionToken;
    }

    public String getAutoSettle() {
        return this.mAutoSettle;
    }

    public JSONObject getSyncData() {
        return this.mSyncData;
    }
}
