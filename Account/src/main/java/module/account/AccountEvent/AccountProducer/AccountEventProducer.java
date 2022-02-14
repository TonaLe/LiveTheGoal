package module.account.AccountEvent.AccountProducer;


import module.account.DTO.AccountDto;

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
