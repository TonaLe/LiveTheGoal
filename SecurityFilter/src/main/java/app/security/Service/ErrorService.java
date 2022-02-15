package app.security.Service;

import app.security.DTO.ErrorDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ErrorService {

    private final ConcurrentHashMap<Integer, ErrorDto> listError = new ConcurrentHashMap<>();

    public void initErrorList(final ErrorDto errorDto, final int id) {
        listError.put(id, errorDto);
    }

    public ErrorDto getError(final Integer id) {
        if (listError.isEmpty()) {
            return null;
        }
        return listError.get(id);
    }
}
