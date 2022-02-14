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
}
