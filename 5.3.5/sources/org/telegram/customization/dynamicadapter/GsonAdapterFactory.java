package org.telegram.customization.dynamicadapter;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.telegram.customization.dynamicadapter.data.ObjBase;

public class GsonAdapterFactory implements TypeAdapterFactory {

    private static class ObjBaseTypeAdapter extends TypeAdapter<ObjBase> {
        private final Gson gson;
        private final GsonAdapterFactory gsonAdapterFactory;
        private final TypeAdapter<JsonElement> jsonElementAdapter;

        ObjBaseTypeAdapter(GsonAdapterFactory gsonAdapterFactory, Gson gson, TypeAdapter<JsonElement> jsonElementAdapter) {
            this.jsonElementAdapter = jsonElementAdapter;
            this.gson = gson;
            this.gsonAdapterFactory = gsonAdapterFactory;
        }

        public void write(JsonWriter out, ObjBase value) throws IOException {
        }

        public ObjBase read(JsonReader in) throws IOException {
            JsonObject objectJson = ((JsonElement) this.jsonElementAdapter.read(in)).getAsJsonObject();
            try {
                return (ObjBase) this.gson.getDelegateAdapter(this.gsonAdapterFactory, TypeToken.get(Helper.getModel(objectJson.get(Param.TYPE).getAsInt()))).fromJsonTree(objectJson);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (ObjBase.class.isAssignableFrom(type.getRawType())) {
            return new ObjBaseTypeAdapter(this, gson, gson.getAdapter(JsonElement.class)).nullSafe();
        }
        return null;
    }
}
