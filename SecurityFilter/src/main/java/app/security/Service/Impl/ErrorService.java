package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

import static app.security.Bean.ErrorConstant.*;

@Service
public class ErrorService {

    private final AccountDAO accountDAO;

    private final ConcurrentHashMap<String, ErrorDto> listError = new ConcurrentHashMap<>();

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ErrorService(final AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void initErrorList(final ErrorDto errorDto, final String username) {
        if (listError.get(username) != null) {
            LOG.warn(String.format("Error username: %s already existed in Map\n", username));
            return;
        }
        listError.put(username, errorDto);
    }

    public ErrorDto getError(final AccountDto accountDto) {
        if (accountDAO.getUserByUsername(accountDto.getUsername()) != null) {
            return new ErrorDto(ERROR_ID_4, ERROR_MSG_4);
        } else if (accountDAO.getUserByEmail(accountDto.getEmail()) != null) {
            return new ErrorDto(ERROR_ID_5, ERROR_MSG_5);
        }
        return null;
    }
}
