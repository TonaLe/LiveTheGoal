package app.security.Service;

import app.security.DTO.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ErrorService {

    private final ConcurrentHashMap<String, ErrorDto> listError = new ConcurrentHashMap<>();

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public void initErrorList(final ErrorDto errorDto, final String username) {
        if (listError.get(username) != null) {
            LOG.warn(String.format("Error username: %s already existed in Map\n", username));
            return;
        }
        listError.put(username, errorDto);
    }

    public ErrorDto getError(final String username) {
        if (listError.isEmpty()) {
            return null;
        }
        return listError.get(username);
    }
}
