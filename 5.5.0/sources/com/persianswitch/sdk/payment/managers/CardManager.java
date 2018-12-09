package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.ResourceUtils;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.ResultPack;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.SyncWebService;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranResponse;
import com.persianswitch.sdk.base.webservice.data.WSTranResponse.ExpirationStatus;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.Bank;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.model.UserCard.CardProtocolConverter;
import com.persianswitch.sdk.payment.payment.ISuggestionUpdate;
import com.persianswitch.sdk.payment.repo.CardRepo;
import com.persianswitch.sdk.payment.webservice.SDKSyncWebServiceCallback;
import com.persianswitch.sdk.payment.webservice.SDKWebServiceCallback;
import java.util.ArrayList;
import java.util.List;

public final class CardManager {
    /* renamed from: a */
    public final SyncCardCallback f7397a = new C22841(this);
    /* renamed from: b */
    private final Context f7398b;

    public interface SyncCardCallback {
        /* renamed from: a */
        void mo3295a();

        /* renamed from: a */
        void mo3296a(WSResponse wSResponse);
    }

    /* renamed from: com.persianswitch.sdk.payment.managers.CardManager$1 */
    class C22841 implements SyncCardCallback {
        /* renamed from: a */
        final /* synthetic */ CardManager f7386a;

        C22841(CardManager cardManager) {
            this.f7386a = cardManager;
        }

        /* renamed from: a */
        public void mo3295a() {
            BaseSetting.m10459b(this.f7386a.f7398b, System.currentTimeMillis());
            SDKLog.m10657a("CardManager", "onCardsSynced [timestamp = %d]", Long.valueOf(r0));
        }

        /* renamed from: a */
        public void mo3296a(WSResponse wSResponse) {
        }
    }

    private static class ResponseCardProtocolConverter implements ProtocolConverter<ResponseCard> {
        /* renamed from: a */
        private final Context f7393a;
        /* renamed from: b */
        private final boolean f7394b;
        /* renamed from: c */
        private final UserCard f7395c;
        /* renamed from: d */
        private final CardProtocolConverter f7396d = new CardProtocolConverter(this.f7393a);

        static class ResponseCard {
            /* renamed from: a */
            final List<UserCard> f7389a;
            /* renamed from: b */
            final boolean f7390b;
            /* renamed from: c */
            final boolean f7391c;
            /* renamed from: d */
            final boolean f7392d;

            ResponseCard(List<UserCard> list, boolean z, boolean z2, boolean z3) {
                this.f7389a = list;
                this.f7390b = z;
                this.f7391c = z2;
                this.f7392d = z3;
            }
        }

        ResponseCardProtocolConverter(Context context, boolean z, UserCard userCard) {
            this.f7393a = context;
            this.f7394b = z;
            this.f7395c = userCard;
        }

        /* renamed from: a */
        public ResponseCard m11069a(String str) {
            boolean z;
            boolean z2;
            Throwable th;
            boolean z3;
            Throwable th2;
            boolean z4 = true;
            boolean z5 = this.f7394b;
            List arrayList = new ArrayList();
            try {
                String[] split = StringUtils.m10800a((Object) str).split("&&", 3);
                ExpirationStatus a = ExpirationStatus.m10959a(split[1]);
                if (!StringUtils.m10805a("0;0", split[0])) {
                    Long d = StringUtils.m10808d(StringUtils.m10800a(split[0]).split(";")[0]);
                    Long d2 = StringUtils.m10808d(StringUtils.m10800a(split[0]).split(";")[1]);
                    this.f7395c.m11276g();
                    this.f7395c.m11265a(d);
                    this.f7395c.m11268b(d2);
                    new CardRepo(this.f7393a).mo3369a(this.f7395c);
                    if (d != null && d2 != null && d.longValue() > 0 && d2.longValue() > 0) {
                        int c = Bank.m11114a(d2.longValue()).m11118c();
                        if (c > 0) {
                            if (this.f7395c.m11264a(true) == null) {
                                this.f7395c.m11266a(ResourceUtils.m10763a(this.f7393a, "fa", c), true);
                            }
                            if (this.f7395c.m11264a(false) == null) {
                                this.f7395c.m11266a(ResourceUtils.m10763a(this.f7393a, "en", c), false);
                            }
                        }
                        arrayList.add(this.f7395c);
                    }
                }
                if (a == ExpirationStatus.SAVED) {
                    z = !this.f7395c.m11277h();
                    try {
                        this.f7395c.m11273d(true);
                        new CardRepo(this.f7393a).mo3369a(this.f7395c);
                        z2 = false;
                    } catch (Throwable e) {
                        z2 = z5;
                        z5 = z;
                        th = e;
                        z4 = false;
                        SDKLog.m10656a("CardManager", "New Card Don't Save In Database", th, new Object[0]);
                        z3 = z5;
                        z5 = z2;
                        return new ResponseCard(arrayList, z5, z3, z4);
                    }
                } else if (a == ExpirationStatus.REMOVE_CAUSE_CHANGED) {
                    if (this.f7395c.m11277h()) {
                        z2 = true;
                        z = true;
                    } else {
                        z2 = false;
                        z = false;
                    }
                    try {
                        this.f7395c.m11273d(false);
                        new CardRepo(this.f7393a).mo3369a(this.f7395c);
                    } catch (Throwable e2) {
                        th2 = e2;
                        z4 = z2;
                        z2 = z5;
                        z5 = z;
                        th = th2;
                        SDKLog.m10656a("CardManager", "New Card Don't Save In Database", th, new Object[0]);
                        z3 = z5;
                        z5 = z2;
                        return new ResponseCard(arrayList, z5, z3, z4);
                    }
                } else {
                    z2 = false;
                    z = false;
                }
                if (this.f7394b) {
                    Object a2 = StringUtils.m10800a(split[2]);
                    if (!"1;1;1;1;".equals(a2)) {
                        if ("0;0;0;0".equals(a2)) {
                            z4 = false;
                        } else {
                            try {
                                for (String a3 : StringUtils.m10800a(a2).split("&")) {
                                    arrayList.add(this.f7396d.m11232a(a3));
                                }
                            } catch (Throwable e3) {
                                th2 = e3;
                                z5 = z;
                                th = th2;
                                boolean z6 = z2;
                                z2 = true;
                                z4 = z6;
                                SDKLog.m10656a("CardManager", "New Card Don't Save In Database", th, new Object[0]);
                                z3 = z5;
                                z5 = z2;
                                return new ResponseCard(arrayList, z5, z3, z4);
                            }
                        }
                    }
                }
                z4 = z5;
                z3 = z;
                z5 = z4;
                z4 = z2;
            } catch (Throwable e22) {
                th = e22;
                z2 = z5;
                z5 = false;
                z4 = false;
                SDKLog.m10656a("CardManager", "New Card Don't Save In Database", th, new Object[0]);
                z3 = z5;
                z5 = z2;
                return new ResponseCard(arrayList, z5, z3, z4);
            }
            return new ResponseCard(arrayList, z5, z3, z4);
        }
    }

    public CardManager(Context context) {
        this.f7398b = context;
    }

    /* renamed from: a */
    private void m11071a(WSResponse wSResponse, SyncCardCallback syncCardCallback) {
        String str = wSResponse.m10947h()[1];
        if ("0".equalsIgnoreCase(str)) {
            m11074c();
            if (syncCardCallback != null) {
                syncCardCallback.mo3295a();
            }
        } else if (StringUtils.m10803a(str)) {
            syncCardCallback.mo3296a(wSResponse);
        } else {
            try {
                List arrayList = new ArrayList();
                if (!StringUtils.m10803a(str)) {
                    CardProtocolConverter cardProtocolConverter = new CardProtocolConverter(this.f7398b);
                    for (String a : str.split("&")) {
                        arrayList.add(cardProtocolConverter.m11232a(a));
                    }
                }
                new CardRepo(this.f7398b).m10612a(Where.m10602b());
                m11073a(arrayList, syncCardCallback);
            } catch (Exception e) {
                syncCardCallback.mo3296a(wSResponse);
            }
        }
    }

    /* renamed from: a */
    private void m11073a(List<UserCard> list, SyncCardCallback syncCardCallback) {
        CardRepo cardRepo = new CardRepo(this.f7398b.getApplicationContext());
        if (list != null) {
            for (UserCard a : list) {
                cardRepo.mo3369a((IPhoenixModel) a);
            }
        }
        if (syncCardCallback != null) {
            syncCardCallback.mo3295a();
        }
    }

    /* renamed from: c */
    private void m11074c() {
        new CardRepo(this.f7398b).m10612a(Where.m10602b());
    }

    /* renamed from: a */
    public SyncCardCallback m11075a() {
        return this.f7397a;
    }

    /* renamed from: a */
    public void m11076a(SyncCardCallback syncCardCallback) {
        Context context = this.f7398b;
        WSRequest a = WSTranRequest.m10950a(this.f7398b, new SDKConfig(), OpCode.SYNC_CARDS_BY_SERVER.m10839a(), 0);
        a.m10918a(HostDataRequestField.m11147a(context).m11152a());
        a.m10919a(new String[]{TtmlNode.ANONYMOUS_REGION_ID});
        ResultPack a2 = SyncWebService.m10866a(a).m10870a(this.f7398b, new SDKSyncWebServiceCallback());
        WSResponse b = a2.m10847b();
        if (a2.m10846a() == TransactionStatus.SUCCESS) {
            m11071a(b, syncCardCallback);
        } else {
            m11075a().mo3296a(b);
        }
    }

    /* renamed from: a */
    public void m11077a(boolean z, UserCard userCard, WSTranResponse wSTranResponse, ISuggestionUpdate iSuggestionUpdate) {
        boolean z2 = false;
        boolean z3 = true;
        ResponseCard a = new ResponseCardProtocolConverter(this.f7398b, z, userCard).m11069a(wSTranResponse.m10965k());
        boolean z4 = a.f7391c;
        boolean z5 = a.f7392d;
        if (a.f7390b) {
            m11074c();
        }
        try {
            m11073a(a.f7389a, this.f7397a);
        } catch (Exception e) {
            this.f7397a.mo3296a(wSTranResponse);
        }
        if (z) {
            this.f7397a.mo3295a();
        }
        if (wSTranResponse.m10942c() == StatusCode.CARD_NOT_FOUND) {
            new CardRepo(this.f7398b).m10619d(userCard);
        } else if (wSTranResponse.m10942c() == StatusCode.EXPIRATION_DATE_NOT_FOUND) {
            userCard.m11273d(false);
            new CardRepo(this.f7398b).mo3369a((IPhoenixModel) userCard);
            z2 = true;
        } else {
            z2 = z5;
            z3 = z4;
        }
        if (iSuggestionUpdate != null && r1) {
            iSuggestionUpdate.mo3322a(z2);
        }
    }

    /* renamed from: b */
    public void m11078b() {
        m11079b(this.f7397a);
    }

    /* renamed from: b */
    public void m11079b(final SyncCardCallback syncCardCallback) {
        Context context = this.f7398b;
        WSRequest a = WSTranRequest.m10950a(this.f7398b, new SDKConfig(), OpCode.SYNC_CARDS_BY_SERVER.m10839a(), 0);
        a.m10918a(HostDataRequestField.m11147a(this.f7398b).m11152a());
        a.m10919a(new String[]{TtmlNode.ANONYMOUS_REGION_ID});
        WebService.m10861b(a).m10865b(this.f7398b, new SDKWebServiceCallback<WSResponse>(this) {
            /* renamed from: c */
            final /* synthetic */ CardManager f7388c;

            /* renamed from: a */
            public void mo3276a() {
            }

            /* renamed from: a */
            public void mo3277a(WSStatus wSStatus, String str, WSResponse wSResponse) {
                if (syncCardCallback != null) {
                    syncCardCallback.mo3296a(wSResponse);
                }
            }

            /* renamed from: a */
            public void mo3278a(String str, WSResponse wSResponse) {
                this.f7388c.m11071a(wSResponse, syncCardCallback);
            }
        });
    }
}
