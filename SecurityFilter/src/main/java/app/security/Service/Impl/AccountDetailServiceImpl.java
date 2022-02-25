package app.security.Service.Impl;

import app.security.DAO.AccountDAO;
import app.security.DAO.AccountDetailDAO;
import app.security.DTO.AccountDto;
import app.security.Entity.Account;
import app.security.Entity.AccountDetail;
import app.security.Enum.Role;
import app.security.Service.AccountDetailService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailServiceImpl implements AccountDetailService {

    private final AccountDetailDAO accountDetailDAO;

    private final ModelMapper modelMapper;

    private final AccountDAO accountDAO;

    @Autowired
    public AccountDetailServiceImpl(final AccountDetailDAO accountDetailDAO,
                                    final AccountDAO accountDAO) {
        this.accountDetailDAO = accountDetailDAO;
        this.accountDAO = accountDAO;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public void setAccountDetail(final AccountDto account) {
    }
}
