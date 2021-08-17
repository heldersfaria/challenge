package br.com.dextra.potter.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Optional;

import static com.google.gson.JsonParser.parseString;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class GsonUtils {

    private GsonUtils() {

    }

    public static JsonArray getAsJsonArray(String jsonString, String... campos) {
        JsonArray jsonArray = new JsonArray();

        try {

            if (isBlank(jsonString)) {
                return jsonArray;
            }

            if (campos == null || campos.length == 0) {
                jsonArray = parseString(jsonString).getAsJsonArray();
            } else {

                JsonObject jsonObject = parseString(jsonString).getAsJsonObject();

                for (String campo : campos) {

                    JsonElement jsonElement = jsonObject.get(campo);

                    if (jsonElement != null) {
                        if (jsonElement.isJsonObject()) {
                            jsonObject = jsonObject.getAsJsonObject(campo);

                        } else if (jsonElement.isJsonArray()) {
                            jsonArray = jsonElement.getAsJsonArray();
                            break;
                        }
                    }
                }
            }

        } catch (Exception ignored) {
        }
        return jsonArray;
    }

    public static JsonObject getAsJsonObject(String jsonString, String... campos) {
        JsonObject retorno = new JsonObject();

        try {
            if (isNotBlank(jsonString)) {
                JsonObject jsonObject = parseString(jsonString).getAsJsonObject();
                retorno = buscarJsonObject(jsonObject, campos).orElseGet(JsonObject::new);
            }
        } catch (Exception ignored) {
        }

        return retorno;
    }

    private static Optional<JsonObject> buscarJsonObject(JsonObject jsonObject, String... campos) {

        JsonObject jsonObjectAtual = jsonObject;

        for (String campo : campos) {

            JsonElement jsonElement = jsonObjectAtual.get(campo);

            if (jsonElement != null) {
                if (jsonElement.isJsonObject()) {
                    jsonObjectAtual = jsonObjectAtual.getAsJsonObject(campo);
                }
            } else {
                return empty();
            }
        }

        return Optional.ofNullable(jsonObjectAtual);
    }
}
