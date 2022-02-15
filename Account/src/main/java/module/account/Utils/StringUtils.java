package module.account.Utils;

import com.google.gson.Gson;
import module.account.DTO.AccountDto;

public class StringUtils {

    private static Gson gson = new Gson();

    public static String convertObjectToString(Object object) {
        return gson.toJson(object);
    }

    public static AccountDto convertJsonToAccount(String json) {
        return gson.fromJson(json, AccountDto.class);
    }
}
