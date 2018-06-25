package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.C2262R;

public enum Bank {
    UNDEFINED(0, new String[0], C2262R.string.asanpardakht_bank_card, C2262R.drawable.asanpardakht_ic_bank_empty),
    MELLI(r6, new String[]{"603799"}, C2262R.string.asanpardakht_bank_melli, C2262R.drawable.asanpardakht_ic_bank_melli),
    SADERAT(r6, new String[]{"603769"}, C2262R.string.asanpardakht_bank_saderat, C2262R.drawable.asanpardakht_ic_bank_saderat),
    PARSIAN(r6, new String[]{"622106", "627884", "639194"}, C2262R.string.asanpardakht_bank_parsian, C2262R.drawable.asanpardakht_ic_bank_parsian),
    MELLAT(9, new String[]{"610433", "991975"}, C2262R.string.asanpardakht_bank_mellat, C2262R.drawable.asanpardakht_ic_bank_mellat),
    KESHAVRZI(10, new String[]{"603770", "639217"}, C2262R.string.asanpardakht_bank_keshavarzi, C2262R.drawable.asanpardakht_ic_bank_keshavarzi),
    TEJARAT(11, new String[]{"627353", "585983"}, C2262R.string.asanpardakht_bank_tejarat, C2262R.drawable.asanpardakht_ic_bank_tejarat),
    SARMAYE(12, new String[]{"639607"}, C2262R.string.asanpardakht_bank_sarmaye, C2262R.drawable.asanpardakht_ic_bank_sarmaye),
    PASARGARD(13, new String[]{"502229", "639347"}, C2262R.string.asanpardakht_bank_passargard, C2262R.drawable.asanpardakht_ic_bank_pasargad),
    AYANDE(14, new String[]{"636214"}, C2262R.string.asanpardakht_bank_ayande, C2262R.drawable.asanpardakht_ic_bank_ayande),
    SEPAH(15, new String[]{"589210", "604932"}, C2262R.string.asanpardakht_bank_sepah, C2262R.drawable.asanpardakht_ic_bank_sepah),
    SAMAN(16, new String[]{"621986"}, C2262R.string.asanpardakht_bank_saman, C2262R.drawable.asanpardakht_ic_bank_saman),
    MASKAN(17, new String[]{"628023"}, C2262R.string.asanpardakht_bank_maskan, C2262R.drawable.asanpardakht_ic_bank_maskan),
    SINA(18, new String[]{"639346"}, C2262R.string.asanpardakht_bank_sina, C2262R.drawable.asanpardakht_ic_bank_sina),
    EGHTESAD_NOVIN(19, new String[]{"627412"}, C2262R.string.asanpardakht_bank_en, C2262R.drawable.asanpardakht_ic_bank_en),
    SHAHR(20, new String[]{"502806", "504706"}, C2262R.string.asanpardakht_bank_shahr, C2262R.drawable.asanpardakht_ic_bank_shahr),
    KAR_AFARIN(21, new String[]{"627488", "502210"}, C2262R.string.asanpardakht_bank_karafarin, C2262R.drawable.asanpardakht_ic_bank_karafarin),
    TOSEE_SADERAT(22, new String[]{"627648", "207177"}, C2262R.string.asanpardakht_bank_tosee_saderat, C2262R.drawable.asanpardakht_ic_bank_tosee_saderat),
    SANAT_MADAN(23, new String[]{"627961"}, C2262R.string.asanpardakht_bank_sanat_madan, C2262R.drawable.asanpardakht_ic_bank_sanat_madan),
    REFAH(24, new String[]{"589463"}, C2262R.string.asanpardakht_refah_bank, C2262R.drawable.asanpardakht_ic_refah_bank),
    MEHR_IRAN(27, new String[]{"606373"}, C2262R.string.asanpardakht_bank_mehr_iran, C2262R.drawable.asanpardakht_ic_bank_mehr_iran),
    TOSEE_FINANCE(29, new String[]{"628157"}, C2262R.string.asanpardakht_toose_finance, C2262R.drawable.asanpardakht_ic_tosee_finance),
    POST(30, new String[]{"627760"}, C2262R.string.asanpardakht_post_bank, C2262R.drawable.asanpardakht_ic_post_bank),
    ANSAR(32, new String[]{"627381"}, C2262R.string.asanpardakht_bank_ansar, C2262R.drawable.asanpardakht_ic_bank_ansar),
    TOSEE_TAVON(33, new String[]{"502908"}, C2262R.string.asanpardakht_bank_tosee, C2262R.drawable.asanpardakht_ic_bank_tosee),
    DAY(35, new String[]{"502938"}, C2262R.string.asanpardakht_bank_day, C2262R.drawable.asanpardakht_ic_bank_day),
    HEKMAT_IRANIAN(39, new String[]{"636949"}, C2262R.string.asanpardakht_bank_hekmat_iranian, C2262R.drawable.asanpardakht_ic_bank_hekmat_iranian),
    IRAN_ZAMIN(47, new String[]{"505785"}, C2262R.string.asanpardakht_bank_iran_zamin, C2262R.drawable.asanpardakht_ic_bank_iran_zamin),
    GARDESHGARI(48, new String[]{"505416"}, C2262R.string.asanpardakht_bank_gardeshgari, C2262R.drawable.asanpardakht_ic_bank_gardeshgari),
    ASAN_PARDAKHT(59, new String[]{"983254", "983255"}, C2262R.string.asanpardakht_bank_asan_pardakht, C2262R.drawable.asanpardakht_ic_bank_asan_pardakht),
    GHAVAMIN(60, new String[]{"639599"}, C2262R.string.asanpardakht_bank_ghavamin, C2262R.drawable.asanpardakht_ic_bank_ghavamin),
    RESALT(61, new String[]{"504172"}, C2262R.string.asanpardakht_bank_resalat, C2262R.drawable.asanpardakht_ic_bank_resalat),
    KHAVARMIANE(62, new String[]{"505809", "585947"}, C2262R.string.asanpardakht_bank_khavarmiane, C2262R.drawable.asanpardakht_ic_bank_khavarmianeh),
    KOUSAR(63, new String[]{"505801"}, C2262R.string.asanpardakht_bank_kousar, C2262R.drawable.asanpardakht_ic_bank_kousar),
    MEHR_EGHTESAD(64, new String[]{"639370"}, C2262R.string.asanpardakht_bank_mehr_eghtesad, C2262R.drawable.asanpardakht_ic_bank_mehr_eghtesad),
    ASGARIYE(65, new String[]{"606256"}, C2262R.string.asanpardakht_bank_asgariye, C2262R.drawable.asanpardakht_ic_bank_asgariye),
    CENTRAL(66, new String[]{"636795", "936450"}, C2262R.string.asanpardakht_bank_central, C2262R.drawable.asanpardakht_ic_bank_central),
    VENEZOELA(67, new String[]{"581874"}, C2262R.string.asanpardakht_bank_iran_venezoela, C2262R.drawable.asanpardakht_ic_bank_venezoela),
    TAKHFIF_IRANIAN(68, new String[]{"141272"}, C2262R.string.asanpardakht_bank_takhfif_iranian, C2262R.drawable.asanpardakht_ic_bank_bashgah_iranian);
    
    /* renamed from: N */
    private final long f7464N;
    /* renamed from: O */
    private final String[] f7465O;
    /* renamed from: P */
    private int f7466P;
    /* renamed from: Q */
    private int f7467Q;

    private Bank(int i, String[] strArr, int i2, int i3) {
        this.f7464N = (long) i;
        this.f7465O = strArr;
        this.f7466P = i2;
        this.f7467Q = i3;
    }

    /* renamed from: a */
    public static Bank m11114a(long j) {
        for (Bank bank : values()) {
            if (bank.m11116a() == j) {
                return bank;
            }
        }
        return UNDEFINED;
    }

    /* renamed from: a */
    public static Bank m11115a(String str) {
        if (str == null) {
            return UNDEFINED;
        }
        for (Bank bank : values()) {
            for (String startsWith : bank.m11117b()) {
                if (str.startsWith(startsWith)) {
                    return bank;
                }
            }
        }
        return UNDEFINED;
    }

    /* renamed from: a */
    public long m11116a() {
        return this.f7464N;
    }

    /* renamed from: b */
    public String[] m11117b() {
        return this.f7465O;
    }

    /* renamed from: c */
    public int m11118c() {
        return this == UNDEFINED ? 0 : this.f7466P;
    }

    /* renamed from: d */
    public int m11119d() {
        return this.f7467Q;
    }
}
