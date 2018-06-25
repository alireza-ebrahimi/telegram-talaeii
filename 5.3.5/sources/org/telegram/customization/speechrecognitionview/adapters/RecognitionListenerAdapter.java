package org.telegram.customization.speechrecognitionview.adapters;

import android.os.Bundle;
import android.speech.RecognitionListener;

public abstract class RecognitionListenerAdapter implements RecognitionListener {
    public void onReadyForSpeech(Bundle params) {
    }

    public void onBeginningOfSpeech() {
    }

    public void onRmsChanged(float rmsdB) {
    }

    public void onBufferReceived(byte[] buffer) {
    }

    public void onEndOfSpeech() {
    }

    public void onError(int error) {
    }

    public void onResults(Bundle results) {
    }

    public void onPartialResults(Bundle partialResults) {
    }

    public void onEvent(int eventType, Bundle params) {
    }
}
