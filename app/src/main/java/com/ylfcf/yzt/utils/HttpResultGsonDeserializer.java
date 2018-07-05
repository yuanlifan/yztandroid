package com.ylfcf.yzt.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ylfcf.yzt.http.base.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by yjx on 2018/7/4.
 */
public class HttpResultGsonDeserializer implements JsonDeserializer<BaseModel<?>> {

    @Override
    public BaseModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonData = jsonObject.has("msg") ? jsonObject.get("msg") : null;

        BaseModel httpResult = new BaseModel();
        httpResult.setTime(jsonObject.has("time") ? jsonObject.get("time").getAsString() : "");
        httpResult.setError_id(jsonObject.has("error_id") ? jsonObject.get("error_id").getAsInt() : 0);
        httpResult.setError(jsonObject.has("error") ? jsonObject.get("error").getAsString() : "");

        //处理Data空串情况
        if(jsonData != null && jsonData.isJsonObject()) {
            //指定泛型具体类型
            if (type instanceof ParameterizedType) {
                Type itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
                Object item = jsonDeserializationContext.deserialize(jsonData, itemType);
                httpResult.setMsg(item);
            }else{
                //未指定泛型具体类型
                httpResult.setMsg(jsonData);
            }
        }else {
            httpResult.setMsg(null);
        }

        return httpResult;
    }
}
