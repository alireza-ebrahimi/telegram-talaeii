package utils.view;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import utils.p178a.C3791b;

public final class FontUtil {
    /* renamed from: a */
    public static boolean f10280a = true;
    /* renamed from: b */
    static struc[] f10281b = new struc[]{new struc('ذ', 'ﺬ', 'ﺫ', 'ﺬ', 'ﺫ'), new struc('د', 'ﺪ', 'ﺩ', 'ﺪ', 'ﺩ'), new struc('ج', 'ﺞ', 'ﺟ', 'ﺠ', 'ﺝ'), new struc('ح', 'ﺢ', 'ﺣ', 'ﺤ', 'ﺡ'), new struc('خ', 'ﺦ', 'ﺧ', 'ﺨ', 'ﺥ'), new struc('ه', 'ﻪ', 'ﻫ', 'ﻬ', 'ﻩ'), new struc('ع', 'ﻊ', 'ﻋ', 'ﻌ', 'ﻉ'), new struc('غ', 'ﻎ', 'ﻏ', 'ﻐ', 'ﻍ'), new struc('ف', 'ﻒ', 'ﻓ', 'ﻔ', 'ﻑ'), new struc('ق', 'ﻖ', 'ﻗ', 'ﻘ', 'ﻕ'), new struc('ث', 'ﺚ', 'ﺛ', 'ﺜ', 'ﺙ'), new struc('ص', 'ﺺ', 'ﺻ', 'ﺼ', 'ﺹ'), new struc('ض', 'ﺾ', 'ﺿ', 'ﻀ', 'ﺽ'), new struc('ط', 'ﻂ', 'ﻃ', 'ﻄ', 'ﻁ'), new struc('ك', 'ﻚ', 'ﻛ', 'ﻜ', 'ﻙ'), new struc('م', 'ﻢ', 'ﻣ', 'ﻤ', 'ﻡ'), new struc('ن', 'ﻦ', 'ﻧ', 'ﻨ', 'ﻥ'), new struc('ت', 'ﺖ', 'ﺗ', 'ﺘ', 'ﺕ'), new struc('ا', 'ﺎ', 'ﺍ', 'ﺎ', 'ﺍ'), new struc('ل', 'ﻞ', 'ﻟ', 'ﻠ', 'ﻝ'), new struc('ب', 'ﺐ', 'ﺑ', 'ﺒ', 'ﺏ'), new struc('ي', 'ﻲ', 'ﻳ', 'ﻴ', 'ﻱ'), new struc('س', 'ﺲ', 'ﺳ', 'ﺴ', 'ﺱ'), new struc('ش', 'ﺶ', 'ﺷ', 'ﺸ', 'ﺵ'), new struc('ظ', 'ﻆ', 'ﻇ', 'ﻈ', 'ﻅ'), new struc('ز', 'ﺰ', 'ﺯ', 'ﺰ', 'ﺯ'), new struc('و', 'ﻮ', 'ﻭ', 'ﻮ', 'ﻭ'), new struc('ة', 'ﺔ', 'ﺓ', 'ﺓ', 'ﺓ'), new struc('ى', 'ﻰ', 'ﻯ', 'ﻰ', 'ﻯ'), new struc('ر', 'ﺮ', 'ﺭ', 'ﺮ', 'ﺭ'), new struc('ؤ', 'ﺆ', 'ﺅ', 'ﺆ', 'ﺅ'), new struc('ء', 'ﺀ', 'ﺀ', 'ﺀ', 'ﺀ'), new struc('ئ', 'ﺊ', 'ﺋ', 'ﺌ', 'ﺉ'), new struc('أ', 'ﺄ', 'ﺃ', 'ﺄ', 'ﺃ'), new struc('آ', 'ﺂ', 'ﺁ', 'ﺂ', 'ﺁ'), new struc('إ', 'ﺈ', 'ﺇ', 'ﺈ', 'ﺇ'), new struc('پ', 'ﭗ', 'ﭘ', 'ﭙ', 'ﭖ'), new struc('چ', 'ﭻ', 'ﭼ', 'ﭽ', 'ﭺ'), new struc('ژ', 'ﮋ', 'ﮊ', 'ﮋ', 'ﮊ'), new struc('ک', 'ﮏ', 'ﮐ', 'ﮑ', 'ﮎ'), new struc('گ', 'ﮓ', 'ﮔ', 'ﮕ', 'ﮒ'), new struc('ی', 'ﯽ', 'ﻳ', 'ﻴ', 'ﯼ'), new struc('ۀ', 'ﮥ', 'ﮤ', 'ﮥ', 'ﮤ')};
    /* renamed from: c */
    static struc[] f10282c = new struc[]{new struc('ذ', 'µ', '', 'µ', ''), new struc('د', '´', '', '´', ''), new struc('ج', '', '±', 'ù', '¿'), new struc('ح', '', '²', 'ú', 'À'), new struc('خ', '', '³', 'þ', 'Á'), new struc('ه', '¬', 'ä', '', 'Õ'), new struc('ع', 'É', 'Ó', '', '¤'), new struc('غ', 'Ê', 'Ý', '', '¥'), new struc('ف', '¦', 'Þ', '', 'Ì'), new struc('ق', '§', 'ß', '', 'Î'), new struc('ث', '½', '¯', 'ê', ''), new struc('ص', 'Ä', 'È', '', ' '), new struc('ض', 'Å', 'Ë', '', '¡'), new struc('ط', 'Æ', 'Í', 'Í', '¢'), new struc('ك', 'Ï', 'à', '', '¨'), new struc('م', 'Ò', 'â', '', 'ª'), new struc('ن', 'Ô', 'ã', '', '«'), new struc('ت', '½', '¯', 'ê', ''), new struc('ا', '»', '', '»', ''), new struc('ل', 'Ñ', 'á', '', '©'), new struc('ب', '¼', '®', 'é', ''), new struc('ي', 'Ü', 'æ', '', 'Ü'), new struc('س', 'Â', '¸', '¸', ''), new struc('ش', 'Ã', '¹', '¹', ''), new struc('ظ', 'Ç', 'Í', 'Í', 'Ç'), new struc('ز', '·', '·', '·', '·'), new struc('و', '', '', '', ''), new struc('ة', 'Ú', 'Ú', 'Ú', 'Ú'), new struc('ى', 'Ü', 'æ', '', 'Ü'), new struc('ر', '¶', '¶', '¶', '¶'), new struc('ؤ', 'ç', 'ç', 'ç', 'ç'), new struc('ء', 'º', 'º', 'º', 'º'), new struc('ئ', '×', 'è', '', '×'), new struc('أ', '', '', '', ''), new struc('آ', '', '', '', ''), new struc('إ', '', '', '', ''), new struc('پ', '¼', '®', 'é', ''), new struc('چ', '', '±', 'ù', '¿'), new struc('ژ', '·', '·', '·', '·'), new struc('ک', 'Ï', 'à', '', '¨'), new struc('گ', 'Ï', 'à', '', '¨'), new struc('ی', 'Ü', 'æ', '', 'Ü'), new struc('ۀ', '¬', 'ä', '', 'Õ')};
    /* renamed from: d */
    public static String[] f10283d = new String[]{"ایرانسنس", "پیش فرض تلگرام", "تنها", "ساحل ", "مروارید"};
    /* renamed from: e */
    private static final String f10284e = (Character.toString('ﻟ') + Character.toString('ﺎ'));
    /* renamed from: f */
    private static final String f10285f = (Character.toString('ﻠ') + Character.toString('ﺎ'));
    /* renamed from: g */
    private static final String f10286g = Character.toString('ﻻ');
    /* renamed from: h */
    private static final String f10287h = Character.toString('ﻼ');

    private static final class struc {
        /* renamed from: a */
        public char f10275a;
        /* renamed from: b */
        public char f10276b;
        /* renamed from: c */
        public char f10277c;
        /* renamed from: d */
        public char f10278d;
        /* renamed from: e */
        public char f10279e;

        public struc(char c, char c2, char c3, char c4, char c5) {
            this.f10275a = c;
            this.f10276b = c2;
            this.f10277c = c3;
            this.f10278d = c4;
            this.f10279e = c5;
        }
    }

    /* renamed from: a */
    public static String m14170a() {
        int w = C3791b.w(ApplicationLoader.applicationContext);
        return w == 0 ? LocaleController.getCurrentLanguageName().contentEquals("فارسی") ? "fonts/IRANSansMobile_Light.ttf" : "fonts/IRANSans.ttf" : w == 1 ? "fonts/rmedium.ttf" : w == 2 ? LocaleController.getCurrentLanguageName().contentEquals("فارسی") ? "fonts/Tanha-FD.ttf" : "fonts/Tanha.ttf" : w == 3 ? LocaleController.getCurrentLanguageName().contentEquals("فارسی") ? "fonts/Sahel-FD.ttf" : "fonts/Sahel.ttf" : "fonts/morvarid.ttf";
    }

    /* renamed from: b */
    public static String m14171b() {
        return "fonts/IRANSansMobile_Medium.ttf";
    }
}
