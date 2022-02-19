package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import app.security.Event.AccountEvent.AccountProducer.AccountEventProducer;
import app.security.Service.AccountService;
import app.security.Service.ErrorService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/")
public class AccountController {

    private final AccountService accountService;
    private final AccountEventProducer accountEventProducer;
    private final ErrorService errorService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final long ONE_SECOND_SLEEP = 1000;
    private final long FIVE_SECOND_SLEEP = 5000;

    @Autowired
    public AccountController(final AccountService accountService,
                             final AccountEventProducer accountEventProducer,
                             final ErrorService errorService) {
        this.accountService = accountService;
        this.accountEventProducer = accountEventProducer;
        this.errorService = errorService;
    }

    @SneakyThrows
    @PostMapping("/signup")
    public Response setAccount(@Valid @RequestBody AccountDto account) {
        if (account == null) return Response.status(Response.Status.BAD_REQUEST).build();
        accountEventProducer.sendCreationMessage(account);

        sleepThreadInSecond(500);
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
        accountEventProducer.sendRequestForAccountInfo(username);

        sleepThreadInSecond(500);

        final AccountDto accountDto = accountService.loadAccountByUsername(username);

        if (accountDto == null) {
            Response.status(Response.Status.BAD_REQUEST).entity("Account is not existed").build();
        }
        return Response.status(Response.Status.OK).entity(accountDto).build();
    }

    @GetMapping("Account/info")
    public Response getAccountsInfo(@RequestParam("limit") int limit,
                                    @RequestParam("offset") int offset) {
        accountEventProducer.sendRequestForAccountsInfo(limit, offset);

        sleepThreadInSecond(1000);
    }

    private void sleepThreadInSecond(final long milisecond) {
        try {
            LOG.info("Thread sleep in 1 second");
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            LOG.error(String.valueOf(
                    new IllegalArgumentException("Exception in putting thread to sleeping state: " + e)));
        }
    }


}
