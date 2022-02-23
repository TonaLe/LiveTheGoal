package app.security.Service;

import app.security.DAO.AccountDAO;
import app.security.DTO.AccountDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.Entity.Account;
import app.security.Enum.Role;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


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
     * The Model mapper.
     */
    private final ModelMapper modelMapper;

    /**
     * The B crypt password encoder.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The Consumer key.
     */
    private String consumerKey;

    /**
     * The Account dao.
     */
    private final AccountDAO accountDAO;

    /**
     * Instantiates a new Account service.
     *
     * @param accountDAO the account dao
     */
    @Autowired
    public AccountServiceImpl(final AccountDAO accountDAO) {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        this.accountDAO = accountDAO;
    }

    @Override
    public void setAccount(AccountDto account) {
        Role roleDomain = Role.valueOf(StringUtils.upperCase(account.getRole()));
        Account accountDomain = modelMapper.map(account, Account.class);
        accountDomain.setRole(roleDomain);
        accountDomain.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountDAO.setAccount(accountDomain);    }

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
    public List<AccountDto> getListAccount(final int limit,
                                           final int offset) {
        Pageable pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        List<Account> listAccount = accountDAO.findAllAccount(pageable);

        return listAccount.stream()
                .filter(Objects::nonNull)
                .filter(account -> !account.getIsDeleted())
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateAccountInfo(final String username, final AccountDto accountDto) {
        LOG.info(String.format("Update information for account: %s", username));

        Account account = accountDAO.getUserByUsername(username);
        Role roleDomain = Role.valueOf(StringUtils.upperCase(accountDto.getRole()));
        Account accountDomain = modelMapper.map(accountDto, Account.class);
        accountDomain.setRole(roleDomain);
        accountDomain.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));

        Account updatedAccount = transferAccountInfo(account, accountDomain);
        accountDAO.setAccount(updatedAccount);
    }

    /**
     * Transfer account info account.
     *
     * @param account       the account
     * @param accountDomain the account domain
     * @return the account
     */
    private Account transferAccountInfo(final Account account, final Account accountDomain) {
        account.setUsername(accountDomain.getUsername());
        account.setPassword(accountDomain.getPassword());
        account.setFirstName(accountDomain.getFirstName());
        account.setLastName(accountDomain.getLastName());
        account.setMsisdn(accountDomain.getMsisdn());
        account.setRole(accountDomain.getRole());
        return account;
    }

    @Override
    public void deleteAccount(final String username) {
        Account account = accountDAO.getUserByUsername(username);
        account.setIsDeleted(true);
    }
}
