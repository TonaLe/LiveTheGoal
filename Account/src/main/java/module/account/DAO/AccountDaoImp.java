package module.account.DAO;


import module.account.Entity.Account;
import module.account.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Account dao imp.
 */
@Repository
public class AccountDaoImp implements AccountDAO {

    /**
     * The Account repository.
     */
    @Autowired
    private final AccountRepository accountRepository;

    /**
     * Instantiates a new Account dao imp.
     *
     * @param accountRepository the account repository
     */
    @Autowired
    public AccountDaoImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void setAccount(final Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getUserByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public Account getUserByEmail(final String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public List<Account> findAllAccount(final Pageable pageable) {
        return accountRepository.findAllAccount(pageable);
    }
}
