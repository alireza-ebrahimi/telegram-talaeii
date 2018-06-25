package org.telegram.customization.util.view.Poll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PollModel {
    ArrayList<PollItem> CandidateVoteResult;
    String barBackColor;
    int id;
    String imageUrl;
    String subTitle;
    String title;
    int type;

    /* renamed from: org.telegram.customization.util.view.Poll.PollModel$1 */
    class C12541 implements Comparator<PollItem> {
        C12541() {
        }

        public int compare(PollItem item1, PollItem item2) {
            return item1.getSortOrder() - item2.getSortOrder();
        }
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBarBackColor() {
        return this.barBackColor;
    }

    public void setBarBackColor(String barBackColor) {
        this.barBackColor = barBackColor;
    }

    public ArrayList<PollItem> getCandidateVoteResult() {
        Collections.sort(this.CandidateVoteResult, new C12541());
        return this.CandidateVoteResult;
    }

    public void setCandidateVoteResult(ArrayList<PollItem> candidateVoteResult) {
        this.CandidateVoteResult = candidateVoteResult;
    }
}
