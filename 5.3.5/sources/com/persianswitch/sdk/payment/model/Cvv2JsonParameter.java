package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.TODO;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class Cvv2JsonParameter {
    public String Amount;
    public String BankId;
    public String Cvv2Force;
    public String HostId;
    public String MerchantCode;
    public String MobileNo;
    public String OpCode;
    public String Source;

    public static class JsonParser implements Jsonable<Cvv2JsonParameter> {
        public JSONObject toJson(Cvv2JsonParameter cvv2JsonParameter) throws JsonWriteException {
            Map<String, Object> param = new HashMap(10);
            if (cvv2JsonParameter.HostId != null) {
                param.put("hid", cvv2JsonParameter.HostId);
            }
            if (cvv2JsonParameter.Cvv2Force != null) {
                param.put("ccv", cvv2JsonParameter.Cvv2Force);
            }
            if (cvv2JsonParameter.OpCode != null) {
                param.put("op", cvv2JsonParameter.OpCode);
            }
            if (cvv2JsonParameter.Amount != null) {
                param.put("am", cvv2JsonParameter.Amount);
            }
            if (cvv2JsonParameter.BankId != null) {
                param.put("bnk", cvv2JsonParameter.BankId);
            }
            if (cvv2JsonParameter.MerchantCode != null) {
                param.put("mrc", cvv2JsonParameter.MerchantCode);
            }
            if (cvv2JsonParameter.Source != null) {
                param.put("src", cvv2JsonParameter.Source);
            }
            if (cvv2JsonParameter.MobileNo != null) {
                param.put("mno", cvv2JsonParameter.MobileNo);
            }
            return Json.toJson(param);
        }

        public JSONObject parseJson(Cvv2JsonParameter instanceObject, String json) throws JsonParseException {
            return (JSONObject) TODO.notImplementedYet();
        }
    }

    public String toJson() {
        try {
            return new JsonParser().toJson(this).toString();
        } catch (JsonWriteException e) {
            return "{}";
        }
    }
}
