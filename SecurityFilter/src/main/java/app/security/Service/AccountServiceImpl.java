package app.security.Service;

import app.security.DAO.AccountDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;


@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final ConcurrentHashMap<String, AccountDto> listAccountEvent = new ConcurrentHashMap<>();

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public void setAccount(Account account) {
        accountDAO.setAccount(account);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountDAO.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountDAO.loadUserbyName(s);
    }

    @Override
    public AccountDto loadAccountByUsername(final String username) {
        if ()
        return null;
    }
}
