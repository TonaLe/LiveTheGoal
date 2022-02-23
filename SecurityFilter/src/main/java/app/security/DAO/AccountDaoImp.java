package app.security.DAO;

import app.security.DTO.ApplicationUser;
import app.security.Entity.Account;
import app.security.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

import static app.security.Enum.UserAuthorities.ADMIN;
import static app.security.Enum.UserAuthorities.USER;


/**
 * The type Account dao imp.
 */
@Repository
public class AccountDaoImp implements AccountDAO {

    /**
     * The Account non expired.
     */
    private final boolean ACCOUNT_NON_EXPIRED = true;
    /**
     * The Account non locked.
     */
    private final boolean ACCOUNT_NON_LOCKED = true;
    /**
     * The Credentials non expired.
     */
    private final boolean CREDENTIALS_NON_EXPIRED = true;
    /**
     * The Not expired.
     */
    private final boolean NOT_EXPIRED = true;

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

    public void setAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserbyName(String username) {
        if (!username.equals("NONE_PROVIDED")) {
            Account account = accountRepository.findAccountByUsername(username);
            return new ApplicationUser(
                    account.getRole().equals("Admin") ? ADMIN.getAuthority() : USER.getAuthority(),
                    account.getUsername(),
                    account.getPassword(),
                    ACCOUNT_NON_EXPIRED,
                    ACCOUNT_NON_LOCKED,
                    CREDENTIALS_NON_EXPIRED,
                    NOT_EXPIRED
            );
        }
        return null;
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
        return accountRepository.findAll(pageable).getContent();
    }
}
