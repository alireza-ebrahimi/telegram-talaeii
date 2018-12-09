package org.telegram.customization.Model.Payment;

public class HostResponseData {
    String hresp;
    String hsign;

    public String getHresp() {
        return this.hresp;
    }

    public String getHsign() {
        return this.hsign;
    }

    public void setHresp(String str) {
        this.hresp = str;
    }

    public void setHsign(String str) {
        this.hsign = str;
    }
}
