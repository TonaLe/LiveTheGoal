package app.security.Service;

import app.security.DAO.AccountDAO;
import app.security.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
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
}
