package org.telegram.messenger.exoplayer2.audio;

import java.nio.ShortBuffer;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class Sonic {
    private static final int AMDF_FREQUENCY = 4000;
    private static final int MAXIMUM_PITCH = 400;
    private static final int MINIMUM_PITCH = 65;
    private static final boolean USE_CHORD_PITCH = false;
    private final short[] downSampleBuffer = new short[this.maxRequired];
    private short[] inputBuffer;
    private int inputBufferSize = this.maxRequired;
    private int maxDiff;
    private final int maxPeriod;
    private final int maxRequired = (this.maxPeriod * 2);
    private int minDiff;
    private final int minPeriod;
    private int newRatePosition;
    private final int numChannels;
    private int numInputSamples;
    private int numOutputSamples;
    private int numPitchSamples;
    private int oldRatePosition;
    private short[] outputBuffer;
    private int outputBufferSize;
    private float pitch;
    private short[] pitchBuffer;
    private int pitchBufferSize;
    private int prevMinDiff;
    private int prevPeriod;
    private int remainingInputToCopy;
    private final int sampleRate;
    private float speed;

    public Sonic(int sampleRate, int numChannels) {
        this.sampleRate = sampleRate;
        this.numChannels = numChannels;
        this.minPeriod = sampleRate / 400;
        this.maxPeriod = sampleRate / 65;
        this.inputBuffer = new short[(this.maxRequired * numChannels)];
        this.outputBufferSize = this.maxRequired;
        this.outputBuffer = new short[(this.maxRequired * numChannels)];
        this.pitchBufferSize = this.maxRequired;
        this.pitchBuffer = new short[(this.maxRequired * numChannels)];
        this.oldRatePosition = 0;
        this.newRatePosition = 0;
        this.prevPeriod = 0;
        this.speed = 1.0f;
        this.pitch = 1.0f;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void queueInput(ShortBuffer buffer) {
        int samplesToWrite = buffer.remaining() / this.numChannels;
        int bytesToWrite = (this.numChannels * samplesToWrite) * 2;
        enlargeInputBufferIfNeeded(samplesToWrite);
        buffer.get(this.inputBuffer, this.numInputSamples * this.numChannels, bytesToWrite / 2);
        this.numInputSamples += samplesToWrite;
        processStreamInput();
    }

    public void getOutput(ShortBuffer buffer) {
        int samplesToRead = Math.min(buffer.remaining() / this.numChannels, this.numOutputSamples);
        buffer.put(this.outputBuffer, 0, this.numChannels * samplesToRead);
        this.numOutputSamples -= samplesToRead;
        System.arraycopy(this.outputBuffer, this.numChannels * samplesToRead, this.outputBuffer, 0, this.numOutputSamples * this.numChannels);
    }

    public void queueEndOfStream() {
        int remainingSamples = this.numInputSamples;
        int expectedOutputSamples = this.numOutputSamples + ((int) ((((((float) remainingSamples) / (this.speed / this.pitch)) + ((float) this.numPitchSamples)) / this.pitch) + 0.5f));
        enlargeInputBufferIfNeeded((this.maxRequired * 2) + remainingSamples);
        for (int xSample = 0; xSample < (this.maxRequired * 2) * this.numChannels; xSample++) {
            this.inputBuffer[(this.numChannels * remainingSamples) + xSample] = (short) 0;
        }
        this.numInputSamples += this.maxRequired * 2;
        processStreamInput();
        if (this.numOutputSamples > expectedOutputSamples) {
            this.numOutputSamples = expectedOutputSamples;
        }
        this.numInputSamples = 0;
        this.remainingInputToCopy = 0;
        this.numPitchSamples = 0;
    }

    public int getSamplesAvailable() {
        return this.numOutputSamples;
    }

    private void enlargeOutputBufferIfNeeded(int numSamples) {
        if (this.numOutputSamples + numSamples > this.outputBufferSize) {
            this.outputBufferSize += (this.outputBufferSize / 2) + numSamples;
            this.outputBuffer = Arrays.copyOf(this.outputBuffer, this.outputBufferSize * this.numChannels);
        }
    }

    private void enlargeInputBufferIfNeeded(int numSamples) {
        if (this.numInputSamples + numSamples > this.inputBufferSize) {
            this.inputBufferSize += (this.inputBufferSize / 2) + numSamples;
            this.inputBuffer = Arrays.copyOf(this.inputBuffer, this.inputBufferSize * this.numChannels);
        }
    }

    private void removeProcessedInputSamples(int position) {
        int remainingSamples = this.numInputSamples - position;
        System.arraycopy(this.inputBuffer, this.numChannels * position, this.inputBuffer, 0, this.numChannels * remainingSamples);
        this.numInputSamples = remainingSamples;
    }

    private void copyToOutput(short[] samples, int position, int numSamples) {
        enlargeOutputBufferIfNeeded(numSamples);
        System.arraycopy(samples, this.numChannels * position, this.outputBuffer, this.numOutputSamples * this.numChannels, this.numChannels * numSamples);
        this.numOutputSamples += numSamples;
    }

    private int copyInputToOutput(int position) {
        int numSamples = Math.min(this.maxRequired, this.remainingInputToCopy);
        copyToOutput(this.inputBuffer, position, numSamples);
        this.remainingInputToCopy -= numSamples;
        return numSamples;
    }

    private void downSampleInput(short[] samples, int position, int skip) {
        int numSamples = this.maxRequired / skip;
        int samplesPerValue = this.numChannels * skip;
        position *= this.numChannels;
        for (int i = 0; i < numSamples; i++) {
            int value = 0;
            for (int j = 0; j < samplesPerValue; j++) {
                value += samples[((i * samplesPerValue) + position) + j];
            }
            this.downSampleBuffer[i] = (short) (value / samplesPerValue);
        }
    }

    private int findPitchPeriodInRange(short[] samples, int position, int minPeriod, int maxPeriod) {
        int bestPeriod = 0;
        int worstPeriod = 255;
        int minDiff = 1;
        int maxDiff = 0;
        position *= this.numChannels;
        for (int period = minPeriod; period <= maxPeriod; period++) {
            int diff = 0;
            for (int i = 0; i < period; i++) {
                diff += Math.abs(samples[position + i] - samples[(position + period) + i]);
            }
            if (diff * bestPeriod < minDiff * period) {
                minDiff = diff;
                bestPeriod = period;
            }
            if (diff * worstPeriod > maxDiff * period) {
                maxDiff = diff;
                worstPeriod = period;
            }
        }
        this.minDiff = minDiff / bestPeriod;
        this.maxDiff = maxDiff / worstPeriod;
        return bestPeriod;
    }

    private boolean previousPeriodBetter(int minDiff, int maxDiff, boolean preferNewPeriod) {
        if (minDiff == 0 || this.prevPeriod == 0) {
            return false;
        }
        if (preferNewPeriod) {
            if (maxDiff > minDiff * 3 || minDiff * 2 <= this.prevMinDiff * 3) {
                return false;
            }
        } else if (minDiff <= this.prevMinDiff) {
            return false;
        }
        return true;
    }

    private int findPitchPeriod(short[] samples, int position, boolean preferNewPeriod) {
        int skip;
        int period;
        int retPeriod;
        if (this.sampleRate > 4000) {
            skip = this.sampleRate / 4000;
        } else {
            skip = 1;
        }
        if (this.numChannels == 1 && skip == 1) {
            period = findPitchPeriodInRange(samples, position, this.minPeriod, this.maxPeriod);
        } else {
            downSampleInput(samples, position, skip);
            period = findPitchPeriodInRange(this.downSampleBuffer, 0, this.minPeriod / skip, this.maxPeriod / skip);
            if (skip != 1) {
                period *= skip;
                int minP = period - (skip * 4);
                int maxP = period + (skip * 4);
                if (minP < this.minPeriod) {
                    minP = this.minPeriod;
                }
                if (maxP > this.maxPeriod) {
                    maxP = this.maxPeriod;
                }
                if (this.numChannels == 1) {
                    period = findPitchPeriodInRange(samples, position, minP, maxP);
                } else {
                    downSampleInput(samples, position, 1);
                    period = findPitchPeriodInRange(this.downSampleBuffer, 0, minP, maxP);
                }
            }
        }
        if (previousPeriodBetter(this.minDiff, this.maxDiff, preferNewPeriod)) {
            retPeriod = this.prevPeriod;
        } else {
            retPeriod = period;
        }
        this.prevMinDiff = this.minDiff;
        this.prevPeriod = period;
        return retPeriod;
    }

    private void moveNewSamplesToPitchBuffer(int originalNumOutputSamples) {
        int numSamples = this.numOutputSamples - originalNumOutputSamples;
        if (this.numPitchSamples + numSamples > this.pitchBufferSize) {
            this.pitchBufferSize += (this.pitchBufferSize / 2) + numSamples;
            this.pitchBuffer = Arrays.copyOf(this.pitchBuffer, this.pitchBufferSize * this.numChannels);
        }
        System.arraycopy(this.outputBuffer, this.numChannels * originalNumOutputSamples, this.pitchBuffer, this.numPitchSamples * this.numChannels, this.numChannels * numSamples);
        this.numOutputSamples = originalNumOutputSamples;
        this.numPitchSamples += numSamples;
    }

    private void removePitchSamples(int numSamples) {
        if (numSamples != 0) {
            System.arraycopy(this.pitchBuffer, this.numChannels * numSamples, this.pitchBuffer, 0, (this.numPitchSamples - numSamples) * this.numChannels);
            this.numPitchSamples -= numSamples;
        }
    }

    private void adjustPitch(int originalNumOutputSamples) {
        if (this.numOutputSamples != originalNumOutputSamples) {
            moveNewSamplesToPitchBuffer(originalNumOutputSamples);
            int position = 0;
            while (this.numPitchSamples - position >= this.maxRequired) {
                int period = findPitchPeriod(this.pitchBuffer, position, false);
                int newPeriod = (int) (((float) period) / this.pitch);
                enlargeOutputBufferIfNeeded(newPeriod);
                if (this.pitch >= 1.0f) {
                    overlapAdd(newPeriod, this.numChannels, this.outputBuffer, this.numOutputSamples, this.pitchBuffer, position, this.pitchBuffer, (position + period) - newPeriod);
                } else {
                    int i = period;
                    overlapAddWithSeparation(i, this.numChannels, newPeriod - period, this.outputBuffer, this.numOutputSamples, this.pitchBuffer, position, this.pitchBuffer, position);
                }
                this.numOutputSamples += newPeriod;
                position += period;
            }
            removePitchSamples(position);
        }
    }

    private short interpolate(short[] in, int inPos, int oldSampleRate, int newSampleRate) {
        int rightPosition = (this.oldRatePosition + 1) * newSampleRate;
        int ratio = rightPosition - (this.newRatePosition * oldSampleRate);
        int width = rightPosition - (this.oldRatePosition * newSampleRate);
        return (short) (((ratio * in[inPos]) + ((width - ratio) * in[this.numChannels + inPos])) / width);
    }

    private void adjustRate(float rate, int originalNumOutputSamples) {
        if (this.numOutputSamples != originalNumOutputSamples) {
            int newSampleRate = (int) (((float) this.sampleRate) / rate);
            int oldSampleRate = this.sampleRate;
            while (true) {
                if (newSampleRate <= 16384 && oldSampleRate <= 16384) {
                    break;
                }
                newSampleRate /= 2;
                oldSampleRate /= 2;
            }
            moveNewSamplesToPitchBuffer(originalNumOutputSamples);
            for (int position = 0; position < this.numPitchSamples - 1; position++) {
                while ((this.oldRatePosition + 1) * newSampleRate > this.newRatePosition * oldSampleRate) {
                    enlargeOutputBufferIfNeeded(1);
                    for (int i = 0; i < this.numChannels; i++) {
                        this.outputBuffer[(this.numOutputSamples * this.numChannels) + i] = interpolate(this.pitchBuffer, (this.numChannels * position) + i, oldSampleRate, newSampleRate);
                    }
                    this.newRatePosition++;
                    this.numOutputSamples++;
                }
                this.oldRatePosition++;
                if (this.oldRatePosition == oldSampleRate) {
                    this.oldRatePosition = 0;
                    Assertions.checkState(this.newRatePosition == newSampleRate);
                    this.newRatePosition = 0;
                }
            }
            removePitchSamples(this.numPitchSamples - 1);
        }
    }

    private int skipPitchPeriod(short[] samples, int position, float speed, int period) {
        int newSamples;
        if (speed >= 2.0f) {
            newSamples = (int) (((float) period) / (speed - 1.0f));
        } else {
            newSamples = period;
            this.remainingInputToCopy = (int) ((((float) period) * (2.0f - speed)) / (speed - 1.0f));
        }
        enlargeOutputBufferIfNeeded(newSamples);
        overlapAdd(newSamples, this.numChannels, this.outputBuffer, this.numOutputSamples, samples, position, samples, position + period);
        this.numOutputSamples += newSamples;
        return newSamples;
    }

    private int insertPitchPeriod(short[] samples, int position, float speed, int period) {
        int newSamples;
        if (speed < 0.5f) {
            newSamples = (int) ((((float) period) * speed) / (1.0f - speed));
        } else {
            newSamples = period;
            this.remainingInputToCopy = (int) ((((float) period) * ((2.0f * speed) - 1.0f)) / (1.0f - speed));
        }
        enlargeOutputBufferIfNeeded(period + newSamples);
        System.arraycopy(samples, this.numChannels * position, this.outputBuffer, this.numOutputSamples * this.numChannels, this.numChannels * period);
        overlapAdd(newSamples, this.numChannels, this.outputBuffer, this.numOutputSamples + period, samples, position + period, samples, position);
        this.numOutputSamples += period + newSamples;
        return newSamples;
    }

    private void changeSpeed(float speed) {
        if (this.numInputSamples >= this.maxRequired) {
            int numSamples = this.numInputSamples;
            int position = 0;
            do {
                if (this.remainingInputToCopy > 0) {
                    position += copyInputToOutput(position);
                } else {
                    int period = findPitchPeriod(this.inputBuffer, position, true);
                    if (((double) speed) > 1.0d) {
                        position += skipPitchPeriod(this.inputBuffer, position, speed, period) + period;
                    } else {
                        position += insertPitchPeriod(this.inputBuffer, position, speed, period);
                    }
                }
            } while (this.maxRequired + position <= numSamples);
            removeProcessedInputSamples(position);
        }
    }

    private void processStreamInput() {
        int originalNumOutputSamples = this.numOutputSamples;
        float s = this.speed / this.pitch;
        if (((double) s) > 1.00001d || ((double) s) < 0.99999d) {
            changeSpeed(s);
        } else {
            copyToOutput(this.inputBuffer, 0, this.numInputSamples);
            this.numInputSamples = 0;
        }
        if (this.pitch != 1.0f) {
            adjustRate(this.pitch, originalNumOutputSamples);
        }
    }

    private static void overlapAdd(int numSamples, int numChannels, short[] out, int outPos, short[] rampDown, int rampDownPos, short[] rampUp, int rampUpPos) {
        for (int i = 0; i < numChannels; i++) {
            int o = (outPos * numChannels) + i;
            int u = (rampUpPos * numChannels) + i;
            int d = (rampDownPos * numChannels) + i;
            for (int t = 0; t < numSamples; t++) {
                out[o] = (short) (((rampDown[d] * (numSamples - t)) + (rampUp[u] * t)) / numSamples);
                o += numChannels;
                d += numChannels;
                u += numChannels;
            }
        }
    }

    private static void overlapAddWithSeparation(int numSamples, int numChannels, int separation, short[] out, int outPos, short[] rampDown, int rampDownPos, short[] rampUp, int rampUpPos) {
        for (int i = 0; i < numChannels; i++) {
            int o = (outPos * numChannels) + i;
            int u = (rampUpPos * numChannels) + i;
            int d = (rampDownPos * numChannels) + i;
            for (int t = 0; t < numSamples + separation; t++) {
                if (t < separation) {
                    out[o] = (short) ((rampDown[d] * (numSamples - t)) / numSamples);
                    d += numChannels;
                } else if (t < numSamples) {
                    out[o] = (short) (((rampDown[d] * (numSamples - t)) + (rampUp[u] * (t - separation))) / numSamples);
                    d += numChannels;
                    u += numChannels;
                } else {
                    out[o] = (short) ((rampUp[u] * (t - separation)) / numSamples);
                    u += numChannels;
                }
                o += numChannels;
            }
        }
    }
}
