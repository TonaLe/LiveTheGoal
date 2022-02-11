package app.security.Service;


import app.security.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    void setAccount(Account account);

    Account getAccountByUsername(String username);
}
