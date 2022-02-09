package app.security.DAO;

import app.security.Entity.Account;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountDAO {

    void setAccount(Account account);

    UserDetails loadUserbyName(String user);

    Account getUserByUsername(String username);
}
