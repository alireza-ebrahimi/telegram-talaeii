package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.ResourceUtils;
import com.persianswitch.sdk.base.utils.TODO;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.ResultPack;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.SyncWebService;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
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
    private static final String TAG = "CardManager";
    private final Context mContext;
    public final SyncCardCallback mDefaultCallback = new C07921();

    public interface SyncCardCallback {
        void onCardsSynced();

        void onError(WSResponse wSResponse);
    }

    /* renamed from: com.persianswitch.sdk.payment.managers.CardManager$1 */
    class C07921 implements SyncCardCallback {
        C07921() {
        }

        public void onCardsSynced() {
            BaseSetting.setLastTimeCardsSynced(CardManager.this.mContext, System.currentTimeMillis());
            SDKLog.m39i(CardManager.TAG, "onCardsSynced [timestamp = %d]", Long.valueOf(timestamp));
        }

        public void onError(WSResponse wsResponse) {
        }
    }

    private static class ResponseCardProtocolConverter implements ProtocolConverter<ResponseCard> {
        private final Context mContext;
        private final UserCard mCurrentCard;
        private final boolean mRequestSendCards;
        private final CardProtocolConverter mUserCardConverter = new CardProtocolConverter(this.mContext);

        static class ResponseCard {
            final List<UserCard> cards;
            final boolean currentCardChanged;
            final boolean keepCard;
            final boolean removeAnythingExceptThis;

            ResponseCard(List<UserCard> cards, boolean removeAnythingExceptThis, boolean currentCardChanged, boolean keepCard) {
                this.cards = cards;
                this.removeAnythingExceptThis = removeAnythingExceptThis;
                this.currentCardChanged = currentCardChanged;
                this.keepCard = keepCard;
            }
        }

        ResponseCardProtocolConverter(Context context, boolean requestSendCards, UserCard currentCard) {
            this.mContext = context;
            this.mRequestSendCards = requestSendCards;
            this.mCurrentCard = currentCard;
        }

        public String serialize(ResponseCard object) {
            return (String) TODO.notImplementedYet();
        }

        public ResponseCard deserialize(String protocol) {
            boolean isAllCards = this.mRequestSendCards;
            boolean currentCardChanged = false;
            boolean keepCard = false;
            List<UserCard> deserializeCards = new ArrayList();
            try {
                String[] splitSegment = StringUtils.toNonNullString(protocol).split("&&", 3);
                ExpirationStatus expirationStatus = ExpirationStatus.getInstance(splitSegment[1]);
                if (!StringUtils.isEquals("0;0", splitSegment[0])) {
                    Long newCardId = StringUtils.toLong(StringUtils.toNonNullString(splitSegment[0]).split(";")[0]);
                    Long newBankId = StringUtils.toLong(StringUtils.toNonNullString(splitSegment[0]).split(";")[1]);
                    this.mCurrentCard.removeAllExceptLastDigits();
                    this.mCurrentCard.setCardId(newCardId);
                    this.mCurrentCard.setBankId(newBankId);
                    new CardRepo(this.mContext).createOrUpdate(this.mCurrentCard);
                    if (newCardId != null && newBankId != null && newCardId.longValue() > 0 && newBankId.longValue() > 0) {
                        int bankNameResourceId = Bank.getById(newBankId.longValue()).getBankNameResourceId();
                        if (bankNameResourceId > 0) {
                            if (this.mCurrentCard.getTitle(true) == null) {
                                this.mCurrentCard.setTitle(ResourceUtils.getStringForLang(this.mContext, LanguageManager.PERSIAN, bankNameResourceId), true);
                            }
                            if (this.mCurrentCard.getTitle(false) == null) {
                                this.mCurrentCard.setTitle(ResourceUtils.getStringForLang(this.mContext, LanguageManager.ENGLISH, bankNameResourceId), false);
                            }
                        }
                        deserializeCards.add(this.mCurrentCard);
                    }
                }
                if (expirationStatus == ExpirationStatus.SAVED) {
                    if (!this.mCurrentCard.isExpirySaved()) {
                        currentCardChanged = true;
                    }
                    this.mCurrentCard.setExpirationSaved(true);
                    new CardRepo(this.mContext).createOrUpdate(this.mCurrentCard);
                } else if (expirationStatus == ExpirationStatus.REMOVE_CAUSE_CHANGED) {
                    if (this.mCurrentCard.isExpirySaved()) {
                        currentCardChanged = true;
                        keepCard = true;
                    }
                    this.mCurrentCard.setExpirationSaved(false);
                    new CardRepo(this.mContext).createOrUpdate(this.mCurrentCard);
                }
                if (this.mRequestSendCards) {
                    String otherCardsSegment = StringUtils.toNonNullString(splitSegment[2]);
                    if ("1;1;1;1;".equals(otherCardsSegment)) {
                        isAllCards = true;
                    } else if ("0;0;0;0".equals(otherCardsSegment)) {
                        isAllCards = false;
                    } else {
                        isAllCards = true;
                        for (String otherCard : StringUtils.toNonNullString(otherCardsSegment).split("&")) {
                            deserializeCards.add(this.mUserCardConverter.deserialize(otherCard));
                        }
                    }
                }
            } catch (Exception e) {
                SDKLog.m34d(CardManager.TAG, "New Card Don't Save In Database", e, new Object[0]);
            }
            return new ResponseCard(deserializeCards, isAllCards, currentCardChanged, keepCard);
        }
    }

    public CardManager(Context context) {
        this.mContext = context;
    }

    public SyncCardCallback getDefaultCallback() {
        return this.mDefaultCallback;
    }

    public void syncByResponse(boolean sendCards, UserCard currentCard, WSTranResponse response, ISuggestionUpdate updateCallback) {
        ResponseCard responseCard = new ResponseCardProtocolConverter(this.mContext, sendCards, currentCard).deserialize(response.getCardId());
        boolean currentCardChanged = responseCard.currentCardChanged;
        boolean keepCard = responseCard.keepCard;
        if (responseCard.removeAnythingExceptThis) {
            clearAllCards();
        }
        try {
            updateCards(responseCard.cards, this.mDefaultCallback);
        } catch (Exception e) {
            this.mDefaultCallback.onError(response);
        }
        if (sendCards) {
            this.mDefaultCallback.onCardsSynced();
        }
        if (response.getStatus() == StatusCode.CARD_NOT_FOUND) {
            new CardRepo(this.mContext).delete((IPhoenixModel) currentCard);
            currentCardChanged = true;
            keepCard = false;
        } else if (response.getStatus() == StatusCode.EXPIRATION_DATE_NOT_FOUND) {
            currentCard.setExpirationSaved(false);
            new CardRepo(this.mContext).createOrUpdate(currentCard);
            currentCardChanged = true;
            keepCard = true;
        }
        if (updateCallback != null && currentCardChanged) {
            updateCallback.updateCardSuggestion(keepCard);
        }
    }

    private void clearAllCards() {
        new CardRepo(this.mContext).delete(Where.any());
    }

    private void updateCards(List<UserCard> cards, SyncCardCallback syncCallback) throws Exception {
        CardRepo cardRepo = new CardRepo(this.mContext.getApplicationContext());
        if (cards != null) {
            for (UserCard card : cards) {
                cardRepo.createOrUpdate(card);
            }
        }
        if (syncCallback != null) {
            syncCallback.onCardsSynced();
        }
    }

    public void syncInCurrentThread(SyncCardCallback syncCallback) {
        Context context = this.mContext;
        WSTranRequest request = WSTranRequest.create(this.mContext, new SDKConfig(), OpCode.SYNC_CARDS_BY_SERVER.getCode(), 0);
        request.setHostData(HostDataRequestField.withHostVersion(context).toJson());
        request.setLegacyExtraData(new String[]{""});
        ResultPack resultPack = SyncWebService.create(request).syncLaunch(this.mContext, new SDKSyncWebServiceCallback());
        WSResponse wsResponse = resultPack.getResponse();
        if (resultPack.getStatus() == TransactionStatus.SUCCESS) {
            onSyncWebServiceSuccessful(wsResponse, syncCallback);
        } else {
            getDefaultCallback().onError(wsResponse);
        }
    }

    private void onSyncWebServiceSuccessful(WSResponse result, SyncCardCallback syncCallback) {
        String flattedCardList = result.getLegacyExtraData()[1];
        if ("0".equalsIgnoreCase(flattedCardList)) {
            clearAllCards();
            if (syncCallback != null) {
                syncCallback.onCardsSynced();
            }
        } else if (StringUtils.isEmpty(flattedCardList)) {
            syncCallback.onError(result);
        } else {
            try {
                List<UserCard> cards = new ArrayList();
                if (!StringUtils.isEmpty(flattedCardList)) {
                    CardProtocolConverter converter = new CardProtocolConverter(this.mContext);
                    for (String flattedCard : flattedCardList.split("&")) {
                        cards.add(converter.deserialize(flattedCard));
                    }
                }
                new CardRepo(this.mContext).delete(Where.any());
                updateCards(cards, syncCallback);
            } catch (Exception e) {
                syncCallback.onError(result);
            }
        }
    }

    public void sync() {
        sync(this.mDefaultCallback);
    }

    public void sync(final SyncCardCallback syncCallback) {
        Context context = this.mContext;
        WSTranRequest request = WSTranRequest.create(this.mContext, new SDKConfig(), OpCode.SYNC_CARDS_BY_SERVER.getCode(), 0);
        request.setHostData(HostDataRequestField.withHostVersion(this.mContext).toJson());
        request.setLegacyExtraData(new String[]{""});
        WebService.create(request).launch(this.mContext, new SDKWebServiceCallback<WSResponse>() {
            public void onPreExecute() {
            }

            public void onSuccessful(String message, WSResponse result) {
                CardManager.this.onSyncWebServiceSuccessful(result, syncCallback);
            }

            public void onError(WSStatus error, String errorMessage, WSResponse response) {
                if (syncCallback != null) {
                    syncCallback.onError(response);
                }
            }
        });
    }
}
