package utils.view;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import utils.app.AppPreferences;

public final class FontUtil {
    public static String[] FONTS = new String[]{"ایرانسنس", "پیش فرض تلگرام", "تنها", "ساحل ", "مروارید"};
    private static final int N_DISTINCT_CHARACTERS = 43;
    static struc[] arrStruc = new struc[]{new struc('ذ', 'ﺬ', 'ﺫ', 'ﺬ', 'ﺫ'), new struc('د', 'ﺪ', 'ﺩ', 'ﺪ', 'ﺩ'), new struc('ج', 'ﺞ', 'ﺟ', 'ﺠ', 'ﺝ'), new struc('ح', 'ﺢ', 'ﺣ', 'ﺤ', 'ﺡ'), new struc('خ', 'ﺦ', 'ﺧ', 'ﺨ', 'ﺥ'), new struc('ه', 'ﻪ', 'ﻫ', 'ﻬ', 'ﻩ'), new struc('ع', 'ﻊ', 'ﻋ', 'ﻌ', 'ﻉ'), new struc('غ', 'ﻎ', 'ﻏ', 'ﻐ', 'ﻍ'), new struc('ف', 'ﻒ', 'ﻓ', 'ﻔ', 'ﻑ'), new struc('ق', 'ﻖ', 'ﻗ', 'ﻘ', 'ﻕ'), new struc('ث', 'ﺚ', 'ﺛ', 'ﺜ', 'ﺙ'), new struc('ص', 'ﺺ', 'ﺻ', 'ﺼ', 'ﺹ'), new struc('ض', 'ﺾ', 'ﺿ', 'ﻀ', 'ﺽ'), new struc('ط', 'ﻂ', 'ﻃ', 'ﻄ', 'ﻁ'), new struc('ك', 'ﻚ', 'ﻛ', 'ﻜ', 'ﻙ'), new struc('م', 'ﻢ', 'ﻣ', 'ﻤ', 'ﻡ'), new struc('ن', 'ﻦ', 'ﻧ', 'ﻨ', 'ﻥ'), new struc('ت', 'ﺖ', 'ﺗ', 'ﺘ', 'ﺕ'), new struc('ا', 'ﺎ', 'ﺍ', 'ﺎ', 'ﺍ'), new struc('ل', 'ﻞ', 'ﻟ', 'ﻠ', 'ﻝ'), new struc('ب', 'ﺐ', 'ﺑ', 'ﺒ', 'ﺏ'), new struc('ي', 'ﻲ', 'ﻳ', 'ﻴ', 'ﻱ'), new struc('س', 'ﺲ', 'ﺳ', 'ﺴ', 'ﺱ'), new struc('ش', 'ﺶ', 'ﺷ', 'ﺸ', 'ﺵ'), new struc('ظ', 'ﻆ', 'ﻇ', 'ﻈ', 'ﻅ'), new struc('ز', 'ﺰ', 'ﺯ', 'ﺰ', 'ﺯ'), new struc('و', 'ﻮ', 'ﻭ', 'ﻮ', 'ﻭ'), new struc('ة', 'ﺔ', 'ﺓ', 'ﺓ', 'ﺓ'), new struc('ى', 'ﻰ', 'ﻯ', 'ﻰ', 'ﻯ'), new struc('ر', 'ﺮ', 'ﺭ', 'ﺮ', 'ﺭ'), new struc('ؤ', 'ﺆ', 'ﺅ', 'ﺆ', 'ﺅ'), new struc('ء', 'ﺀ', 'ﺀ', 'ﺀ', 'ﺀ'), new struc('ئ', 'ﺊ', 'ﺋ', 'ﺌ', 'ﺉ'), new struc('أ', 'ﺄ', 'ﺃ', 'ﺄ', 'ﺃ'), new struc('آ', 'ﺂ', 'ﺁ', 'ﺂ', 'ﺁ'), new struc('إ', 'ﺈ', 'ﺇ', 'ﺈ', 'ﺇ'), new struc('پ', 'ﭗ', 'ﭘ', 'ﭙ', 'ﭖ'), new struc('چ', 'ﭻ', 'ﭼ', 'ﭽ', 'ﭺ'), new struc('ژ', 'ﮋ', 'ﮊ', 'ﮋ', 'ﮊ'), new struc('ک', 'ﮏ', 'ﮐ', 'ﮑ', 'ﮎ'), new struc('گ', 'ﮓ', 'ﮔ', 'ﮕ', 'ﮒ'), new struc('ی', 'ﯽ', 'ﻳ', 'ﻴ', 'ﯼ'), new struc('ۀ', 'ﮥ', 'ﮤ', 'ﮥ', 'ﮤ')};
    static struc[] arrStrucWoosim = new struc[]{new struc('ذ', 'µ', '', 'µ', ''), new struc('د', '´', '', '´', ''), new struc('ج', '', '±', 'ù', '¿'), new struc('ح', '', '²', 'ú', 'À'), new struc('خ', '', '³', 'þ', 'Á'), new struc('ه', '¬', 'ä', '', 'Õ'), new struc('ع', 'É', 'Ó', '', '¤'), new struc('غ', 'Ê', 'Ý', '', '¥'), new struc('ف', '¦', 'Þ', '', 'Ì'), new struc('ق', '§', 'ß', '', 'Î'), new struc('ث', '½', '¯', 'ê', ''), new struc('ص', 'Ä', 'È', '', ' '), new struc('ض', 'Å', 'Ë', '', '¡'), new struc('ط', 'Æ', 'Í', 'Í', '¢'), new struc('ك', 'Ï', 'à', '', '¨'), new struc('م', 'Ò', 'â', '', 'ª'), new struc('ن', 'Ô', 'ã', '', '«'), new struc('ت', '½', '¯', 'ê', ''), new struc('ا', '»', '', '»', ''), new struc('ل', 'Ñ', 'á', '', '©'), new struc('ب', '¼', '®', 'é', ''), new struc('ي', 'Ü', 'æ', '', 'Ü'), new struc('س', 'Â', '¸', '¸', ''), new struc('ش', 'Ã', '¹', '¹', ''), new struc('ظ', 'Ç', 'Í', 'Í', 'Ç'), new struc('ز', '·', '·', '·', '·'), new struc('و', '', '', '', ''), new struc('ة', 'Ú', 'Ú', 'Ú', 'Ú'), new struc('ى', 'Ü', 'æ', '', 'Ü'), new struc('ر', '¶', '¶', '¶', '¶'), new struc('ؤ', 'ç', 'ç', 'ç', 'ç'), new struc('ء', 'º', 'º', 'º', 'º'), new struc('ئ', '×', 'è', '', '×'), new struc('أ', '', '', '', ''), new struc('آ', '', '', '', ''), new struc('إ', '', '', '', ''), new struc('پ', '¼', '®', 'é', ''), new struc('چ', '', '±', 'ù', '¿'), new struc('ژ', '·', '·', '·', '·'), new struc('ک', 'Ï', 'à', '', '¨'), new struc('گ', 'Ï', 'à', '', '¨'), new struc('ی', 'Ü', 'æ', '', 'Ü'), new struc('ۀ', '¬', 'ä', '', 'Õ')};
    public static boolean isFarsiConversionNeeded = true;
    private static final String szLa = Character.toString('ﻻ');
    private static final String szLaStick = Character.toString('ﻼ');
    private static final String szLamAndAlef = (Character.toString('ﻟ') + Character.toString('ﺎ'));
    private static final String szLamStickAndAlef = (Character.toString('ﻠ') + Character.toString('ﺎ'));
    private static Typeface typeface;

    private static final class struc {
        public char character;
        public char endGlyph;
        public char iniGlyph;
        public char isoGlyph;
        public char midGlyph;

        public struc(char Character, char EndGlyph, char IniGlyph, char MidGlyph, char IsoGlyph) {
            this.character = Character;
            this.endGlyph = EndGlyph;
            this.iniGlyph = IniGlyph;
            this.midGlyph = MidGlyph;
            this.isoGlyph = IsoGlyph;
        }
    }

    private static final boolean isFromTheSet1(char ch) {
        char[] theSet1 = new char[]{'ج', 'ح', 'خ', 'ه', 'ع', 'غ', 'ف', 'ق', 'ث', 'ص', 'ض', 'ط', 'ك', 'م', 'ن', 'ت', 'ل', 'ب', 'ي', 'س', 'ش', 'ظ', 'پ', 'چ', 'ک', 'گ', 'ی', 'ئ'};
        for (int i = 0; i < 28; i++) {
            if (ch == theSet1[i]) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isFromTheSet2(char ch) {
        char[] theSet2 = new char[]{'ا', 'أ', 'إ', 'آ', 'د', 'ذ', 'ر', 'ز', 'و', 'ؤ', 'ة', 'ى', 'ژ', 'ۀ'};
        for (int i = 0; i < 14; i++) {
            if (ch == theSet2[i]) {
                return true;
            }
        }
        return false;
    }

    public static final String ConvertBackToRealFarsi(String In) {
        if (!isFarsiConversionNeeded) {
            return In;
        }
        String strOut = "";
        StringBuilder strBuilder = new StringBuilder("");
        char[] cArr = new char[In.length()];
        cArr = In.toCharArray();
        int i = 0;
        while (i < In.length()) {
            boolean found = false;
            int j = 0;
            while (j < arrStruc.length) {
                if (cArr[i] == arrStruc[j].midGlyph || cArr[i] == arrStruc[j].iniGlyph || cArr[i] == arrStruc[j].endGlyph || cArr[i] == arrStruc[j].isoGlyph) {
                    strBuilder.append(arrStruc[j].character);
                    found = true;
                    break;
                }
                j++;
            }
            if (!found) {
                strBuilder.append(cArr[i]);
            }
            i++;
        }
        return strBuilder.toString().replace(szLa, "لا").replace(szLaStick, "لا");
    }

    public static final String Convert(String In) {
        if (!isFarsiConversionNeeded) {
            return In;
        }
        if (In == null) {
            return "";
        }
        String Out = In;
        char[] cArr = new char[Out.length()];
        cArr = Out.toCharArray();
        char[] cArr2 = new char[In.length()];
        cArr2 = In.toCharArray();
        int i = 0;
        while (i < In.length()) {
            char ch = cArr2[i];
            if ((ch >= 'ء' && ch <= 'ي') || ch == 'پ' || ch == 'چ' || ch == 'ژ' || ch == 'ک' || ch == 'گ' || ch == 'ی' || ch == 'ۀ') {
                boolean linkBefore;
                int idx = 0;
                while (idx < 43 && arrStruc[idx].character != cArr2[i]) {
                    idx++;
                }
                boolean linkAfter;
                if (i == In.length() - 1) {
                    linkAfter = false;
                } else {
                    linkAfter = isFromTheSet1(cArr2[i + 1]) || isFromTheSet2(cArr2[i + 1]);
                }
                if (i == 0) {
                    linkBefore = false;
                } else {
                    linkBefore = isFromTheSet1(cArr2[i - 1]);
                }
                if (idx < 43) {
                    if (linkBefore && linkAfter) {
                        cArr[i] = arrStruc[idx].midGlyph;
                    }
                    if (linkBefore && !linkAfter) {
                        cArr[i] = arrStruc[idx].endGlyph;
                    }
                    if (!linkBefore && linkAfter) {
                        cArr[i] = arrStruc[idx].iniGlyph;
                    }
                    if (!(linkBefore || linkAfter)) {
                        cArr[i] = arrStruc[idx].isoGlyph;
                    }
                } else {
                    cArr[i] = cArr2[i];
                }
            } else {
                cArr[i] = cArr2[i];
            }
            i++;
        }
        Out = "";
        for (char c : cArr) {
            Out = Out + c;
        }
        return Out.replace('‌', ' ').replace(szLamAndAlef, szLa).replace(szLamStickAndAlef, szLaStick);
    }

    private static final String reorderWords(String strIn) {
        String strOut = "";
        String prevWord = "";
        int state = 0;
        char[] arr = strIn.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (charIsLTR(arr[i]) && state != 1) {
                state = 1;
                strOut = prevWord + strOut;
                prevWord = "" + arr[i];
            } else if (!charIsRTL(arr[i]) || state == 0) {
                prevWord = prevWord + arr[i];
            } else {
                state = 0;
                strOut = prevWord + strOut;
                prevWord = "" + arr[i];
            }
        }
        return prevWord + strOut;
    }

    private static final boolean charIsLTR(char ch) {
        int i;
        int i2 = 1;
        if (ch >= 'A') {
            i = 1;
        } else {
            i = 0;
        }
        if (ch > 'z') {
            i2 = 0;
        }
        return (i2 & i) | Character.isDigit(ch);
    }

    private static final boolean charIsRTL(char ch) {
        return ch >= 'ء';
    }

    public static final Typeface GetFarsiFont(Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), getDeafultFontAddress());
        }
        return typeface;
    }

    public static String getDeafultFontAddress() {
        int selected = AppPreferences.getSelectedFont(ApplicationLoader.applicationContext);
        if (selected == 0) {
            if (LocaleController.getCurrentLanguageName().contentEquals("فارسی")) {
                return "fonts/IRANSansMobile_Light.ttf";
            }
            return "fonts/IRANSans.ttf";
        } else if (selected == 1) {
            return "fonts/rmedium.ttf";
        } else {
            if (selected == 2) {
                if (LocaleController.getCurrentLanguageName().contentEquals("فارسی")) {
                    return "fonts/Tanha-FD.ttf";
                }
                return "fonts/Tanha.ttf";
            } else if (selected != 3) {
                return "fonts/morvarid.ttf";
            } else {
                if (LocaleController.getCurrentLanguageName().contentEquals("فارسی")) {
                    return "fonts/Sahel-FD.ttf";
                }
                return "fonts/Sahel.ttf";
            }
        }
    }

    public static String getDeafultTitleFontAddress() {
        return "fonts/IRANSansMobile_Medium.ttf";
    }

    public static void setFont(Context context, TextView[] tvs) {
        Typeface Adobe = Typeface.createFromAsset(context.getAssets(), getDeafultFontAddress());
        for (TextView tv : tvs) {
            tv.setTypeface(Adobe);
        }
    }
}
