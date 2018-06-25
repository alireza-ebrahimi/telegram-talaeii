package com.persianswitch.sdk.payment.model;

import android.content.Context;
import com.persianswitch.sdk.C0770R;

public enum Bank {
    UNDEFINED(0, new String[0], C0770R.string.asanpardakht_bank_card, C0770R.drawable.asanpardakht_ic_bank_empty),
    MELLI(r6, new String[]{"603799"}, C0770R.string.asanpardakht_bank_melli, C0770R.drawable.asanpardakht_ic_bank_melli),
    SADERAT(r6, new String[]{"603769"}, C0770R.string.asanpardakht_bank_saderat, C0770R.drawable.asanpardakht_ic_bank_saderat),
    PARSIAN(r6, new String[]{"622106", "627884", "639194"}, C0770R.string.asanpardakht_bank_parsian, C0770R.drawable.asanpardakht_ic_bank_parsian),
    MELLAT(9, new String[]{"610433", "991975"}, C0770R.string.asanpardakht_bank_mellat, C0770R.drawable.asanpardakht_ic_bank_mellat),
    KESHAVRZI(10, new String[]{"603770", "639217"}, C0770R.string.asanpardakht_bank_keshavarzi, C0770R.drawable.asanpardakht_ic_bank_keshavarzi),
    TEJARAT(11, new String[]{"627353", "585983"}, C0770R.string.asanpardakht_bank_tejarat, C0770R.drawable.asanpardakht_ic_bank_tejarat),
    SARMAYE(12, new String[]{"639607"}, C0770R.string.asanpardakht_bank_sarmaye, C0770R.drawable.asanpardakht_ic_bank_sarmaye),
    PASARGARD(13, new String[]{"502229", "639347"}, C0770R.string.asanpardakht_bank_passargard, C0770R.drawable.asanpardakht_ic_bank_pasargad),
    AYANDE(14, new String[]{"636214"}, C0770R.string.asanpardakht_bank_ayande, C0770R.drawable.asanpardakht_ic_bank_ayande),
    SEPAH(15, new String[]{"589210", "604932"}, C0770R.string.asanpardakht_bank_sepah, C0770R.drawable.asanpardakht_ic_bank_sepah),
    SAMAN(16, new String[]{"621986"}, C0770R.string.asanpardakht_bank_saman, C0770R.drawable.asanpardakht_ic_bank_saman),
    MASKAN(17, new String[]{"628023"}, C0770R.string.asanpardakht_bank_maskan, C0770R.drawable.asanpardakht_ic_bank_maskan),
    SINA(18, new String[]{"639346"}, C0770R.string.asanpardakht_bank_sina, C0770R.drawable.asanpardakht_ic_bank_sina),
    EGHTESAD_NOVIN(19, new String[]{"627412"}, C0770R.string.asanpardakht_bank_en, C0770R.drawable.asanpardakht_ic_bank_en),
    SHAHR(20, new String[]{"502806", "504706"}, C0770R.string.asanpardakht_bank_shahr, C0770R.drawable.asanpardakht_ic_bank_shahr),
    KAR_AFARIN(21, new String[]{"627488", "502210"}, C0770R.string.asanpardakht_bank_karafarin, C0770R.drawable.asanpardakht_ic_bank_karafarin),
    TOSEE_SADERAT(22, new String[]{"627648", "207177"}, C0770R.string.asanpardakht_bank_tosee_saderat, C0770R.drawable.asanpardakht_ic_bank_tosee_saderat),
    SANAT_MADAN(23, new String[]{"627961"}, C0770R.string.asanpardakht_bank_sanat_madan, C0770R.drawable.asanpardakht_ic_bank_sanat_madan),
    REFAH(24, new String[]{"589463"}, C0770R.string.asanpardakht_refah_bank, C0770R.drawable.asanpardakht_ic_refah_bank),
    MEHR_IRAN(27, new String[]{"606373"}, C0770R.string.asanpardakht_bank_mehr_iran, C0770R.drawable.asanpardakht_ic_bank_mehr_iran),
    TOSEE_FINANCE(29, new String[]{"628157"}, C0770R.string.asanpardakht_toose_finance, C0770R.drawable.asanpardakht_ic_tosee_finance),
    POST(30, new String[]{"627760"}, C0770R.string.asanpardakht_post_bank, C0770R.drawable.asanpardakht_ic_post_bank),
    ANSAR(32, new String[]{"627381"}, C0770R.string.asanpardakht_bank_ansar, C0770R.drawable.asanpardakht_ic_bank_ansar),
    TOSEE_TAVON(33, new String[]{"502908"}, C0770R.string.asanpardakht_bank_tosee, C0770R.drawable.asanpardakht_ic_bank_tosee),
    DAY(35, new String[]{"502938"}, C0770R.string.asanpardakht_bank_day, C0770R.drawable.asanpardakht_ic_bank_day),
    HEKMAT_IRANIAN(39, new String[]{"636949"}, C0770R.string.asanpardakht_bank_hekmat_iranian, C0770R.drawable.asanpardakht_ic_bank_hekmat_iranian),
    IRAN_ZAMIN(47, new String[]{"505785"}, C0770R.string.asanpardakht_bank_iran_zamin, C0770R.drawable.asanpardakht_ic_bank_iran_zamin),
    GARDESHGARI(48, new String[]{"505416"}, C0770R.string.asanpardakht_bank_gardeshgari, C0770R.drawable.asanpardakht_ic_bank_gardeshgari),
    ASAN_PARDAKHT(59, new String[]{"983254", "983255"}, C0770R.string.asanpardakht_bank_asan_pardakht, C0770R.drawable.asanpardakht_ic_bank_asan_pardakht),
    GHAVAMIN(60, new String[]{"639599"}, C0770R.string.asanpardakht_bank_ghavamin, C0770R.drawable.asanpardakht_ic_bank_ghavamin),
    RESALT(61, new String[]{"504172"}, C0770R.string.asanpardakht_bank_resalat, C0770R.drawable.asanpardakht_ic_bank_resalat),
    KHAVARMIANE(62, new String[]{"505809", "585947"}, C0770R.string.asanpardakht_bank_khavarmiane, C0770R.drawable.asanpardakht_ic_bank_khavarmianeh),
    KOUSAR(63, new String[]{"505801"}, C0770R.string.asanpardakht_bank_kousar, C0770R.drawable.asanpardakht_ic_bank_kousar),
    MEHR_EGHTESAD(64, new String[]{"639370"}, C0770R.string.asanpardakht_bank_mehr_eghtesad, C0770R.drawable.asanpardakht_ic_bank_mehr_eghtesad),
    ASGARIYE(65, new String[]{"606256"}, C0770R.string.asanpardakht_bank_asgariye, C0770R.drawable.asanpardakht_ic_bank_asgariye),
    CENTRAL(66, new String[]{"636795", "936450"}, C0770R.string.asanpardakht_bank_central, C0770R.drawable.asanpardakht_ic_bank_central),
    VENEZOELA(67, new String[]{"581874"}, C0770R.string.asanpardakht_bank_iran_venezoela, C0770R.drawable.asanpardakht_ic_bank_venezoela),
    TAKHFIF_IRANIAN(68, new String[]{"141272"}, C0770R.string.asanpardakht_bank_takhfif_iranian, C0770R.drawable.asanpardakht_ic_bank_bashgah_iranian);
    
    private final long bankId;
    private int bankLogoResourceId;
    private int bankNameResourceId;
    private final String[] prefixCardNos;

    private Bank(int bankId, String[] prefixCardNos, int bankNameResourceId, int bankLogoResourceId) {
        this.bankId = (long) bankId;
        this.prefixCardNos = prefixCardNos;
        this.bankNameResourceId = bankNameResourceId;
        this.bankLogoResourceId = bankLogoResourceId;
    }

    public static Bank getById(long bankId) {
        for (Bank bank : values()) {
            if (bank.getBankId() == bankId) {
                return bank;
            }
        }
        return UNDEFINED;
    }

    public static Bank getByCardNo(String cardNo) {
        if (cardNo == null) {
            return UNDEFINED;
        }
        for (Bank bank : values()) {
            for (String prefixCardNo : bank.getPrefixCardNos()) {
                if (cardNo.startsWith(prefixCardNo)) {
                    return bank;
                }
            }
        }
        return UNDEFINED;
    }

    public long getBankId() {
        return this.bankId;
    }

    public String[] getPrefixCardNos() {
        return this.prefixCardNos;
    }

    public int getBankNameResourceId() {
        if (this == UNDEFINED) {
            return 0;
        }
        return this.bankNameResourceId;
    }

    public String getBankName(Context context) {
        if (this == UNDEFINED) {
            return null;
        }
        return context.getString(this.bankNameResourceId);
    }

    public int getBankLogoResource() {
        return this.bankLogoResourceId;
    }
}
