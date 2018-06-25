package org.telegram.PhoneFormat;

import java.util.ArrayList;
import java.util.Iterator;

public class CallingCodeInfo {
    public String callingCode = "";
    public ArrayList<String> countries = new ArrayList();
    public ArrayList<String> intlPrefixes = new ArrayList();
    public ArrayList<RuleSet> ruleSets = new ArrayList();
    public ArrayList<String> trunkPrefixes = new ArrayList();

    String matchingAccessCode(String str) {
        Iterator it = this.intlPrefixes.iterator();
        while (it.hasNext()) {
            String code = (String) it.next();
            if (str.startsWith(code)) {
                return code;
            }
        }
        return null;
    }

    String matchingTrunkCode(String str) {
        Iterator it = this.trunkPrefixes.iterator();
        while (it.hasNext()) {
            String code = (String) it.next();
            if (str.startsWith(code)) {
                return code;
            }
        }
        return null;
    }

    String format(String orig) {
        String str = orig;
        String trunkPrefix = null;
        String intlPrefix = null;
        if (str.startsWith(this.callingCode)) {
            intlPrefix = this.callingCode;
            str = str.substring(intlPrefix.length());
        } else {
            String trunk = matchingTrunkCode(str);
            if (trunk != null) {
                trunkPrefix = trunk;
                str = str.substring(trunkPrefix.length());
            }
        }
        Iterator it = this.ruleSets.iterator();
        while (it.hasNext()) {
            String phone = ((RuleSet) it.next()).format(str, intlPrefix, trunkPrefix, true);
            if (phone != null) {
                return phone;
            }
        }
        it = this.ruleSets.iterator();
        while (it.hasNext()) {
            phone = ((RuleSet) it.next()).format(str, intlPrefix, trunkPrefix, false);
            if (phone != null) {
                return phone;
            }
        }
        if (intlPrefix == null || str.length() == 0) {
            return orig;
        }
        return String.format("%s %s", new Object[]{intlPrefix, str});
    }

    boolean isValidPhoneNumber(String orig) {
        String str = orig;
        String trunkPrefix = null;
        String intlPrefix = null;
        if (str.startsWith(this.callingCode)) {
            intlPrefix = this.callingCode;
            str = str.substring(intlPrefix.length());
        } else {
            String trunk = matchingTrunkCode(str);
            if (trunk != null) {
                trunkPrefix = trunk;
                str = str.substring(trunkPrefix.length());
            }
        }
        Iterator it = this.ruleSets.iterator();
        while (it.hasNext()) {
            if (((RuleSet) it.next()).isValid(str, intlPrefix, trunkPrefix, true)) {
                return true;
            }
        }
        it = this.ruleSets.iterator();
        while (it.hasNext()) {
            if (((RuleSet) it.next()).isValid(str, intlPrefix, trunkPrefix, false)) {
                return true;
            }
        }
        return false;
    }
}
