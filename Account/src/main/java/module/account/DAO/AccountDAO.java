package module.account.DAO;


import module.account.Entity.Account;

public interface AccountDAO {

    void setAccount(Account account);

    Account getUserByUsername(String username);
}
