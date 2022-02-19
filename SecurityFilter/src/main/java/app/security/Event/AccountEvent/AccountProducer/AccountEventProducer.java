package app.security.Event.AccountEvent.AccountProducer;

import app.security.DTO.AccountDto;
import app.security.Entity.Account;

/**
 * The interface Account event producer.
 */
public interface AccountEventProducer {

    /**
     * Send creation message.
     *
     * @param account the account
     */
    void sendCreationMessage(final AccountDto account);

    /**
     * Send authorise message.
     *
     * @param account the account
     */
    void sendAuthoriseMessage(final AccountDto account);

    /**
     * Send request for account info.
     *
     * @param username the username
     */
    void sendRequestForAccountInfo(final String username);

    /**
     * Send request for accounts info.
     *
     * @param limit  the limit
     * @param offset the offset
     */
    void sendRequestForAccountsInfo(final int limit, final int offset);
}
