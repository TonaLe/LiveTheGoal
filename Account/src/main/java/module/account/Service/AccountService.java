package module.account.Service;

import module.account.Entity.Account;

public interface AccountService  {

    void setAccount(Account account);

    Account getAccountByUsername(String username);
}
