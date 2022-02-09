package app.security.Controller;

import app.security.Entity.Account;
import app.security.Repository.AccountRepository;
import app.security.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Test")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/signup")
    public void setAccount(@RequestBody Account account){
        accountService.setAccount(account);
    }

    @GetMapping("/User/{username}")
    public ResponseEntity<Void> getUseByUsername(@PathVariable String username){
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity(account,HttpStatus.OK);
    }
}
