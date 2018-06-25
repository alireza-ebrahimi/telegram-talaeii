package com.persianswitch.sdk.payment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public class PaymentProfile implements Parcelable {
    public static final Creator<PaymentProfile> CREATOR = new C08011();
    private String mAmount;
    private UserCard mCard;
    private CVV2Status mCvv2Status;
    private String mDistributorMobileNumber;
    private String mHostData;
    private String mHostDataSign;
    private String mHostSuggestCardNo;
    private String mHostTranId;
    private boolean mIsAutoSettle;
    private String mMerchantCode;
    private String mMerchantLogoURL;
    private String mMerchantName;
    private String mPaymentId;
    private int mPoint;
    private String mReferenceNumber;
    private String mServerData;
    private String mServerMessage;
    private int mStatusCode;
    private TransactionStatus mStatusType;
    private String mToken;
    private long mUniqueTranId;

    /* renamed from: com.persianswitch.sdk.payment.model.PaymentProfile$1 */
    static class C08011 implements Creator<PaymentProfile> {
        C08011() {
        }

        public PaymentProfile createFromParcel(Parcel in) {
            return new PaymentProfile(in);
        }

        public PaymentProfile[] newArray(int size) {
            return new PaymentProfile[size];
        }
    }

    public PaymentProfile() {
        this.mStatusCode = 1001;
        this.mStatusType = TransactionStatus.UNKNOWN;
        this.mCvv2Status = CVV2Status.CVV2_REQUIRED;
    }

    private PaymentProfile(Parcel in) {
        this.mStatusCode = 1001;
        this.mStatusType = TransactionStatus.UNKNOWN;
        this.mStatusCode = in.readInt();
        this.mStatusType = TransactionStatus.valueOf(in.readString());
        this.mUniqueTranId = in.readLong();
        this.mCard = (UserCard) in.readParcelable(UserCard.class.getClassLoader());
        this.mHostSuggestCardNo = in.readString();
        this.mCvv2Status = in.readInt() == CVV2Status.CVV2_REQUIRED.getStatus() ? CVV2Status.CVV2_REQUIRED : CVV2Status.CVV2_NOT_REQUIRED_STATUS;
        this.mAmount = in.readString();
        this.mPaymentId = in.readString();
        this.mMerchantCode = in.readString();
        this.mMerchantName = in.readString();
        this.mMerchantLogoURL = in.readString();
        this.mServerMessage = in.readString();
        this.mReferenceNumber = in.readString();
        this.mHostTranId = in.readString();
        this.mHostData = in.readString();
        this.mHostDataSign = in.readString();
        this.mServerData = in.readString();
        this.mToken = in.readString();
        this.mPoint = in.readInt();
        this.mDistributorMobileNumber = in.readString();
        this.mIsAutoSettle = in.readInt() > 0;
    }

    public static PaymentProfile recycle(PaymentProfile paymentProfile) {
        PaymentProfile profile = new PaymentProfile();
        profile.setHostTranId(paymentProfile.getHostTranId());
        profile.setHostData(paymentProfile.getHostData());
        profile.setHostDataSign(paymentProfile.getHostDataSign());
        return profile;
    }

    public String getHostTranId() {
        return this.mHostTranId;
    }

    public PaymentProfile setHostTranId(String hostTranId) {
        this.mHostTranId = hostTranId;
        return this;
    }

    public String getHostData() {
        return this.mHostData;
    }

    public void setHostData(@Nullable String hostData) {
        this.mHostData = hostData;
    }

    public String getHostDataSign() {
        return this.mHostDataSign;
    }

    public void setHostDataSign(String hostDataSign) {
        this.mHostDataSign = hostDataSign;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public void setCvv2Status(CVV2Status status) {
        this.mCvv2Status = status;
    }

    public CVV2Status getCvv2Status() {
        return this.mCvv2Status;
    }

    public String getHostSuggestCardNo() {
        return this.mHostSuggestCardNo;
    }

    public void setHostSuggestCardNo(String hostSuggestCardNo) {
        this.mHostSuggestCardNo = hostSuggestCardNo;
    }

    public UserCard getCard() {
        return this.mCard;
    }

    public void setCard(UserCard card) {
        this.mCard = card;
    }

    public String getServerMessage() {
        return this.mServerMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.mServerMessage = serverMessage;
    }

    public String getReferenceNumber() {
        return this.mReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.mReferenceNumber = referenceNumber;
    }

    public int getPoint() {
        return this.mPoint;
    }

    public void setPoint(int point) {
        this.mPoint = point;
    }

    public TransactionStatus getStatusType() {
        return this.mStatusType;
    }

    public void setStatusType(TransactionStatus statusType) {
        this.mStatusType = statusType;
    }

    public String getMerchantCode() {
        return this.mMerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.mMerchantCode = merchantCode;
    }

    public String getMerchantName() {
        return this.mMerchantName;
    }

    public void setMerchantName(String merchantName) {
        this.mMerchantName = merchantName;
    }

    public String getMerchantLogoURL() {
        return this.mMerchantLogoURL;
    }

    public void setMerchantLogoURL(String merchantLogoURL) {
        this.mMerchantLogoURL = merchantLogoURL;
    }

    public String getAmount() {
        return this.mAmount;
    }

    public void setAmount(String amount) {
        this.mAmount = amount;
    }

    public String getPaymentId() {
        return this.mPaymentId;
    }

    public void setPaymentId(String paymentId) {
        this.mPaymentId = paymentId;
    }

    public String getDistributorMobileNumber() {
        return this.mDistributorMobileNumber;
    }

    public void setDistributorMobileNumber(String value) {
        this.mDistributorMobileNumber = value;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public String getServerData() {
        return this.mServerData;
    }

    public void setServerData(String serverData) {
        this.mServerData = serverData;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStatusCode);
        dest.writeString(this.mStatusType.toString());
        dest.writeLong(this.mUniqueTranId);
        dest.writeParcelable(this.mCard, flags);
        dest.writeString(this.mHostSuggestCardNo);
        dest.writeInt(this.mCvv2Status.getStatus());
        dest.writeString(this.mAmount);
        dest.writeString(this.mPaymentId);
        dest.writeString(this.mMerchantCode);
        dest.writeString(this.mMerchantName);
        dest.writeString(this.mMerchantLogoURL);
        dest.writeString(this.mServerMessage);
        dest.writeString(this.mReferenceNumber);
        dest.writeString(this.mHostTranId);
        dest.writeString(this.mHostData);
        dest.writeString(this.mHostDataSign);
        dest.writeString(this.mServerData);
        dest.writeString(this.mToken);
        dest.writeInt(this.mPoint);
        dest.writeString(this.mDistributorMobileNumber);
        dest.writeInt(this.mIsAutoSettle ? 1 : 0);
    }

    public void setIsAutoSettle(boolean isAutoSettle) {
        this.mIsAutoSettle = isAutoSettle;
    }

    public boolean isAutoSettle() {
        return this.mIsAutoSettle;
    }

    public boolean isUnknown() {
        return this.mStatusType == TransactionStatus.UNKNOWN && this.mIsAutoSettle;
    }

    public Long getUniqueTranId() {
        return Long.valueOf(this.mUniqueTranId);
    }

    public void setUniqueTranId(long uniqueTranId) {
        this.mUniqueTranId = uniqueTranId;
    }
}
