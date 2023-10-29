package com.business.brendaapp.utils;

import java.io.StringReader;

import com.business.brendaapp.payloads.in.CreateProductPayload;
import com.business.brendaapp.payloads.in.UpdateProductPayload;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

public class Serializer {
    public static CreateProductPayload serializeProduct(String value)
    {
        JsonObject  jsonObject = Streams.parse(new JsonReader(new StringReader(value))).getAsJsonObject();
        String valueCategory =  jsonObject.get("category").getAsString();
        CreateProductPayload payload = CreateProductPayload.builder()
        .category(valueCategory)
        .description(jsonObject.get("description").getAsString())
        .price(jsonObject.get("price").getAsDouble())
        .title(jsonObject.get("title").getAsString())
        .build();
        return payload;
    }

    public static UpdateProductPayload serializeProductPayload(String value)
    {
        JsonObject  jsonObject = Streams.parse(new JsonReader(new StringReader(value))).getAsJsonObject();
        UpdateProductPayload payload = UpdateProductPayload.builder()
        .idProduit(jsonObject.get("idProduit").getAsString())
        .category(jsonObject.get("category").getAsString())
        .description(jsonObject.get("description").getAsString())
        .price(jsonObject.get("price").getAsDouble())
        .title(jsonObject.get("title").getAsString())
        .build();
        return payload;
    }
}