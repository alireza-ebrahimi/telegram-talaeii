package com.crashlytics.android.core;

import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.CustomAttributeData;
import com.crashlytics.android.core.internal.models.DeviceData;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.crashlytics.android.core.internal.models.SignalData;
import com.crashlytics.android.core.internal.models.ThreadData;
import com.crashlytics.android.core.internal.models.ThreadData.FrameData;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

class NativeCrashWriter {
    private static final SignalData DEFAULT_SIGNAL = new SignalData("", "", 0);
    private static final BinaryImageMessage[] EMPTY_BINARY_IMAGE_MESSAGES = new BinaryImageMessage[0];
    private static final ProtobufMessage[] EMPTY_CHILDREN = new ProtobufMessage[0];
    private static final CustomAttributeMessage[] EMPTY_CUSTOM_ATTRIBUTE_MESSAGES = new CustomAttributeMessage[0];
    private static final FrameMessage[] EMPTY_FRAME_MESSAGES = new FrameMessage[0];
    private static final ThreadMessage[] EMPTY_THREAD_MESSAGES = new ThreadMessage[0];
    static final String NDK_CRASH_TYPE = "ndk-crash";

    private static abstract class ProtobufMessage {
        private final ProtobufMessage[] children;
        private final int tag;

        public ProtobufMessage(int tag, ProtobufMessage... children) {
            this.tag = tag;
            if (children == null) {
                children = NativeCrashWriter.EMPTY_CHILDREN;
            }
            this.children = children;
        }

        public int getSize() {
            int size = getSizeNoTag();
            return (size + CodedOutputStream.computeRawVarint32Size(size)) + CodedOutputStream.computeTagSize(this.tag);
        }

        public int getSizeNoTag() {
            int size = getPropertiesSize();
            for (ProtobufMessage child : this.children) {
                size += child.getSize();
            }
            return size;
        }

        public void write(CodedOutputStream cos) throws IOException {
            cos.writeTag(this.tag, 2);
            cos.writeRawVarint32(getSizeNoTag());
            writeProperties(cos);
            for (ProtobufMessage child : this.children) {
                child.write(cos);
            }
        }

        public int getPropertiesSize() {
            return 0;
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
        }
    }

    private static final class ApplicationMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 3;

        public ApplicationMessage(ExecutionMessage executionMessage, RepeatedMessage customAttrs) {
            super(3, executionMessage, customAttrs);
        }
    }

    private static final class BinaryImageMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 4;
        private final long baseAddr;
        private final String filePath;
        private final long imageSize;
        private final String uuid;

        public BinaryImageMessage(BinaryImageData binaryImageData) {
            super(4, new ProtobufMessage[0]);
            this.baseAddr = binaryImageData.baseAddress;
            this.imageSize = binaryImageData.size;
            this.filePath = binaryImageData.path;
            this.uuid = binaryImageData.id;
        }

        public int getPropertiesSize() {
            int addrSize = CodedOutputStream.computeUInt64Size(1, this.baseAddr);
            return ((CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.filePath)) + addrSize) + CodedOutputStream.computeUInt64Size(2, this.imageSize)) + CodedOutputStream.computeBytesSize(4, ByteString.copyFromUtf8(this.uuid));
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeUInt64(1, this.baseAddr);
            cos.writeUInt64(2, this.imageSize);
            cos.writeBytes(3, ByteString.copyFromUtf8(this.filePath));
            cos.writeBytes(4, ByteString.copyFromUtf8(this.uuid));
        }
    }

    private static final class CustomAttributeMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 2;
        private final String key;
        private final String value;

        public CustomAttributeMessage(CustomAttributeData customAttributeData) {
            super(2, new ProtobufMessage[0]);
            this.key = customAttributeData.key;
            this.value = customAttributeData.value;
        }

        public int getPropertiesSize() {
            return CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.key)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.value == null ? "" : this.value));
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeBytes(1, ByteString.copyFromUtf8(this.key));
            cos.writeBytes(2, ByteString.copyFromUtf8(this.value == null ? "" : this.value));
        }
    }

    private static final class DeviceMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 5;
        private final float batteryLevel;
        private final int batteryVelocity;
        private final long diskUsed;
        private final int orientation;
        private final boolean proximityOn;
        private final long ramUsed;

        public DeviceMessage(float batteryLevel, int batteryVelocity, boolean proximityOn, int orientation, long ramUsed, long diskUsed) {
            super(5, new ProtobufMessage[0]);
            this.batteryLevel = batteryLevel;
            this.batteryVelocity = batteryVelocity;
            this.proximityOn = proximityOn;
            this.orientation = orientation;
            this.ramUsed = ramUsed;
            this.diskUsed = diskUsed;
        }

        public int getPropertiesSize() {
            return (((((0 + CodedOutputStream.computeFloatSize(1, this.batteryLevel)) + CodedOutputStream.computeSInt32Size(2, this.batteryVelocity)) + CodedOutputStream.computeBoolSize(3, this.proximityOn)) + CodedOutputStream.computeUInt32Size(4, this.orientation)) + CodedOutputStream.computeUInt64Size(5, this.ramUsed)) + CodedOutputStream.computeUInt64Size(6, this.diskUsed);
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeFloat(1, this.batteryLevel);
            cos.writeSInt32(2, this.batteryVelocity);
            cos.writeBool(3, this.proximityOn);
            cos.writeUInt32(4, this.orientation);
            cos.writeUInt64(5, this.ramUsed);
            cos.writeUInt64(6, this.diskUsed);
        }
    }

    private static final class EventMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 10;
        private final String crashType;
        private final long time;

        public EventMessage(long time, String crashType, ProtobufMessage... eventMessages) {
            super(10, eventMessages);
            this.time = time;
            this.crashType = crashType;
        }

        public int getPropertiesSize() {
            return CodedOutputStream.computeUInt64Size(1, this.time) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.crashType));
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeUInt64(1, this.time);
            cos.writeBytes(2, ByteString.copyFromUtf8(this.crashType));
        }
    }

    private static final class ExecutionMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 1;

        public ExecutionMessage(SignalMessage signalMessage, RepeatedMessage threads, RepeatedMessage binaryImages) {
            super(1, threads, signalMessage, binaryImages);
        }
    }

    private static final class FrameMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 3;
        private final long address;
        private final String file;
        private final int importance;
        private final long offset;
        private final String symbol;

        public FrameMessage(FrameData frameData) {
            super(3, new ProtobufMessage[0]);
            this.address = frameData.address;
            this.symbol = frameData.symbol;
            this.file = frameData.file;
            this.offset = frameData.offset;
            this.importance = frameData.importance;
        }

        public int getPropertiesSize() {
            return (((CodedOutputStream.computeUInt64Size(1, this.address) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.symbol))) + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.file))) + CodedOutputStream.computeUInt64Size(4, this.offset)) + CodedOutputStream.computeUInt32Size(5, this.importance);
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeUInt64(1, this.address);
            cos.writeBytes(2, ByteString.copyFromUtf8(this.symbol));
            cos.writeBytes(3, ByteString.copyFromUtf8(this.file));
            cos.writeUInt64(4, this.offset);
            cos.writeUInt32(5, this.importance);
        }
    }

    private static final class LogMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 6;
        ByteString logBytes;

        public LogMessage(ByteString logBytes) {
            super(6, new ProtobufMessage[0]);
            this.logBytes = logBytes;
        }

        public int getPropertiesSize() {
            return CodedOutputStream.computeBytesSize(1, this.logBytes);
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeBytes(1, this.logBytes);
        }
    }

    private static final class NullMessage extends ProtobufMessage {
        public NullMessage() {
            super(0, new ProtobufMessage[0]);
        }

        public void write(CodedOutputStream cos) throws IOException {
        }

        public int getSize() {
            return 0;
        }
    }

    private static final class RepeatedMessage extends ProtobufMessage {
        private final ProtobufMessage[] messages;

        public RepeatedMessage(ProtobufMessage... messages) {
            super(0, new ProtobufMessage[0]);
            this.messages = messages;
        }

        public void write(CodedOutputStream cos) throws IOException {
            for (ProtobufMessage message : this.messages) {
                message.write(cos);
            }
        }

        public int getSize() {
            int size = 0;
            for (ProtobufMessage message : this.messages) {
                size += message.getSize();
            }
            return size;
        }
    }

    private static final class SignalMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 3;
        private final long sigAddr;
        private final String sigCode;
        private final String sigName;

        public SignalMessage(SignalData signalData) {
            super(3, new ProtobufMessage[0]);
            this.sigName = signalData.name;
            this.sigCode = signalData.code;
            this.sigAddr = signalData.faultAddress;
        }

        public int getPropertiesSize() {
            return (CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.sigName)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.sigCode))) + CodedOutputStream.computeUInt64Size(3, this.sigAddr);
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            cos.writeBytes(1, ByteString.copyFromUtf8(this.sigName));
            cos.writeBytes(2, ByteString.copyFromUtf8(this.sigCode));
            cos.writeUInt64(3, this.sigAddr);
        }
    }

    private static final class ThreadMessage extends ProtobufMessage {
        private static final int PROTOBUF_TAG = 1;
        private final int importance;
        private final String name;

        public ThreadMessage(ThreadData threadData, RepeatedMessage frames) {
            super(1, frames);
            this.name = threadData.name;
            this.importance = threadData.importance;
        }

        public int getPropertiesSize() {
            return CodedOutputStream.computeUInt32Size(2, this.importance) + (hasName() ? CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.name)) : 0);
        }

        public void writeProperties(CodedOutputStream cos) throws IOException {
            if (hasName()) {
                cos.writeBytes(1, ByteString.copyFromUtf8(this.name));
            }
            cos.writeUInt32(2, this.importance);
        }

        private boolean hasName() {
            return this.name != null && this.name.length() > 0;
        }
    }

    NativeCrashWriter() {
    }

    private static EventMessage createEventMessage(SessionEventData crashEvent, LogFileManager logFileManager, Map<String, String> attributes) throws IOException {
        ApplicationMessage applicationMessage = new ApplicationMessage(new ExecutionMessage(new SignalMessage(crashEvent.signal != null ? crashEvent.signal : DEFAULT_SIGNAL), createThreadsMessage(crashEvent.threads), createBinaryImagesMessage(crashEvent.binaryImages)), createCustomAttributesMessage(mergeCustomAttributes(crashEvent.customAttributes, attributes)));
        ProtobufMessage deviceMessage = createDeviceMessage(crashEvent.deviceData);
        ByteString logByteString = logFileManager.getByteStringForLog();
        if (logByteString == null) {
            Fabric.getLogger().mo4381d(CrashlyticsCore.TAG, "No log data to include with this event.");
        }
        logFileManager.clearLog();
        ProtobufMessage logMessage = logByteString != null ? new LogMessage(logByteString) : new NullMessage();
        return new EventMessage(crashEvent.timestamp, NDK_CRASH_TYPE, applicationMessage, deviceMessage, logMessage);
    }

    private static CustomAttributeData[] mergeCustomAttributes(CustomAttributeData[] ndkAttributes, Map<String, String> javaAttributes) {
        Map<String, String> sorted = new TreeMap(javaAttributes);
        if (ndkAttributes != null) {
            for (CustomAttributeData attr : ndkAttributes) {
                sorted.put(attr.key, attr.value);
            }
        }
        Entry[] entryArray = (Entry[]) sorted.entrySet().toArray(new Entry[sorted.size()]);
        CustomAttributeData[] merged = new CustomAttributeData[entryArray.length];
        for (int i = 0; i < merged.length; i++) {
            merged[i] = new CustomAttributeData((String) entryArray[i].getKey(), (String) entryArray[i].getValue());
        }
        return merged;
    }

    private static ProtobufMessage createDeviceMessage(DeviceData deviceData) {
        if (deviceData == null) {
            return new NullMessage();
        }
        return new DeviceMessage(((float) deviceData.batteryCapacity) / 100.0f, deviceData.batteryVelocity, deviceData.proximity, deviceData.orientation, deviceData.totalPhysicalMemory - deviceData.availablePhysicalMemory, deviceData.totalInternalStorage - deviceData.availableInternalStorage);
    }

    private static RepeatedMessage createThreadsMessage(ThreadData[] threads) {
        ThreadMessage[] threadMessages = threads != null ? new ThreadMessage[threads.length] : EMPTY_THREAD_MESSAGES;
        for (int threadIdx = 0; threadIdx < threadMessages.length; threadIdx++) {
            ThreadData threadData = threads[threadIdx];
            threadMessages[threadIdx] = new ThreadMessage(threadData, createFramesMessage(threadData.frames));
        }
        return new RepeatedMessage(threadMessages);
    }

    private static RepeatedMessage createFramesMessage(FrameData[] frames) {
        FrameMessage[] frameMessages = frames != null ? new FrameMessage[frames.length] : EMPTY_FRAME_MESSAGES;
        for (int frameIdx = 0; frameIdx < frameMessages.length; frameIdx++) {
            frameMessages[frameIdx] = new FrameMessage(frames[frameIdx]);
        }
        return new RepeatedMessage(frameMessages);
    }

    private static RepeatedMessage createBinaryImagesMessage(BinaryImageData[] binaryImages) {
        BinaryImageMessage[] binaryImageMessages = binaryImages != null ? new BinaryImageMessage[binaryImages.length] : EMPTY_BINARY_IMAGE_MESSAGES;
        for (int i = 0; i < binaryImageMessages.length; i++) {
            binaryImageMessages[i] = new BinaryImageMessage(binaryImages[i]);
        }
        return new RepeatedMessage(binaryImageMessages);
    }

    private static RepeatedMessage createCustomAttributesMessage(CustomAttributeData[] customAttributes) {
        CustomAttributeMessage[] customAttributeMessages = customAttributes != null ? new CustomAttributeMessage[customAttributes.length] : EMPTY_CUSTOM_ATTRIBUTE_MESSAGES;
        for (int i = 0; i < customAttributeMessages.length; i++) {
            customAttributeMessages[i] = new CustomAttributeMessage(customAttributes[i]);
        }
        return new RepeatedMessage(customAttributeMessages);
    }

    public static void writeNativeCrash(SessionEventData crashEventData, LogFileManager logFileManager, Map<String, String> customAttributes, CodedOutputStream cos) throws IOException {
        createEventMessage(crashEventData, logFileManager, customAttributes).write(cos);
    }
}
