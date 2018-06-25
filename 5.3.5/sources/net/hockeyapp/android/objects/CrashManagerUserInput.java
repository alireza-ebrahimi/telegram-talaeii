package net.hockeyapp.android.objects;

public enum CrashManagerUserInput {
    CrashManagerUserInputDontSend(0),
    CrashManagerUserInputSend(1),
    CrashManagerUserInputAlwaysSend(2);
    
    private final int mValue;

    private CrashManagerUserInput(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return this.mValue;
    }
}
