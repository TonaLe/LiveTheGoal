package app.security.Controller;

import app.security.DTO.AccountDto;
import app.security.Entity.Account;
import app.security.Event.AccountEvent.AccountProducer.AccountEventProducer;
import app.security.Repository.AccountRepository;
import app.security.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/Test")
public class AccountController {

    private final AccountService accountService;
    private final AccountEventProducer accountEventProducer;

    @Autowired
    public AccountController(final AccountService accountService,
                             final AccountEventProducer accountEventProducer) {
        this.accountService = accountService;
        this.accountEventProducer = accountEventProducer;
    }

    @PostMapping("/signup")
    public void setAccount(@Valid @RequestBody AccountDto account) {
        if (account == null) return;

        accountEventProducer.sendCreationMessage(account);
    }

    @GetMapping("/User/{username}")
    public ResponseEntity<Void> getUseByUsername(@PathVariable String username) {
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity(account, HttpStatus.OK);
    }
}
