package com.squareup.picasso;

public interface Callback {

    public static class EmptyCallback implements Callback {
        public void onSuccess() {
        }

        public void onError() {
        }
    }

    void onError();

    void onSuccess();
}
