package org.telegram.customization.util.view.Poll;

import java.util.ArrayList;

public class PollResultModel {
    ArrayList<String> options;
    ArrayList<Integer> results;
    boolean status;

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Integer> getResults() {
        return this.results;
    }

    public void setResults(ArrayList<Integer> results) {
        this.results = results;
    }
}
