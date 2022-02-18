package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Entity.Account;
import app.security.Event.AccountEvent.AccountProducer.AccountEventProducer;
import app.security.Service.AccountService;
import app.security.Service.ErrorService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
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

        LOG.info("Stop Thread in 0.5 seconds");
        Thread.sleep(500);
        ErrorDto errorDto = errorService.getError(Integer.toString(account.getId()));

        if (errorDto != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GetMapping("/User/{username}")
    public ResponseEntity<Void> getUseByUsername(@PathVariable String username) {
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity(account, HttpStatus.OK);
    }

//    @PostMapping("")
}
