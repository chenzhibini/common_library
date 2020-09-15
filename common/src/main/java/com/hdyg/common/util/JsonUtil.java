package com.hdyg.common.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson解析Json工具类
 * Created by Administrator on 2017/5/4.
 */

public class JsonUtil {


    /**
     * 创建 gson
     *
     * @return
     */
    public static Gson createGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .create();
        return gson;
    }


    /**
     * 解析 Json 字符串为对象
     *
     * @param jsonData json数据
     * @param type     实体类
     * @param <T>      实体类
     * @return result 对象
     */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = null;
//        Gson gson = new Gson();
        Gson gson = createGson();
        result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     * list转字符串
     *
     * @return
     */
    public static String parseListToJson(List list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    /**
     * 对象转字符串
     * @param object
     * @return
     */
    public static String parseObjectToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
    /**
     * 解析 Json 字符串为数组
     *
     * @param json  json 数据
     * @param clazz 实体
     * @return 数组
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 字符串json转数组
     * @param json json串
     * @return jsonList
     */
    public static List<String> jsonToArrayList(String json){
        List<String> jsonList = new Gson().fromJson(json, new TypeToken<List<String>>() {}.getType());
        return jsonList;
    }

    /**
     * 得到json文件中的内容
     * @param context 上下文
     * @param fileName 文件名
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            LogUtils.e("异常===>"+e.getMessage());
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /** ####################################### Json 异常处理 ######################################## */
    /**
     * 针对 int 类型的值的转换器
     */
    static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

        @Override
        public Integer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                if (jsonElement.getAsString().equals("") || jsonElement.getAsString().equals("null")) {
                    return 0;
                }
            } catch (Exception e) {

            }
            try {
                return jsonElement.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer integer, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(integer);
        }
    }

    /**
     * 针对 long 类型的值的转换器
     */
    static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {

        @Override
        public Long deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                if (jsonElement.getAsString().equals("") || jsonElement.getAsString().equals("null")) {
                    return 0l;
                }
            } catch (Exception e) {

            }
            try {
                return jsonElement.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long mLong, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(mLong);
        }
    }

    /**
     * 针对 double 类型的值的转换器
     */
    static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {

        @Override
        public Double deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                if (jsonElement.getAsString().equals("") || jsonElement.getAsString().equals("null")) {
                    return 0.00;
                }
            } catch (Exception e) {

            }
            try {
                return jsonElement.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double mDouble, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(mDouble);
        }
    }

    /**
     * 针对 String 类型的值的转换器
     */
    class StringDefault0Adapter implements JsonSerializer<String>, JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                if (jsonElement.getAsString().equals("null")) {
                    return "";
                }
            } catch (Exception e) {

            }
            try {
                return jsonElement.getAsString();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(String str, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(str);
        }
    }


}
