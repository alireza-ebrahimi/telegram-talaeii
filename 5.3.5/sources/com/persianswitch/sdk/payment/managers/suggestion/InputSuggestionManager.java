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
    public static void updateSuggestCards(ApLabelAutoComplete input, boolean clearText, SuggestionListener<UserCard> listener) {
        Context context = input.getContext();
        input.getInnerInput().setAdapter(new CardSuggestAdapter(context, new CardRepo(context).getAll()));
        input.getInnerInput().setFilterEnabled(false);
        if (clearText) {
            input.setText(null);
            listener.onClear();
        }
    }

    public static void suggestCard(final ApLabelAutoComplete input, UserCard defaultCard, final SuggestionListener<UserCard> listener) {
        if (input != null) {
            Context context = input.getContext();
            input.getInnerInput().setAdapter(new CardSuggestAdapter(context, new CardRepo(context).getAll()));
            input.getInnerInput().setFilterEnabled(false);
            if (defaultCard != null) {
                input.setText(defaultCard.getCardDisplayName());
                if (listener != null) {
                    listener.onSelect(defaultCard);
                }
                if (defaultCard.getLogoResource() > 0) {
                    input.setStartImage(defaultCard.getLogoResource());
                } else {
                    input.setStartImage(-1);
                }
                input.getInnerInput().setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        if (input.getText().length() < 16) {
                            return false;
                        }
                        if (listener != null) {
                            listener.onClear();
                        }
                        input.setText("");
                        input.setStartImage(-1);
                        input.getInnerInput().setDefaultTouchListener();
                        return true;
                    }
                });
            }
            input.getInnerInput().setOnItemClickListener(new OnItemClickListener() {

                /* renamed from: com.persianswitch.sdk.payment.managers.suggestion.InputSuggestionManager$2$1 */
                class C07961 implements OnTouchListener {
                    C07961() {
                    }

                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        if (input.getText().length() < 16) {
                            return false;
                        }
                        if (listener != null) {
                            listener.onClear();
                        }
                        input.setText("");
                        input.setStartImage(-1);
                        input.getInnerInput().setDefaultTouchListener();
                        return true;
                    }
                }

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UserCard userCard = (UserCard) parent.getItemAtPosition(position);
                    input.setText(userCard.getCardDisplayName());
                    input.getInnerInput().setOnTouchListener(new C07961());
                    if (userCard.getLogoResource() > 0) {
                        input.setStartImage(userCard.getLogoResource());
                    } else {
                        input.setStartImage(-1);
                    }
                    if (listener != null) {
                        listener.onSelect(userCard);
                    }
                }
            });
        }
    }
}
