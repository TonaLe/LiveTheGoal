package app.security.Event.AccountEvent.AccountProducer;

import app.security.Entity.Account;

public interface AccountEventProducer {
    void sendMessage(final Account account);
}
