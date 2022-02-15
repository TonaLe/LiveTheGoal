package module.account.DAO;


import module.account.Entity.Account;

public interface AccountDAO {

    void setAccount(final Account account);

    Account getUserByUsername(final String username);

    Account getUserByEmail(final String email);
}
