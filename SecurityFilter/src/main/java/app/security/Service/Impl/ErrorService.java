package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.ProductDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.DTO.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

import static app.security.Bean.ErrorConstant.*;

@Service
public class ErrorService {

    private final AccountDAO accountDAO;
    private final ProductDAO productDAO;


    private final ConcurrentHashMap<String, ErrorDto> listError = new ConcurrentHashMap<>();

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ErrorService(final AccountDAO accountDAO, ProductDAO productDAO) {
        this.accountDAO = accountDAO;
        this.productDAO = productDAO;
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

    public ErrorDto getSkuError(final ProductDto productDto) {
        if (productDAO.loadProductBySku(productDto.getSku()) != null) {
            return new ErrorDto(ERROR_ID_6, ERROR_MSG_6);
        }
        return null;
    }
}
