package org.telegram.customization.dynamicadapter.data;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class DocAvailableInfo$DocAvailableInfoAdapterFactory implements TypeAdapterFactory {

    /* renamed from: org.telegram.customization.dynamicadapter.data.DocAvailableInfo$DocAvailableInfoAdapterFactory$DocAvailableInfoTypeAdapter */
    private static class DocAvailableInfoTypeAdapter extends TypeAdapter<DocAvailableInfo> {
        private final Gson gson;
        private final DocAvailableInfo$DocAvailableInfoAdapterFactory gsonAdapterFactory;
        private final TypeAdapter<JsonElement> jsonElementAdapter;

        DocAvailableInfoTypeAdapter(DocAvailableInfo$DocAvailableInfoAdapterFactory gsonAdapterFactory, Gson gson, TypeAdapter<JsonElement> jsonElementAdapter) {
            this.jsonElementAdapter = jsonElementAdapter;
            this.gson = gson;
            this.gsonAdapterFactory = gsonAdapterFactory;
        }

        public void write(JsonWriter out, DocAvailableInfo value) throws IOException {
            out.beginObject();
            if (value.docId != 0) {
                out.name("d").value(value.docId);
            }
            if (value.localId != 0) {
                out.name("l").value((long) value.localId);
            }
            if (value.volumeId != 0) {
                out.name("v").value(value.volumeId);
            }
            if (value.messageId != 0) {
                out.name("m").value(value.messageId);
            }
            if (value.size != 0) {
                out.name("s").value((long) value.size);
            }
            if (value.channelId != 0) {
                out.name("cid").value(value.channelId);
            }
            if (!TextUtils.isEmpty(value.channelUsername)) {
                out.name("cun").value(value.channelUsername);
            }
            out.endObject();
        }

        public DocAvailableInfo read(JsonReader in) throws IOException {
            return null;
        }
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (DocAvailableInfo.class.isAssignableFrom(type.getRawType())) {
            return new DocAvailableInfoTypeAdapter(this, gson, gson.getAdapter(JsonElement.class)).nullSafe();
        }
        return null;
    }
}
