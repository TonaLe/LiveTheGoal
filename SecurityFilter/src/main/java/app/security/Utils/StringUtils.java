package app.security.Utils;

import com.google.gson.Gson;

public class StringUtils {

    private static Gson gson = new Gson();

    public static String convertObjectToString(Object object) {
        return gson.toJson(object);
    }
}
