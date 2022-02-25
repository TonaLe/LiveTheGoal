package app.security.DAO;

import app.security.Entity.AccountDetail;

/**
 * The interface Account detail dao.
 */
public interface AccountDetailDAO {

    /**
     * Sets account.
     *
     * @param accountDetail the account detail
     */
    void setAccountDetail(final AccountDetail accountDetail);

    /**
     * Find account detail by account id list.
     *
     * @param accountId the account id
     * @return the list
     */
    AccountDetail findAccountDetailByAccountId(final int accountId);
}
