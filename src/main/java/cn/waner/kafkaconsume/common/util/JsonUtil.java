package cn.waner.kafkaconsume.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
public final class JsonUtil {

    /**
     * 日志管理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();



    /**
     * 将Map转换成Json字符串。
     */
    public static String convertMapToJson(Map<?, ?> map) {
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Json字符串转化为Map。
     */
    public static Map<String, Object> convertJsonToMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            LOGGER.error("解析Json失败。", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 将字符串列表转换成Json。
     *
     * 如，将一个List<String>对象转换成Json字符串“["e1", "e2", "e3", ...]”。
     */
    public static String convertStringArrayToJson(List<String> stringList) {
        StringBuilder json = new StringBuilder("[");
        int jsonLength = json.length();

        stringList.forEach(string -> {
            json.append("\"");
            json.append(string);
            json.append("\",");
        });

        String result = json.length() == jsonLength ? json.toString()
                : json.substring(0, json.length() - 1);
        return result + "]";
    }

    /**
     * 在指定的Json字符串里添加新的字段。
     */
    public static String addFieldToJson(String json, String key, String value) {
        json = json.substring(0, json.length() - 1);

        if (json.length() == 1) {
            json += " \"" + key + "\" : \"" + value + "\"}";
        } else {
            json += ", \"" + key + "\" : \"" + value + "\"}";
        }

        return json;
    }
}
