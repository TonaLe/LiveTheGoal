package module.account.Event.AccountProducer;


import module.account.DTO.AccountDto;

import java.util.List;

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
     * Send all account info msg.
     *
     * @param listAccount the list account
     */
    void sendAllAccountInfoMsg(final List<AccountDto> listAccount);
}
