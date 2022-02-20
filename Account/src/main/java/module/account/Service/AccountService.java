package module.account.Service;

import module.account.DTO.AccountDto;
import module.account.DTO.ErrorDto;
import module.account.Entity.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
     * @param id       the id
     */
    void sendErrorMsg(final ErrorDto errorDto, final int id);

    /**
     * Gets list account info.
     *
     * @param pageable the pageable
     * @return the list account info
     */
    void sendListAccountInfoMsg(final Pageable pageable);
}
