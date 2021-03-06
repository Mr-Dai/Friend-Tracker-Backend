package com.wetrack.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String jsonStr = json.getAsString();
        if (jsonStr == null || jsonStr.trim().isEmpty())
            return null;
        try {
            return LocalDate.parse(jsonStr);
        } catch (IllegalArgumentException ex) {
            throw new JsonParseException("Received illegal date field: `" + jsonStr + "`", ex);
        }
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
