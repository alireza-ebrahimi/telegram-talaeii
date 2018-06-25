package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_channels_channelParticipant extends TLObject {
    public static int constructor = -791039645;
    public TLRPC$ChannelParticipant participant;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_channels_channelParticipant TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channels_channelParticipant result = new TLRPC$TL_channels_channelParticipant();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channels_channelParticipant", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.participant = TLRPC$ChannelParticipant.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                User object = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.users.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.participant.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
