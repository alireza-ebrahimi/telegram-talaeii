package org.telegram.messenger.exoplayer2;

public final class RendererConfiguration {
    public static final RendererConfiguration DEFAULT = new RendererConfiguration(0);
    public final int tunnelingAudioSessionId;

    public RendererConfiguration(int tunnelingAudioSessionId) {
        this.tunnelingAudioSessionId = tunnelingAudioSessionId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this.tunnelingAudioSessionId != ((RendererConfiguration) obj).tunnelingAudioSessionId) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.tunnelingAudioSessionId;
    }
}
