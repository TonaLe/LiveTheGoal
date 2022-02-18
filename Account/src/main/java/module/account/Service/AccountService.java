package module.account.Service;

import module.account.DTO.AccountDto;
import module.account.DTO.ErrorDto;
import module.account.Entity.Account;

/**
 * The interface Account service.
 */
public interface AccountService  {

    /**
     * Sets account.
     *
     * @param account the account
     */
    void setAccount(AccountDto account);

    /**
     * Gets account by username.
     *
     * @param username the username
     * @return the account by username
     */
    Account getAccountByUsername(String username);

    /**
     * Validate account dto boolean.
     *
     * @param accountDto the account dto
     * @return the error dto
     */
    ErrorDto validateAccountDto(AccountDto accountDto);

    /**
     * Send error msg.
     *
     * @param errorDto the error dto
     */
    void sendErrorMsg(final ErrorDto errorDto, final int id);
}
