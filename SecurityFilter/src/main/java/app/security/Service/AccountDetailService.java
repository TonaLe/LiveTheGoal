package app.security.Service;

import app.security.DTO.AccountDto;

/**
 * The interface Account detail service.
 */
public interface AccountDetailService {
    /**
     * Sets account detail.
     *
     * @param account the account
     */
    void setAccountDetail(final AccountDto account);
}
