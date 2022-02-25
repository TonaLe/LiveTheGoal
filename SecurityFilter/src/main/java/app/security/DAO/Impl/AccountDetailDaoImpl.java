package app.security.DAO.Impl;

import app.security.DAO.AccountDetailDAO;
import app.security.Entity.AccountDetail;
import app.security.Repository.AccountDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Account detail dao.
 */
@Repository
public class AccountDetailDaoImpl implements AccountDetailDAO {

    /**
     * The Account detail repository.
     */
    private final AccountDetailRepository accountDetailRepository;

    /**
     * Instantiates a new Account detail dao.
     *
     * @param accountDetailRepository the account detail repository
     */
    @Autowired
    public AccountDetailDaoImpl(final AccountDetailRepository accountDetailRepository) {
        this.accountDetailRepository = accountDetailRepository;
    }

    @Override
    public void setAccountDetail(final AccountDetail accountDetail) {
        accountDetailRepository.save(accountDetail);
    }

    @Override
    public AccountDetail findAccountDetailByAccountId(final int accountId) {
        return accountDetailRepository.findAccountDetailByAccountId(accountId);
    }
}
