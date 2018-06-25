package org.telegram.PhoneFormat;

public class PhoneRule {
    public int byte8;
    public int flag12;
    public int flag13;
    public String format;
    public boolean hasIntlPrefix;
    public boolean hasTrunkPrefix;
    public int maxLen;
    public int maxVal;
    public int minVal;
    public int otherFlag;
    public int prefixLen;

    String format(String str, String intlPrefix, String trunkPrefix) {
        boolean hadC = false;
        boolean hadN = false;
        boolean hasOpen = false;
        int spot = 0;
        StringBuilder res = new StringBuilder(20);
        int i = 0;
        while (i < this.format.length()) {
            char ch = this.format.charAt(i);
            switch (ch) {
                case '#':
                    if (spot >= str.length()) {
                        if (!hasOpen) {
                            break;
                        }
                        res.append(" ");
                        break;
                    }
                    res.append(str.substring(spot, spot + 1));
                    spot++;
                    continue;
                case '(':
                    if (spot < str.length()) {
                        hasOpen = true;
                        break;
                    }
                    break;
                case 'c':
                    hadC = true;
                    if (intlPrefix != null) {
                        res.append(intlPrefix);
                        break;
                    }
                    continue;
                case 'n':
                    hadN = true;
                    if (trunkPrefix != null) {
                        res.append(trunkPrefix);
                        break;
                    }
                    continue;
            }
            if (!(ch == ' ' && i > 0 && ((this.format.charAt(i - 1) == 'n' && trunkPrefix == null) || (this.format.charAt(i - 1) == 'c' && intlPrefix == null))) && (spot < str.length() || (hasOpen && ch == ')'))) {
                res.append(this.format.substring(i, i + 1));
                if (ch == ')') {
                    hasOpen = false;
                }
            }
            i++;
        }
        if (intlPrefix != null && !hadC) {
            res.insert(0, String.format("%s ", new Object[]{intlPrefix}));
        } else if (!(trunkPrefix == null || hadN)) {
            res.insert(0, trunkPrefix);
        }
        return res.toString();
    }

    boolean hasIntlPrefix() {
        return (this.flag12 & 2) != 0;
    }

    boolean hasTrunkPrefix() {
        return (this.flag12 & 1) != 0;
    }
}
