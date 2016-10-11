package com.wetrack.client.json;

import com.google.gson.*;
import org.joda.time.LocalDateTime;

import java.lang.reflect.Type;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String jsonStr = json.getAsString();
        if (jsonStr == null || jsonStr.trim().isEmpty())
            return null;
        try {
            return LocalDateTime.parse(jsonStr);
        } catch (Throwable ex) {
            throw new JsonParseException("Received illegal date time field: `" + jsonStr + "`", ex);
        }
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
