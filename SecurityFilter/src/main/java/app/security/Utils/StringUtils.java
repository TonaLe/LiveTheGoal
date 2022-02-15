package app.security.Utils;

import app.security.DTO.ErrorDto;
import com.google.gson.Gson;

public class StringUtils {

    private static Gson gson = new Gson();

    public static String convertObjectToString(Object object) {
        return gson.toJson(object);
    }

    public static ErrorDto convertJsonToErrorDto(String json) {
        return gson.fromJson(json, ErrorDto.class);
    }
}
