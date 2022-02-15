package module.account.Service;

import module.account.Bean.ErrorConstant;
import module.account.DAO.AccountDAO;
import module.account.DTO.AccountDto;
import module.account.DTO.ErrorDto;
import module.account.Entity.Account;
import module.account.Enum.Role;
import module.account.Event.AccountProducer.AccountProducer;
import module.account.Event.ErrorProducer.ErrorProducer;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    private final ErrorProducer errorProducer;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    private final ModelMapper modelMapper;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final AccountProducer accountProducer;

    @Autowired
    public AccountServiceImpl(final AccountDAO accountDAO,
                              final ErrorProducer errorProducer,
                              final AccountProducer accountProducer) {
        this.accountDAO = accountDAO;
        this.errorProducer = errorProducer;
        this.accountProducer = accountProducer;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void setAccount(AccountDto account) {
        LOG.info("Saving account: " + account.getUsername());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Role roleDomain = Role.valueOf(account.getRole());
        Account accountDomain = modelMapper.map(account, Account.class);
        accountDomain.setRole(roleDomain);
        accountDomain.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountDAO.setAccount(accountDomain);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountDAO.getUserByUsername(username);
    }

    @Override
    public ErrorDto validateAccountDto(final AccountDto accountDto) {
        if (accountDto == null) {
            return ErrorDto.builder()
                    .id(ErrorConstant.ERROR_ID_1)
                    .message(ErrorConstant.ERROR_MESSAGE_1).build();
        } else if (StringUtils.isBlank(accountDto.getUsername())) {
            return ErrorDto.builder()
                    .id(ErrorConstant.ERROR_ID_2)
                    .message(ErrorConstant.ERROR_MESSAGE_2).build();
        } else if (StringUtils.isBlank(accountDto.getPassword())) {
            return ErrorDto.builder()
                    .id(ErrorConstant.ERROR_ID_3)
                    .message(ErrorConstant.ERROR_MESSAGE_3).build();
        } else if (accountDAO.getUserByUsername(accountDto.getUsername()) != null) {
            return ErrorDto.builder()
                    .id(ErrorConstant.ERROR_ID_4)
                    .message(ErrorConstant.ERROR_MSG_4).build();
        } else if (accountDAO.getUserByEmail(accountDto.getEmail()) != null) {
            return ErrorDto.builder()
                    .id(ErrorConstant.ERROR_ID_5)
                    .message(ErrorConstant.ERROR_MSG_5).build();
        }
        return null;
    }

    @Override
    public void sendErrorMsg(final ErrorDto errorDto) {
        errorProducer.sendMessage(errorDto);
    }
}
