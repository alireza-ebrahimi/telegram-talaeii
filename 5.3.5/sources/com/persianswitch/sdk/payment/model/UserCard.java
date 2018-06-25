package com.persianswitch.sdk.payment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.fastkit.FilterableModel;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.ResourceUtils;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.payment.managers.ProtocolConverter;
import com.thin.downloadmanager.BuildConfig;
import java.util.StringTokenizer;

public class UserCard implements Parcelable, IPhoenixModel<Long>, FilterableModel {
    public static final UserCard AP_CARD = new UserCard("-1", ASAN_PARDAKHT_SCORE_PAYMENT_CARD, Long.valueOf(Bank.ASAN_PARDAKHT.getBankId()));
    public static final String AP_CARD_NO = "9832540000000733";
    private static final Long ASAN_PARDAKHT_SCORE_PAYMENT_CARD = Long.valueOf(-1);
    public static final Creator<UserCard> CREATOR = new C08031();
    private Long mBankId;
    private Long mCardId;
    private String mCardNo;
    private boolean mDefaultCard = false;
    private boolean mExpirationSaved = false;
    private boolean mRegisterCard;
    private boolean mRemovable = true;
    private boolean mSendCards;
    private String mTitleEn;
    private String mTitleFa;

    /* renamed from: com.persianswitch.sdk.payment.model.UserCard$1 */
    static class C08031 implements Creator<UserCard> {
        C08031() {
        }

        public UserCard createFromParcel(Parcel in) {
            return new UserCard(in);
        }

        public UserCard[] newArray(int size) {
            return new UserCard[size];
        }
    }

    public static final class CardProtocolConverter implements ProtocolConverter<UserCard> {
        private final Context mContext;

        public CardProtocolConverter(Context context) {
            this.mContext = context;
        }

        public String serialize(UserCard object) {
            int i;
            long j = 0;
            int i2 = 1;
            StringBuilder append = new StringBuilder().append(object.mCardId == null ? 0 : object.mCardId.longValue()).append(";").append(object.mCardNo == null ? Integer.valueOf(0) : object.mCardNo).append(";");
            if (object.mBankId != null) {
                j = object.mBankId.longValue();
            }
            StringBuilder append2 = append.append(j).append(";");
            if (object.mRegisterCard) {
                i = 1;
            } else {
                i = 0;
            }
            append = append2.append(i).append(";");
            if (!object.mSendCards) {
                i2 = 0;
            }
            return append.append(i2).toString();
        }

        public UserCard deserialize(String protocol) {
            StringTokenizer tokenizer = new StringTokenizer(protocol, ";");
            UserCard userCard = new UserCard();
            userCard.mCardId = Long.valueOf(tokenizer.nextToken());
            userCard.mCardNo = tokenizer.nextToken();
            userCard.mBankId = Long.valueOf(tokenizer.nextToken());
            userCard.mExpirationSaved = tokenizer.nextToken().equals(BuildConfig.VERSION_NAME);
            String configSegment = tokenizer.nextToken();
            boolean z = configSegment != null && configSegment.length() > 0 && configSegment.startsWith(BuildConfig.VERSION_NAME);
            userCard.mRemovable = z;
            userCard.mTitleEn = UserCard.extractCardName(this.mContext, false, userCard.mBankId, tokenizer.nextToken());
            userCard.mTitleFa = UserCard.extractCardName(this.mContext, true, userCard.mBankId, tokenizer.nextToken());
            return userCard;
        }
    }

    public static final class CardTable extends Table<UserCard> {
        public static final Column<Long> COLUMN_BANK_ID = new Column("BankId", Long.class);
        public static final Column<Long> COLUMN_CARD_ID = new Column("CardId", Long.class, true);
        public static final Column<String> COLUMN_CARD_NO = new Column("CardNo", String.class);
        public static final Column<Boolean> COLUMN_IS_DEFAULT = new Column("IsDefault", Boolean.class);
        public static final Column<Boolean> COLUMN_IS_EXPIRATION_SAVED = new Column("IsExpirationSaved", Boolean.class);
        public static final Column<Boolean> COLUMN_IS_REMOVABLE = new Column("IsRemovable", Boolean.class);
        public static final Column<String> COLUMN_TITLE_EN = new Column("TitleEn", String.class);
        public static final Column<String> COLUMN_TITLE_FA = new Column("TitleFa", String.class);
        private final CardEntityConverter mCardCursorParser = new CardEntityConverter();

        private static class CardEntityConverter implements EntityConverter<UserCard> {
            private CardEntityConverter() {
            }

            public UserCard fromCursor(Cursor cursor) throws SQLException {
                Long cardId = (Long) CardTable.COLUMN_CARD_ID.getValue(cursor);
                Long bankId = (Long) CardTable.COLUMN_BANK_ID.getValue(cursor);
                String cardNo = (String) CardTable.COLUMN_CARD_NO.getValue(cursor);
                String titleFa = (String) CardTable.COLUMN_TITLE_FA.getValue(cursor);
                String titleEn = (String) CardTable.COLUMN_TITLE_EN.getValue(cursor);
                Boolean isDefault = (Boolean) CardTable.COLUMN_IS_DEFAULT.getValue(cursor);
                Boolean isExpirationSaved = (Boolean) CardTable.COLUMN_IS_EXPIRATION_SAVED.getValue(cursor);
                Boolean isRemovable = (Boolean) CardTable.COLUMN_IS_REMOVABLE.getValue(cursor);
                UserCard userCard = new UserCard();
                userCard.mCardId = cardId;
                userCard.mBankId = bankId;
                userCard.mCardNo = cardNo;
                userCard.mTitleFa = titleFa;
                userCard.mTitleEn = titleEn;
                userCard.mDefaultCard = isDefault.booleanValue();
                userCard.mExpirationSaved = isExpirationSaved.booleanValue();
                userCard.mRemovable = isRemovable.booleanValue();
                return userCard;
            }

            public void populateContentValues(UserCard entity, ContentValues cv) throws SQLException {
                CardTable.COLUMN_CARD_ID.putValue(entity.mCardId, cv);
                CardTable.COLUMN_BANK_ID.putValue(entity.mBankId, cv);
                CardTable.COLUMN_CARD_NO.putValue(entity.mCardNo, cv);
                CardTable.COLUMN_TITLE_FA.putValue(entity.mTitleFa, cv);
                CardTable.COLUMN_TITLE_EN.putValue(entity.mTitleEn, cv);
                CardTable.COLUMN_IS_DEFAULT.putValue(Boolean.valueOf(entity.mDefaultCard), cv);
                CardTable.COLUMN_IS_EXPIRATION_SAVED.putValue(Boolean.valueOf(entity.mExpirationSaved), cv);
                CardTable.COLUMN_IS_REMOVABLE.putValue(Boolean.valueOf(entity.mRemovable), cv);
            }
        }

        public String getName() {
            return "Cards";
        }

        public Column getIdColumn() {
            return COLUMN_CARD_ID;
        }

        public Column[] getColumns() {
            return new Column[]{COLUMN_CARD_ID, COLUMN_BANK_ID, COLUMN_CARD_NO, COLUMN_TITLE_FA, COLUMN_TITLE_EN, COLUMN_IS_DEFAULT, COLUMN_IS_EXPIRATION_SAVED, COLUMN_IS_REMOVABLE};
        }

        public EntityConverter<UserCard> getEntityConverter() {
            return this.mCardCursorParser;
        }
    }

    static {
        UserCard userCard = AP_CARD;
        UserCard userCard2 = AP_CARD;
        Long valueOf = Long.valueOf(0);
        userCard2.mBankId = valueOf;
        userCard.mCardId = valueOf;
        AP_CARD.mCardNo = AP_CARD_NO;
        AP_CARD.mRegisterCard = false;
    }

    public UserCard(String cardNo) {
        this.mCardNo = cardNo;
        this.mBankId = Long.valueOf(Bank.getByCardNo(cardNo).getBankId());
    }

    public UserCard(String cardNo, Long cardId, Long bankId) {
        this.mCardNo = cardNo;
        this.mCardId = cardId;
        this.mBankId = bankId;
    }

    public UserCard(String cardNo, Long cardId, Long bankId, boolean expirationSaved) {
        this.mCardNo = cardNo;
        this.mCardId = cardId;
        this.mBankId = bankId;
        this.mExpirationSaved = expirationSaved;
    }

    protected UserCard(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.mCardId = Long.valueOf(in.readLong());
        this.mCardNo = in.readString();
        this.mBankId = Long.valueOf(in.readLong());
        this.mDefaultCard = in.readByte() != (byte) 0;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mExpirationSaved = z;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mRemovable = z;
        this.mTitleFa = in.readString();
        this.mTitleEn = in.readString();
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mRegisterCard = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.mSendCards = z2;
    }

    public static UserCard fromString(String string) {
        UserCard userCard = new UserCard();
        StringTokenizer tokenizer = new StringTokenizer(string, ";");
        if (tokenizer.countTokens() >= 7) {
            userCard.mCardId = Long.valueOf(tokenizer.nextToken());
            userCard.mCardNo = tokenizer.nextToken();
            userCard.mBankId = Long.valueOf(tokenizer.nextToken());
            userCard.mExpirationSaved = BuildConfig.VERSION_NAME.equals(tokenizer.nextToken());
            String configSegment = tokenizer.nextToken();
            boolean z = configSegment != null && configSegment.length() > 0 && configSegment.startsWith(BuildConfig.VERSION_NAME);
            userCard.mRemovable = z;
            userCard.mTitleEn = tokenizer.nextToken();
            userCard.mTitleFa = tokenizer.nextToken();
        }
        return userCard;
    }

    private static String extractCardName(@NonNull Context context, boolean isPersian, @NonNull Long bankId, String serverName) {
        int clientNameResId = Bank.getById(bankId.longValue()).getBankNameResourceId();
        if (!StringUtils.isEmpty(serverName) && !"#".equals(serverName)) {
            return serverName;
        }
        if (bankId.longValue() <= 0) {
            return "";
        }
        return ResourceUtils.getStringForLang(context, isPersian ? LanguageManager.PERSIAN : LanguageManager.ENGLISH, clientNameResId);
    }

    public String getTitle(boolean isPersians) {
        return isPersians ? this.mTitleFa : this.mTitleEn;
    }

    public Long getDatabaseId() {
        return this.mCardId;
    }

    public String getCardDisplayName() {
        try {
            if (ASAN_PARDAKHT_SCORE_PAYMENT_CARD.equals(this.mCardId)) {
                return "";
            }
            return String.format("****-****-****-%4s", new Object[]{getLastDigits()});
        } catch (Exception e) {
            return "";
        }
    }

    @NonNull
    private String getLastDigits() {
        if (this.mCardNo.length() >= 4) {
            return this.mCardNo.substring(this.mCardNo.length() - 4, this.mCardNo.length());
        }
        return "";
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCard)) {
            return false;
        }
        UserCard userCard = (UserCard) o;
        if (this.mDefaultCard != userCard.mDefaultCard || this.mExpirationSaved != userCard.mExpirationSaved || this.mRemovable != userCard.mRemovable || this.mRegisterCard != userCard.mRegisterCard || this.mSendCards != userCard.mSendCards) {
            return false;
        }
        if (this.mCardId != null) {
            if (!this.mCardId.equals(userCard.mCardId)) {
                return false;
            }
        } else if (userCard.mCardId != null) {
            return false;
        }
        if (this.mCardNo != null) {
            if (!this.mCardNo.equals(userCard.mCardNo)) {
                return false;
            }
        } else if (userCard.mCardNo != null) {
            return false;
        }
        if (this.mBankId != null) {
            if (!this.mBankId.equals(userCard.mBankId)) {
                return false;
            }
        } else if (userCard.mBankId != null) {
            return false;
        }
        if (this.mTitleFa != null) {
            if (!this.mTitleFa.equals(userCard.mTitleFa)) {
                return false;
            }
        } else if (userCard.mTitleFa != null) {
            return false;
        }
        if (this.mTitleEn != null) {
            z = this.mTitleEn.equals(userCard.mTitleEn);
        } else if (userCard.mTitleEn != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 1;
        if (this.mCardId != null) {
            result = this.mCardId.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.mCardNo != null) {
            hashCode = this.mCardNo.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mBankId != null) {
            hashCode = this.mBankId.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mDefaultCard) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mExpirationSaved) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mRemovable) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mTitleFa != null) {
            hashCode = this.mTitleFa.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mTitleEn != null) {
            hashCode = this.mTitleEn.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.mRegisterCard) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (!this.mSendCards) {
            i = 0;
        }
        return hashCode + i;
    }

    public int getLogoResource() {
        if (this.mBankId == null || this.mBankId.longValue() <= 0) {
            return Bank.getByCardNo(this.mCardNo).getBankLogoResource();
        }
        return Bank.getById(this.mBankId.longValue()).getBankLogoResource();
    }

    public void setRegisterCard(boolean registerCard) {
        this.mRegisterCard = registerCard;
    }

    public void setSendCards(boolean sendCards) {
        this.mSendCards = sendCards;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        long j = 0;
        int i2 = 1;
        dest.writeLong(this.mCardId != null ? this.mCardId.longValue() : 0);
        dest.writeString(this.mCardNo);
        if (this.mBankId != null) {
            j = this.mBankId.longValue();
        }
        dest.writeLong(j);
        if (this.mDefaultCard) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.mExpirationSaved) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.mRemovable) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeString(this.mTitleFa);
        dest.writeString(this.mTitleEn);
        if (this.mRegisterCard) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (!this.mSendCards) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
    }

    public String filterOn() {
        return StringUtils.toNonNullString(this.mCardNo);
    }

    public void setCardId(Long cardId) {
        this.mCardId = cardId;
    }

    public void setBankId(Long bankId) {
        this.mBankId = bankId;
    }

    public Long getBankId() {
        return this.mBankId;
    }

    public String getCardNo() {
        return this.mCardNo;
    }

    public void removeAllExceptLastDigits() {
        this.mCardNo = getLastDigits();
    }

    public void setExpirationSaved(boolean val) {
        this.mExpirationSaved = val;
    }

    public void setTitle(String title, boolean isPersian) {
        if (isPersian) {
            this.mTitleFa = title;
        } else {
            this.mTitleEn = title;
        }
    }

    public boolean isExpirySaved() {
        return this.mExpirationSaved;
    }
}
