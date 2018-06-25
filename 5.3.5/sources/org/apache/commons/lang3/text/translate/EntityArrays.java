package org.apache.commons.lang3.text.translate;

import android.support.v4.media.TransportMediator;
import com.persianswitch.sdk.base.log.LogCollector;
import java.lang.reflect.Array;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import utils.view.tagview.Constants;

public class EntityArrays {
    private static final String[][] APOS_ESCAPE;
    private static final String[][] APOS_UNESCAPE = invert(APOS_ESCAPE);
    private static final String[][] BASIC_ESCAPE;
    private static final String[][] BASIC_UNESCAPE = invert(BASIC_ESCAPE);
    private static final String[][] HTML40_EXTENDED_ESCAPE;
    private static final String[][] HTML40_EXTENDED_UNESCAPE = invert(HTML40_EXTENDED_ESCAPE);
    private static final String[][] ISO8859_1_ESCAPE;
    private static final String[][] ISO8859_1_UNESCAPE = invert(ISO8859_1_ESCAPE);
    private static final String[][] JAVA_CTRL_CHARS_ESCAPE;
    private static final String[][] JAVA_CTRL_CHARS_UNESCAPE = invert(JAVA_CTRL_CHARS_ESCAPE);

    public static String[][] ISO8859_1_ESCAPE() {
        return (String[][]) ISO8859_1_ESCAPE.clone();
    }

    static {
        r0 = new String[96][];
        r0[0] = new String[]{" ", "&nbsp;"};
        r0[1] = new String[]{"¡", "&iexcl;"};
        r0[2] = new String[]{"¢", "&cent;"};
        r0[3] = new String[]{"£", "&pound;"};
        r0[4] = new String[]{"¤", "&curren;"};
        r0[5] = new String[]{"¥", "&yen;"};
        r0[6] = new String[]{"¦", "&brvbar;"};
        r0[7] = new String[]{"§", "&sect;"};
        r0[8] = new String[]{"¨", "&uml;"};
        r0[9] = new String[]{"©", "&copy;"};
        r0[10] = new String[]{"ª", "&ordf;"};
        r0[11] = new String[]{"«", "&laquo;"};
        r0[12] = new String[]{"¬", "&not;"};
        r0[13] = new String[]{"­", "&shy;"};
        r0[14] = new String[]{"®", "&reg;"};
        r0[15] = new String[]{"¯", "&macr;"};
        r0[16] = new String[]{"°", "&deg;"};
        r0[17] = new String[]{"±", "&plusmn;"};
        r0[18] = new String[]{"²", "&sup2;"};
        r0[19] = new String[]{"³", "&sup3;"};
        r0[20] = new String[]{"´", "&acute;"};
        r0[21] = new String[]{"µ", "&micro;"};
        r0[22] = new String[]{"¶", "&para;"};
        r0[23] = new String[]{"·", "&middot;"};
        r0[24] = new String[]{"¸", "&cedil;"};
        r0[25] = new String[]{"¹", "&sup1;"};
        r0[26] = new String[]{"º", "&ordm;"};
        r0[27] = new String[]{"»", "&raquo;"};
        r0[28] = new String[]{"¼", "&frac14;"};
        r0[29] = new String[]{"½", "&frac12;"};
        r0[30] = new String[]{"¾", "&frac34;"};
        r0[31] = new String[]{"¿", "&iquest;"};
        r0[32] = new String[]{"À", "&Agrave;"};
        r0[33] = new String[]{"Á", "&Aacute;"};
        r0[34] = new String[]{"Â", "&Acirc;"};
        r0[35] = new String[]{"Ã", "&Atilde;"};
        r0[36] = new String[]{"Ä", "&Auml;"};
        r0[37] = new String[]{"Å", "&Aring;"};
        r0[38] = new String[]{"Æ", "&AElig;"};
        r0[39] = new String[]{"Ç", "&Ccedil;"};
        r0[40] = new String[]{"È", "&Egrave;"};
        r0[41] = new String[]{"É", "&Eacute;"};
        r0[42] = new String[]{"Ê", "&Ecirc;"};
        r0[43] = new String[]{"Ë", "&Euml;"};
        r0[44] = new String[]{"Ì", "&Igrave;"};
        r0[45] = new String[]{"Í", "&Iacute;"};
        r0[46] = new String[]{"Î", "&Icirc;"};
        r0[47] = new String[]{"Ï", "&Iuml;"};
        r0[48] = new String[]{"Ð", "&ETH;"};
        r0[49] = new String[]{"Ñ", "&Ntilde;"};
        r0[50] = new String[]{"Ò", "&Ograve;"};
        r0[51] = new String[]{"Ó", "&Oacute;"};
        r0[52] = new String[]{"Ô", "&Ocirc;"};
        r0[53] = new String[]{"Õ", "&Otilde;"};
        r0[54] = new String[]{"Ö", "&Ouml;"};
        r0[55] = new String[]{Constants.DEFAULT_TAG_DELETE_ICON, "&times;"};
        r0[56] = new String[]{"Ø", "&Oslash;"};
        r0[57] = new String[]{"Ù", "&Ugrave;"};
        r0[58] = new String[]{"Ú", "&Uacute;"};
        r0[59] = new String[]{"Û", "&Ucirc;"};
        r0[60] = new String[]{"Ü", "&Uuml;"};
        r0[61] = new String[]{"Ý", "&Yacute;"};
        r0[62] = new String[]{"Þ", "&THORN;"};
        r0[63] = new String[]{"ß", "&szlig;"};
        r0[64] = new String[]{"à", "&agrave;"};
        r0[65] = new String[]{"á", "&aacute;"};
        r0[66] = new String[]{"â", "&acirc;"};
        r0[67] = new String[]{"ã", "&atilde;"};
        r0[68] = new String[]{"ä", "&auml;"};
        r0[69] = new String[]{"å", "&aring;"};
        r0[70] = new String[]{"æ", "&aelig;"};
        r0[71] = new String[]{"ç", "&ccedil;"};
        r0[72] = new String[]{"è", "&egrave;"};
        r0[73] = new String[]{"é", "&eacute;"};
        r0[74] = new String[]{"ê", "&ecirc;"};
        r0[75] = new String[]{"ë", "&euml;"};
        r0[76] = new String[]{"ì", "&igrave;"};
        r0[77] = new String[]{"í", "&iacute;"};
        r0[78] = new String[]{"î", "&icirc;"};
        r0[79] = new String[]{"ï", "&iuml;"};
        r0[80] = new String[]{"ð", "&eth;"};
        r0[81] = new String[]{"ñ", "&ntilde;"};
        r0[82] = new String[]{"ò", "&ograve;"};
        r0[83] = new String[]{"ó", "&oacute;"};
        r0[84] = new String[]{"ô", "&ocirc;"};
        r0[85] = new String[]{"õ", "&otilde;"};
        r0[86] = new String[]{"ö", "&ouml;"};
        r0[87] = new String[]{"÷", "&divide;"};
        r0[88] = new String[]{"ø", "&oslash;"};
        r0[89] = new String[]{"ù", "&ugrave;"};
        r0[90] = new String[]{"ú", "&uacute;"};
        r0[91] = new String[]{"û", "&ucirc;"};
        r0[92] = new String[]{"ü", "&uuml;"};
        r0[93] = new String[]{"ý", "&yacute;"};
        r0[94] = new String[]{"þ", "&thorn;"};
        r0[95] = new String[]{"ÿ", "&yuml;"};
        ISO8859_1_ESCAPE = r0;
        r0 = new String[151][];
        r0[0] = new String[]{"ƒ", "&fnof;"};
        r0[1] = new String[]{"Α", "&Alpha;"};
        r0[2] = new String[]{"Β", "&Beta;"};
        r0[3] = new String[]{"Γ", "&Gamma;"};
        r0[4] = new String[]{"Δ", "&Delta;"};
        r0[5] = new String[]{"Ε", "&Epsilon;"};
        r0[6] = new String[]{"Ζ", "&Zeta;"};
        r0[7] = new String[]{"Η", "&Eta;"};
        r0[8] = new String[]{"Θ", "&Theta;"};
        r0[9] = new String[]{"Ι", "&Iota;"};
        r0[10] = new String[]{"Κ", "&Kappa;"};
        r0[11] = new String[]{"Λ", "&Lambda;"};
        r0[12] = new String[]{"Μ", "&Mu;"};
        r0[13] = new String[]{"Ν", "&Nu;"};
        r0[14] = new String[]{"Ξ", "&Xi;"};
        r0[15] = new String[]{"Ο", "&Omicron;"};
        r0[16] = new String[]{"Π", "&Pi;"};
        r0[17] = new String[]{"Ρ", "&Rho;"};
        r0[18] = new String[]{"Σ", "&Sigma;"};
        r0[19] = new String[]{"Τ", "&Tau;"};
        r0[20] = new String[]{"Υ", "&Upsilon;"};
        r0[21] = new String[]{"Φ", "&Phi;"};
        r0[22] = new String[]{"Χ", "&Chi;"};
        r0[23] = new String[]{"Ψ", "&Psi;"};
        r0[24] = new String[]{"Ω", "&Omega;"};
        r0[25] = new String[]{"α", "&alpha;"};
        r0[26] = new String[]{"β", "&beta;"};
        r0[27] = new String[]{"γ", "&gamma;"};
        r0[28] = new String[]{"δ", "&delta;"};
        r0[29] = new String[]{"ε", "&epsilon;"};
        r0[30] = new String[]{"ζ", "&zeta;"};
        r0[31] = new String[]{"η", "&eta;"};
        r0[32] = new String[]{"θ", "&theta;"};
        r0[33] = new String[]{"ι", "&iota;"};
        r0[34] = new String[]{"κ", "&kappa;"};
        r0[35] = new String[]{"λ", "&lambda;"};
        r0[36] = new String[]{"μ", "&mu;"};
        r0[37] = new String[]{"ν", "&nu;"};
        r0[38] = new String[]{"ξ", "&xi;"};
        r0[39] = new String[]{"ο", "&omicron;"};
        r0[40] = new String[]{"π", "&pi;"};
        r0[41] = new String[]{"ρ", "&rho;"};
        r0[42] = new String[]{"ς", "&sigmaf;"};
        r0[43] = new String[]{"σ", "&sigma;"};
        r0[44] = new String[]{"τ", "&tau;"};
        r0[45] = new String[]{"υ", "&upsilon;"};
        r0[46] = new String[]{"φ", "&phi;"};
        r0[47] = new String[]{"χ", "&chi;"};
        r0[48] = new String[]{"ψ", "&psi;"};
        r0[49] = new String[]{"ω", "&omega;"};
        r0[50] = new String[]{"ϑ", "&thetasym;"};
        r0[51] = new String[]{"ϒ", "&upsih;"};
        r0[52] = new String[]{"ϖ", "&piv;"};
        r0[53] = new String[]{"•", "&bull;"};
        r0[54] = new String[]{"…", "&hellip;"};
        r0[55] = new String[]{"′", "&prime;"};
        r0[56] = new String[]{"″", "&Prime;"};
        r0[57] = new String[]{"‾", "&oline;"};
        r0[58] = new String[]{"⁄", "&frasl;"};
        r0[59] = new String[]{"℘", "&weierp;"};
        r0[60] = new String[]{"ℑ", "&image;"};
        r0[61] = new String[]{"ℜ", "&real;"};
        r0[62] = new String[]{"™", "&trade;"};
        r0[63] = new String[]{"ℵ", "&alefsym;"};
        r0[64] = new String[]{"←", "&larr;"};
        r0[65] = new String[]{"↑", "&uarr;"};
        r0[66] = new String[]{"→", "&rarr;"};
        r0[67] = new String[]{"↓", "&darr;"};
        r0[68] = new String[]{"↔", "&harr;"};
        r0[69] = new String[]{"↵", "&crarr;"};
        r0[70] = new String[]{"⇐", "&lArr;"};
        r0[71] = new String[]{"⇑", "&uArr;"};
        r0[72] = new String[]{"⇒", "&rArr;"};
        r0[73] = new String[]{"⇓", "&dArr;"};
        r0[74] = new String[]{"⇔", "&hArr;"};
        r0[75] = new String[]{"∀", "&forall;"};
        r0[76] = new String[]{"∂", "&part;"};
        r0[77] = new String[]{"∃", "&exist;"};
        r0[78] = new String[]{"∅", "&empty;"};
        r0[79] = new String[]{"∇", "&nabla;"};
        r0[80] = new String[]{"∈", "&isin;"};
        r0[81] = new String[]{"∉", "&notin;"};
        r0[82] = new String[]{"∋", "&ni;"};
        r0[83] = new String[]{"∏", "&prod;"};
        r0[84] = new String[]{"∑", "&sum;"};
        r0[85] = new String[]{"−", "&minus;"};
        r0[86] = new String[]{"∗", "&lowast;"};
        r0[87] = new String[]{"√", "&radic;"};
        r0[88] = new String[]{"∝", "&prop;"};
        r0[89] = new String[]{"∞", "&infin;"};
        r0[90] = new String[]{"∠", "&ang;"};
        r0[91] = new String[]{"∧", "&and;"};
        r0[92] = new String[]{"∨", "&or;"};
        r0[93] = new String[]{"∩", "&cap;"};
        r0[94] = new String[]{"∪", "&cup;"};
        r0[95] = new String[]{"∫", "&int;"};
        r0[96] = new String[]{"∴", "&there4;"};
        r0[97] = new String[]{"∼", "&sim;"};
        r0[98] = new String[]{"≅", "&cong;"};
        r0[99] = new String[]{"≈", "&asymp;"};
        r0[100] = new String[]{"≠", "&ne;"};
        r0[101] = new String[]{"≡", "&equiv;"};
        r0[102] = new String[]{"≤", "&le;"};
        r0[103] = new String[]{"≥", "&ge;"};
        r0[104] = new String[]{"⊂", "&sub;"};
        r0[105] = new String[]{"⊃", "&sup;"};
        r0[106] = new String[]{"⊆", "&sube;"};
        r0[107] = new String[]{"⊇", "&supe;"};
        r0[108] = new String[]{"⊕", "&oplus;"};
        r0[109] = new String[]{"⊗", "&otimes;"};
        r0[110] = new String[]{"⊥", "&perp;"};
        r0[111] = new String[]{"⋅", "&sdot;"};
        r0[112] = new String[]{"⌈", "&lceil;"};
        r0[113] = new String[]{"⌉", "&rceil;"};
        r0[114] = new String[]{"⌊", "&lfloor;"};
        r0[115] = new String[]{"⌋", "&rfloor;"};
        r0[116] = new String[]{"〈", "&lang;"};
        r0[117] = new String[]{"〉", "&rang;"};
        r0[118] = new String[]{"◊", "&loz;"};
        r0[119] = new String[]{"♠", "&spades;"};
        r0[120] = new String[]{"♣", "&clubs;"};
        r0[121] = new String[]{"♥", "&hearts;"};
        r0[122] = new String[]{"♦", "&diams;"};
        r0[123] = new String[]{"Œ", "&OElig;"};
        r0[124] = new String[]{"œ", "&oelig;"};
        r0[125] = new String[]{"Š", "&Scaron;"};
        r0[TransportMediator.KEYCODE_MEDIA_PLAY] = new String[]{"š", "&scaron;"};
        r0[127] = new String[]{"Ÿ", "&Yuml;"};
        r0[128] = new String[]{"ˆ", "&circ;"};
        r0[TsExtractor.TS_STREAM_TYPE_AC3] = new String[]{"˜", "&tilde;"};
        r0[130] = new String[]{" ", "&ensp;"};
        r0[131] = new String[]{" ", "&emsp;"};
        r0[132] = new String[]{" ", "&thinsp;"};
        r0[133] = new String[]{"‌", "&zwnj;"};
        r0[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO] = new String[]{"‍", "&zwj;"};
        r0[135] = new String[]{"‎", "&lrm;"};
        r0[136] = new String[]{"‏", "&rlm;"};
        r0[137] = new String[]{"–", "&ndash;"};
        r0[TsExtractor.TS_STREAM_TYPE_DTS] = new String[]{"—", "&mdash;"};
        r0[139] = new String[]{"‘", "&lsquo;"};
        r0[140] = new String[]{"’", "&rsquo;"};
        r0[141] = new String[]{"‚", "&sbquo;"};
        r0[142] = new String[]{"“", "&ldquo;"};
        r0[143] = new String[]{"”", "&rdquo;"};
        r0[144] = new String[]{"„", "&bdquo;"};
        r0[145] = new String[]{"†", "&dagger;"};
        r0[146] = new String[]{"‡", "&Dagger;"};
        r0[147] = new String[]{"‰", "&permil;"};
        r0[148] = new String[]{"‹", "&lsaquo;"};
        r0[149] = new String[]{"›", "&rsaquo;"};
        r0[150] = new String[]{"€", "&euro;"};
        HTML40_EXTENDED_ESCAPE = r0;
        r0 = new String[4][];
        r0[0] = new String[]{"\"", "&quot;"};
        r0[1] = new String[]{"&", "&amp;"};
        r0[2] = new String[]{"<", "&lt;"};
        r0[3] = new String[]{">", "&gt;"};
        BASIC_ESCAPE = r0;
        r0 = new String[1][];
        r0[0] = new String[]{"'", "&apos;"};
        APOS_ESCAPE = r0;
        r0 = new String[5][];
        r0[0] = new String[]{"\b", "\\b"};
        r0[1] = new String[]{LogCollector.LINE_SEPARATOR, "\\n"};
        r0[2] = new String[]{"\t", "\\t"};
        r0[3] = new String[]{"\f", "\\f"};
        r0[4] = new String[]{"\r", "\\r"};
        JAVA_CTRL_CHARS_ESCAPE = r0;
    }

    public static String[][] ISO8859_1_UNESCAPE() {
        return (String[][]) ISO8859_1_UNESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_ESCAPE() {
        return (String[][]) HTML40_EXTENDED_ESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_UNESCAPE() {
        return (String[][]) HTML40_EXTENDED_UNESCAPE.clone();
    }

    public static String[][] BASIC_ESCAPE() {
        return (String[][]) BASIC_ESCAPE.clone();
    }

    public static String[][] BASIC_UNESCAPE() {
        return (String[][]) BASIC_UNESCAPE.clone();
    }

    public static String[][] APOS_ESCAPE() {
        return (String[][]) APOS_ESCAPE.clone();
    }

    public static String[][] APOS_UNESCAPE() {
        return (String[][]) APOS_UNESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_ESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_ESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_UNESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_UNESCAPE.clone();
    }

    public static String[][] invert(String[][] array) {
        String[][] newarray = (String[][]) Array.newInstance(String.class, new int[]{array.length, 2});
        for (int i = 0; i < array.length; i++) {
            newarray[i][0] = array[i][1];
            newarray[i][1] = array[i][0];
        }
        return newarray;
    }
}
