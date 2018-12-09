package com.persianswitch.sdk.payment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.fastkit.FilterableModel;
import com.persianswitch.sdk.base.utils.ResourceUtils;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.payment.managers.ProtocolConverter;
import java.util.StringTokenizer;

public class UserCard implements Parcelable, IPhoenixModel<Long>, FilterableModel {
    public static final Creator<UserCard> CREATOR = new C22951();
    /* renamed from: a */
    public static final UserCard f7565a = new UserCard("-1", f7566b, Long.valueOf(Bank.ASAN_PARDAKHT.m11116a()));
    /* renamed from: b */
    private static final Long f7566b = Long.valueOf(-1);
    /* renamed from: c */
    private String f7567c;
    /* renamed from: d */
    private Long f7568d;
    /* renamed from: e */
    private Long f7569e;
    /* renamed from: f */
    private boolean f7570f = false;
    /* renamed from: g */
    private boolean f7571g = false;
    /* renamed from: h */
    private boolean f7572h = true;
    /* renamed from: i */
    private String f7573i;
    /* renamed from: j */
    private String f7574j;
    /* renamed from: k */
    private boolean f7575k;
    /* renamed from: l */
    private boolean f7576l;

    /* renamed from: com.persianswitch.sdk.payment.model.UserCard$1 */
    static class C22951 implements Creator<UserCard> {
        C22951() {
        }

        /* renamed from: a */
        public UserCard m11230a(Parcel parcel) {
            return new UserCard(parcel);
        }

        /* renamed from: a */
        public UserCard[] m11231a(int i) {
            return new UserCard[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m11230a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m11231a(i);
        }
    }

    public static final class CardProtocolConverter implements ProtocolConverter<UserCard> {
        /* renamed from: a */
        private final Context f7555a;

        public CardProtocolConverter(Context context) {
            this.f7555a = context;
        }

        /* renamed from: a */
        public UserCard m11232a(String str) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
            UserCard userCard = new UserCard();
            userCard.f7568d = Long.valueOf(stringTokenizer.nextToken());
            userCard.f7567c = stringTokenizer.nextToken();
            userCard.f7569e = Long.valueOf(stringTokenizer.nextToken());
            userCard.f7570f = stringTokenizer.nextToken().equals("1");
            String nextToken = stringTokenizer.nextToken();
            boolean z = nextToken != null && nextToken.length() > 0 && nextToken.startsWith("1");
            userCard.f7572h = z;
            userCard.f7574j = UserCard.m11248b(this.f7555a, false, userCard.f7569e, stringTokenizer.nextToken());
            userCard.f7573i = UserCard.m11248b(this.f7555a, true, userCard.f7569e, stringTokenizer.nextToken());
            return userCard;
        }

        /* renamed from: a */
        public String m11233a(UserCard userCard) {
            long j = 0;
            int i = 1;
            StringBuilder append = new StringBuilder().append(userCard.f7568d == null ? 0 : userCard.f7568d.longValue()).append(";").append(userCard.f7567c == null ? Integer.valueOf(0) : userCard.f7567c).append(";");
            if (userCard.f7569e != null) {
                j = userCard.f7569e.longValue();
            }
            append = append.append(j).append(";").append(userCard.f7575k ? 1 : 0).append(";");
            if (!userCard.f7576l) {
                i = 0;
            }
            return append.append(i).toString();
        }
    }

    public static final class CardTable extends Table<UserCard> {
        /* renamed from: a */
        public static final Column<Long> f7556a = new Column("CardId", Long.class, true);
        /* renamed from: b */
        public static final Column<Long> f7557b = new Column("BankId", Long.class);
        /* renamed from: c */
        public static final Column<String> f7558c = new Column("CardNo", String.class);
        /* renamed from: d */
        public static final Column<String> f7559d = new Column("TitleFa", String.class);
        /* renamed from: e */
        public static final Column<String> f7560e = new Column("TitleEn", String.class);
        /* renamed from: f */
        public static final Column<Boolean> f7561f = new Column("IsDefault", Boolean.class);
        /* renamed from: g */
        public static final Column<Boolean> f7562g = new Column("IsExpirationSaved", Boolean.class);
        /* renamed from: h */
        public static final Column<Boolean> f7563h = new Column("IsRemovable", Boolean.class);
        /* renamed from: i */
        private final CardEntityConverter f7564i = new CardEntityConverter();

        private static class CardEntityConverter implements EntityConverter<UserCard> {
            private CardEntityConverter() {
            }

            /* renamed from: a */
            public /* synthetic */ Object mo3237a(Cursor cursor) {
                return m11237b(cursor);
            }

            /* renamed from: a */
            public void m11235a(UserCard userCard, ContentValues contentValues) {
                CardTable.f7556a.m10495a(userCard.f7568d, contentValues);
                CardTable.f7557b.m10495a(userCard.f7569e, contentValues);
                CardTable.f7558c.m10495a(userCard.f7567c, contentValues);
                CardTable.f7559d.m10495a(userCard.f7573i, contentValues);
                CardTable.f7560e.m10495a(userCard.f7574j, contentValues);
                CardTable.f7561f.m10495a(Boolean.valueOf(userCard.f7571g), contentValues);
                CardTable.f7562g.m10495a(Boolean.valueOf(userCard.f7570f), contentValues);
                CardTable.f7563h.m10495a(Boolean.valueOf(userCard.f7572h), contentValues);
            }

            /* renamed from: b */
            public UserCard m11237b(Cursor cursor) {
                Long l = (Long) CardTable.f7556a.m10493a(cursor);
                Long l2 = (Long) CardTable.f7557b.m10493a(cursor);
                String str = (String) CardTable.f7558c.m10493a(cursor);
                String str2 = (String) CardTable.f7559d.m10493a(cursor);
                String str3 = (String) CardTable.f7560e.m10493a(cursor);
                Boolean bool = (Boolean) CardTable.f7561f.m10493a(cursor);
                Boolean bool2 = (Boolean) CardTable.f7562g.m10493a(cursor);
                Boolean bool3 = (Boolean) CardTable.f7563h.m10493a(cursor);
                UserCard userCard = new UserCard();
                userCard.f7568d = l;
                userCard.f7569e = l2;
                userCard.f7567c = str;
                userCard.f7573i = str2;
                userCard.f7574j = str3;
                userCard.f7571g = bool.booleanValue();
                userCard.f7570f = bool2.booleanValue();
                userCard.f7572h = bool3.booleanValue();
                return userCard;
            }
        }

        /* renamed from: a */
        public String mo3239a() {
            return "Cards";
        }

        /* renamed from: b */
        public Column mo3240b() {
            return f7556a;
        }

        /* renamed from: c */
        public Column[] mo3241c() {
            return new Column[]{f7556a, f7557b, f7558c, f7559d, f7560e, f7561f, f7562g, f7563h};
        }

        /* renamed from: d */
        public EntityConverter<UserCard> mo3242d() {
            return this.f7564i;
        }
    }

    static {
        UserCard userCard = f7565a;
        UserCard userCard2 = f7565a;
        Long valueOf = Long.valueOf(0);
        userCard2.f7569e = valueOf;
        userCard.f7568d = valueOf;
        f7565a.f7567c = "9832540000000733";
        f7565a.f7575k = false;
    }

    protected UserCard(Parcel parcel) {
        boolean z = true;
        this.f7568d = Long.valueOf(parcel.readLong());
        this.f7567c = parcel.readString();
        this.f7569e = Long.valueOf(parcel.readLong());
        this.f7571g = parcel.readByte() != (byte) 0;
        this.f7570f = parcel.readByte() != (byte) 0;
        this.f7572h = parcel.readByte() != (byte) 0;
        this.f7573i = parcel.readString();
        this.f7574j = parcel.readString();
        this.f7575k = parcel.readByte() != (byte) 0;
        if (parcel.readByte() == (byte) 0) {
            z = false;
        }
        this.f7576l = z;
    }

    public UserCard(String str) {
        this.f7567c = str;
        this.f7569e = Long.valueOf(Bank.m11115a(str).m11116a());
    }

    public UserCard(String str, Long l, Long l2) {
        this.f7567c = str;
        this.f7568d = l;
        this.f7569e = l2;
    }

    /* renamed from: b */
    private static String m11248b(Context context, boolean z, Long l, String str) {
        int c = Bank.m11114a(l.longValue()).m11118c();
        if (!StringUtils.m10803a(str) && !"#".equals(str)) {
            return str;
        }
        if (l.longValue() <= 0) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        return ResourceUtils.m10763a(context, z ? "fa" : "en", c);
    }

    /* renamed from: i */
    private String m11260i() {
        return this.f7567c.length() >= 4 ? this.f7567c.substring(this.f7567c.length() - 4, this.f7567c.length()) : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    public String mo3299a() {
        return StringUtils.m10800a(this.f7567c);
    }

    /* renamed from: a */
    public String m11264a(boolean z) {
        return z ? this.f7573i : this.f7574j;
    }

    /* renamed from: a */
    public void m11265a(Long l) {
        this.f7568d = l;
    }

    /* renamed from: a */
    public void m11266a(String str, boolean z) {
        if (z) {
            this.f7573i = str;
        } else {
            this.f7574j = str;
        }
    }

    /* renamed from: b */
    public Long m11267b() {
        return this.f7568d;
    }

    /* renamed from: b */
    public void m11268b(Long l) {
        this.f7569e = l;
    }

    /* renamed from: b */
    public void m11269b(boolean z) {
        this.f7575k = z;
    }

    /* renamed from: c */
    public /* synthetic */ Object mo3243c() {
        return m11267b();
    }

    /* renamed from: c */
    public void m11271c(boolean z) {
        this.f7576l = z;
    }

    /* renamed from: d */
    public String m11272d() {
        try {
            if (f7566b.equals(this.f7568d)) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            return String.format("****-****-****-%4s", new Object[]{m11260i()});
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: d */
    public void m11273d(boolean z) {
        this.f7570f = z;
    }

    public int describeContents() {
        return 0;
    }

    /* renamed from: e */
    public int m11274e() {
        return (this.f7569e == null || this.f7569e.longValue() <= 0) ? Bank.m11115a(this.f7567c).m11119d() : Bank.m11114a(this.f7569e.longValue()).m11119d();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserCard)) {
            return false;
        }
        UserCard userCard = (UserCard) obj;
        if (this.f7571g != userCard.f7571g || this.f7570f != userCard.f7570f || this.f7572h != userCard.f7572h || this.f7575k != userCard.f7575k || this.f7576l != userCard.f7576l) {
            return false;
        }
        if (this.f7568d != null) {
            if (!this.f7568d.equals(userCard.f7568d)) {
                return false;
            }
        } else if (userCard.f7568d != null) {
            return false;
        }
        if (this.f7567c != null) {
            if (!this.f7567c.equals(userCard.f7567c)) {
                return false;
            }
        } else if (userCard.f7567c != null) {
            return false;
        }
        if (this.f7569e != null) {
            if (!this.f7569e.equals(userCard.f7569e)) {
                return false;
            }
        } else if (userCard.f7569e != null) {
            return false;
        }
        if (this.f7573i != null) {
            if (!this.f7573i.equals(userCard.f7573i)) {
                return false;
            }
        } else if (userCard.f7573i != null) {
            return false;
        }
        if (this.f7574j != null) {
            z = this.f7574j.equals(userCard.f7574j);
        } else if (userCard.f7574j != null) {
            z = false;
        }
        return z;
    }

    /* renamed from: f */
    public Long m11275f() {
        return this.f7569e;
    }

    /* renamed from: g */
    public void m11276g() {
        this.f7567c = m11260i();
    }

    /* renamed from: h */
    public boolean m11277h() {
        return this.f7570f;
    }

    public int hashCode() {
        int i = 1;
        int hashCode = ((this.f7575k ? 1 : 0) + (((this.f7574j != null ? this.f7574j.hashCode() : 0) + (((this.f7573i != null ? this.f7573i.hashCode() : 0) + (((this.f7572h ? 1 : 0) + (((this.f7570f ? 1 : 0) + (((this.f7571g ? 1 : 0) + (((this.f7569e != null ? this.f7569e.hashCode() : 0) + (((this.f7567c != null ? this.f7567c.hashCode() : 0) + ((this.f7568d != null ? this.f7568d.hashCode() : 0) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!this.f7576l) {
            i = 0;
        }
        return hashCode + i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        long j = 0;
        int i2 = 1;
        parcel.writeLong(this.f7568d != null ? this.f7568d.longValue() : 0);
        parcel.writeString(this.f7567c);
        if (this.f7569e != null) {
            j = this.f7569e.longValue();
        }
        parcel.writeLong(j);
        parcel.writeByte((byte) (this.f7571g ? 1 : 0));
        parcel.writeByte((byte) (this.f7570f ? 1 : 0));
        parcel.writeByte((byte) (this.f7572h ? 1 : 0));
        parcel.writeString(this.f7573i);
        parcel.writeString(this.f7574j);
        parcel.writeByte((byte) (this.f7575k ? 1 : 0));
        if (!this.f7576l) {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
    }
}
