package app.security.Service;

import app.security.DAO.AccountDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The type Account service.
 */
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    /**
     * The List account event.
     */
    private final ConcurrentHashMap<String, List<AccountDto>> listAccountEvent = new ConcurrentHashMap<>();

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * The Consumer key.
     */
    private String consumerKey;

    /**
     * The Account dao.
     */
    @Autowired
    private AccountDAO accountDAO;

    @Override
    public void setAccount(Account account) {
        accountDAO.setAccount(account);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountDAO.loadUserbyName(s);
    }

    @Override
    public AccountDto loadAccountByUsername(final String username) {
//        if ()
        return null;
    }

    @Override
    public void initAccountList(final List<AccountDto> listAccount, final String key) {
        LOG.info(String.format("Init list account with key: %s to map", key));

        consumerKey = key;
        if (!listAccount.isEmpty() || listAccountEvent.get(key) != null) {
            return;
        }
        listAccountEvent.put(key, listAccount);
    }

    @Override
    public List<AccountDto> getListAccount() {
        return listAccountEvent.get(consumerKey);
    }
}
