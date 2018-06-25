package com.persianswitch.sdk.payment.managers.suggestion;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelAutoComplete;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.payment.CardSuggestAdapter;
import com.persianswitch.sdk.payment.repo.CardRepo;

public final class InputSuggestionManager {
    /* renamed from: a */
    public static void m11110a(final ApLabelAutoComplete apLabelAutoComplete, UserCard userCard, final SuggestionListener<UserCard> suggestionListener) {
        if (apLabelAutoComplete != null) {
            Context context = apLabelAutoComplete.getContext();
            apLabelAutoComplete.getInnerInput().setAdapter(new CardSuggestAdapter(context, new CardRepo(context).m10611a()));
            apLabelAutoComplete.getInnerInput().setFilterEnabled(false);
            if (userCard != null) {
                apLabelAutoComplete.setText(userCard.m11272d());
                if (suggestionListener != null) {
                    suggestionListener.mo3312a(userCard);
                }
                if (userCard.m11274e() > 0) {
                    apLabelAutoComplete.setStartImage(userCard.m11274e());
                } else {
                    apLabelAutoComplete.setStartImage(-1);
                }
                apLabelAutoComplete.getInnerInput().setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (apLabelAutoComplete.getText().length() < 16) {
                            return false;
                        }
                        if (suggestionListener != null) {
                            suggestionListener.mo3311a();
                        }
                        apLabelAutoComplete.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        apLabelAutoComplete.setStartImage(-1);
                        apLabelAutoComplete.getInnerInput().m11000b();
                        return true;
                    }
                });
            }
            apLabelAutoComplete.getInnerInput().setOnItemClickListener(new OnItemClickListener() {

                /* renamed from: com.persianswitch.sdk.payment.managers.suggestion.InputSuggestionManager$2$1 */
                class C22881 implements OnTouchListener {
                    /* renamed from: a */
                    final /* synthetic */ C22892 f7421a;

                    C22881(C22892 c22892) {
                        this.f7421a = c22892;
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (apLabelAutoComplete.getText().length() < 16) {
                            return false;
                        }
                        if (suggestionListener != null) {
                            suggestionListener.mo3311a();
                        }
                        apLabelAutoComplete.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        apLabelAutoComplete.setStartImage(-1);
                        apLabelAutoComplete.getInnerInput().m11000b();
                        return true;
                    }
                }

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    UserCard userCard = (UserCard) adapterView.getItemAtPosition(i);
                    apLabelAutoComplete.setText(userCard.m11272d());
                    apLabelAutoComplete.getInnerInput().setOnTouchListener(new C22881(this));
                    if (userCard.m11274e() > 0) {
                        apLabelAutoComplete.setStartImage(userCard.m11274e());
                    } else {
                        apLabelAutoComplete.setStartImage(-1);
                    }
                    if (suggestionListener != null) {
                        suggestionListener.mo3312a(userCard);
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public static void m11111a(ApLabelAutoComplete apLabelAutoComplete, boolean z, SuggestionListener<UserCard> suggestionListener) {
        Context context = apLabelAutoComplete.getContext();
        apLabelAutoComplete.getInnerInput().setAdapter(new CardSuggestAdapter(context, new CardRepo(context).m10611a()));
        apLabelAutoComplete.getInnerInput().setFilterEnabled(false);
        if (z) {
            apLabelAutoComplete.setText(null);
            suggestionListener.mo3311a();
        }
    }
}
