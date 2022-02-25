package app.security.Repository;

import app.security.Entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Account detail repository.
 */
@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Integer> {

    /**
     * Find account detail by account id account detail.
     *
     * @param accountId the account id
     * @return the account detail
     */
    AccountDetail findAccountDetailByAccountId(final int accountId);
}
