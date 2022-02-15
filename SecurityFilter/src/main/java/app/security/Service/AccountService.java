package app.security.Service;


import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    void setAccount(final Account account);

    Account getAccountByUsername(final String username);

    ErrorDto validateUserAccount(final AccountDto accountDto);
}
