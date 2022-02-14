package module.account.Service;

import module.account.DAO.AccountDAO;
import module.account.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
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

}
