package org.telegram.customization.dynamicadapter.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class DocAvailableInfo$NewDocAvailableInfoAdapterFactory implements TypeAdapterFactory {

    /* renamed from: org.telegram.customization.dynamicadapter.data.DocAvailableInfo$NewDocAvailableInfoAdapterFactory$NewDocAvailableInfoTypeAdapter */
    private static class NewDocAvailableInfoTypeAdapter extends TypeAdapter<DocAvailableInfo> {
        private final Gson gson;
        private final DocAvailableInfo$NewDocAvailableInfoAdapterFactory gsonAdapterFactory;
        private final TypeAdapter<JsonElement> jsonElementAdapter;

        NewDocAvailableInfoTypeAdapter(DocAvailableInfo$NewDocAvailableInfoAdapterFactory gsonAdapterFactory, Gson gson, TypeAdapter<JsonElement> jsonElementAdapter) {
            this.jsonElementAdapter = jsonElementAdapter;
            this.gson = gson;
            this.gsonAdapterFactory = gsonAdapterFactory;
        }

        public void write(JsonWriter out, DocAvailableInfo value) throws IOException {
            out.beginObject();
            if (value.docId != 0) {
                out.name("fk").value(value.docId);
            }
            if (!(value.localId == 0 || value.volumeId == 0)) {
                out.name("fk").value(String.valueOf(value.localId) + "." + String.valueOf(value.volumeId));
            }
            if (!(value.messageId == 0 || value.channelId == 0)) {
                out.name("mk").value(String.valueOf(value.messageId) + "." + String.valueOf(value.channelId));
            }
            if (value.size != 0) {
                out.name("s").value((long) value.size);
            }
            out.endObject();
        }

        public DocAvailableInfo read(JsonReader in) throws IOException {
            return null;
        }
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (DocAvailableInfo.class.isAssignableFrom(type.getRawType())) {
            return new NewDocAvailableInfoTypeAdapter(this, gson, gson.getAdapter(JsonElement.class)).nullSafe();
        }
        return null;
    }
}
