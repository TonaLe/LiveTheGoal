package app.security.DAO;

import app.security.Entity.Account;
import app.security.DTO.ApplicationUser;
import app.security.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import static app.security.Enum.UserRole.ADMIN;
import static app.security.Enum.UserRole.USER;


@Repository
public class AccountDaoImp implements AccountDAO {
    private AccountRepository accountRepository;

    @Autowired
    public AccountDaoImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void setAccount(Account account){
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserbyName(String username) {
        Account account1 = accountRepository.findAccountByUsername(username).get(0);
        if(!username.equals("NONE_PROVIDED")){
            Account account = accountRepository.findAccountByUsername(username).get(0);
            ApplicationUser applicationUser = new ApplicationUser(
                    account.getRole().equals("Admin")?ADMIN.getAuthority():USER.getAuthority(),
                    account.getUsername(),
                    account.getPassword(),
                    true,
                    true,
                    true,
                    true
            );
            return applicationUser;
        }
        return null;
    }

    @Override
    public Account getUserByUsername(String username) {
        return accountRepository.findAccountByUsername(username).get(0);
    }

}
