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

    public void initErrorList(final ErrorDto errorDto, final String id) {
        if (listError.get(id) != null) {
            LOG.warn(String.format("Error id: %s already existed \n", id));
            return;
        }
        listError.put(id, errorDto);
    }

    public ErrorDto getError(final String id) {
        if (listError.isEmpty()) {
            return null;
        }
        return listError.get(id);
    }
}
