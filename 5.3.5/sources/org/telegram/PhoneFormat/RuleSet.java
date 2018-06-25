package org.telegram.PhoneFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleSet {
    public static Pattern pattern = Pattern.compile("[0-9]+");
    public boolean hasRuleWithIntlPrefix;
    public boolean hasRuleWithTrunkPrefix;
    public int matchLen;
    public ArrayList<PhoneRule> rules = new ArrayList();

    String format(String str, String intlPrefix, String trunkPrefix, boolean prefixRequired) {
        if (str.length() < this.matchLen) {
            return null;
        }
        int val = 0;
        Matcher matcher = pattern.matcher(str.substring(0, this.matchLen));
        if (matcher.find()) {
            val = Integer.parseInt(matcher.group(0));
        }
        Iterator it = this.rules.iterator();
        while (it.hasNext()) {
            PhoneRule rule = (PhoneRule) it.next();
            if (val >= rule.minVal && val <= rule.maxVal && str.length() <= rule.maxLen) {
                if (prefixRequired) {
                    if (((rule.flag12 & 3) == 0 && trunkPrefix == null && intlPrefix == null) || !((trunkPrefix == null || (rule.flag12 & 1) == 0) && (intlPrefix == null || (rule.flag12 & 2) == 0))) {
                        return rule.format(str, intlPrefix, trunkPrefix);
                    }
                } else if ((trunkPrefix == null && intlPrefix == null) || !((trunkPrefix == null || (rule.flag12 & 1) == 0) && (intlPrefix == null || (rule.flag12 & 2) == 0))) {
                    return rule.format(str, intlPrefix, trunkPrefix);
                }
            }
        }
        if (prefixRequired) {
            return null;
        }
        if (intlPrefix != null) {
            it = this.rules.iterator();
            while (it.hasNext()) {
                rule = (PhoneRule) it.next();
                if (val >= rule.minVal && val <= rule.maxVal && str.length() <= rule.maxLen) {
                    if (trunkPrefix == null || (rule.flag12 & 1) != 0) {
                        return rule.format(str, intlPrefix, trunkPrefix);
                    }
                }
            }
            return null;
        } else if (trunkPrefix == null) {
            return null;
        } else {
            it = this.rules.iterator();
            while (it.hasNext()) {
                rule = (PhoneRule) it.next();
                if (val >= rule.minVal && val <= rule.maxVal && str.length() <= rule.maxLen) {
                    if (intlPrefix == null || (rule.flag12 & 2) != 0) {
                        return rule.format(str, intlPrefix, trunkPrefix);
                    }
                }
            }
            return null;
        }
    }

    boolean isValid(String str, String intlPrefix, String trunkPrefix, boolean prefixRequired) {
        if (str.length() < this.matchLen) {
            return false;
        }
        int val = 0;
        Matcher matcher = pattern.matcher(str.substring(0, this.matchLen));
        if (matcher.find()) {
            val = Integer.parseInt(matcher.group(0));
        }
        Iterator it = this.rules.iterator();
        while (it.hasNext()) {
            PhoneRule rule = (PhoneRule) it.next();
            if (val >= rule.minVal && val <= rule.maxVal && str.length() == rule.maxLen) {
                if (prefixRequired) {
                    if ((rule.flag12 & 3) == 0 && trunkPrefix == null && intlPrefix == null) {
                        return true;
                    }
                    if (trunkPrefix != null && (rule.flag12 & 1) != 0) {
                        return true;
                    }
                    if (!(intlPrefix == null || (rule.flag12 & 2) == 0)) {
                        return true;
                    }
                } else if (trunkPrefix == null && intlPrefix == null) {
                    return true;
                } else {
                    if (trunkPrefix != null && (rule.flag12 & 1) != 0) {
                        return true;
                    }
                    if (!(intlPrefix == null || (rule.flag12 & 2) == 0)) {
                        return true;
                    }
                }
            }
        }
        if (!prefixRequired) {
            if (intlPrefix != null && !this.hasRuleWithIntlPrefix) {
                it = this.rules.iterator();
                while (it.hasNext()) {
                    rule = (PhoneRule) it.next();
                    if (val >= rule.minVal && val <= rule.maxVal && str.length() == rule.maxLen) {
                        if (trunkPrefix == null) {
                            return true;
                        }
                        if ((rule.flag12 & 1) != 0) {
                            return true;
                        }
                    }
                }
            } else if (!(trunkPrefix == null || this.hasRuleWithTrunkPrefix)) {
                it = this.rules.iterator();
                while (it.hasNext()) {
                    rule = (PhoneRule) it.next();
                    if (val >= rule.minVal && val <= rule.maxVal && str.length() == rule.maxLen) {
                        if (intlPrefix == null) {
                            return true;
                        }
                        if ((rule.flag12 & 2) != 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
