package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Service.AccountService;
import app.security.Service.ErrorService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;


@RestController
@RequestMapping("/")
public class AccountController {

    private final AccountService accountService;
    private final ErrorService errorService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccountController(final AccountService accountService,
                             final ErrorService errorService) {
        this.accountService = accountService;
        this.errorService = errorService;
    }

    @SneakyThrows
    @PostMapping("/signup")
    public Response setAccount(@Valid @RequestBody AccountDto account) {
        if (account == null) return Response.status(Response.Status.BAD_REQUEST).build();
        accountService.setAccount(account);

        final ErrorDto errorDto = errorService.getError(account.getUsername());

        if (errorDto != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GetMapping("/Account/{username}")
    public Response getUseByUsername(@PathVariable String username) {
        if (StringUtils.isBlank(username)) {
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        final AccountDto accountDto = accountService.loadAccountByUsername(username);

        if (accountDto == null) {
            Response.status(Response.Status.BAD_REQUEST).entity("Account is not existed").build();
        }
        return Response.status(Response.Status.OK).entity(accountDto).build();
    }

    @GetMapping("Account/info")
    public Response getAccountsInfo(@RequestParam("limit") int limit,
                                    @RequestParam("offset") int offset) {

        final List<AccountDto> listAccount = accountService.getListAccount(limit, offset);
        if (listAccount.isEmpty()) {
            Response.status(Response.Status.BAD_REQUEST).entity("No Account to be collected").build();
        }
        return Response.status(Response.Status.OK).entity(listAccount).build();
    }

    @PutMapping("/Account/{username}")
    public Response updateAccountInfo(@PathVariable final String username, @RequestBody final AccountDto account) {
        if (StringUtils.isBlank(username) || account == null) {
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            accountService.updateAccountInfo(username, account);
        } catch (Exception e) {
            LOG.error(String.valueOf(new IllegalArgumentException(
                    String.format("Error in updating account %s ----", username) + e)));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DeleteMapping("Account/{username}")
    public Response deleteAccountInfo(@PathVariable final String username) {
        try {
            accountService.deleteAccount(username);
        } catch (Exception e) {
            LOG.error(String.valueOf(new IllegalArgumentException(
                    String.format("Error in deleting account %s ----", username) + e)));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
