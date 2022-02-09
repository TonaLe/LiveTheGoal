package app.security.Event.AccountEvent;

import app.security.Entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountEventProducer {
    void sendMessage(final Account account);
}
