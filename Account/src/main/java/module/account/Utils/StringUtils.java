package module.account.Utils;

import com.google.gson.Gson;

public class StringUtils {

    private static Gson gson = new Gson();

    public static String convertObjectToString(Object object) {
        return gson.toJson(object);
    }

    public static Object convertJsonToObject(String json) {
        return gson.fromJson(json, Object.class);
    }
}
