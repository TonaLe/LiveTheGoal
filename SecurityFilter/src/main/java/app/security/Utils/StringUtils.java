package app.security.Utils;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class StringUtils {

    private static Gson gson = new Gson();

    public static String convertObjectToString(final Object object) {
        return gson.toJson(object);
    }

    public static ErrorDto convertJsonToErrorDto(final String json) {
        return gson.fromJson(json, ErrorDto.class);
    }

    public static List<AccountDto> convertJsonToListAccount(final String json) {
        return gson.fromJson(json, new TypeToken<List<AccountDto>>(){}.getType());
    }
}
