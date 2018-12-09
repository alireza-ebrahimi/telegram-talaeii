package org.telegram.customization.Model.Payment;

public class PaymentLink {
    String link;
    String paymentId;

    public String getLink() {
        return this.link;
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public void setPaymentId(String str) {
        this.paymentId = str;
    }
}
