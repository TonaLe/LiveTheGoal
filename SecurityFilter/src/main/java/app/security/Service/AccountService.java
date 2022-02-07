package app.security.Service;


import app.security.DTO.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    void setAccount(Account account);

    Account getAccountByUsername(String username);
}
