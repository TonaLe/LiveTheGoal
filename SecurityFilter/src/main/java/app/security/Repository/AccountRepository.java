package app.security.Repository;

import app.security.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

//    @Query(value = "{'username':{$regex:?0}}")
    List<Account> findAccountByUsername(String username);
}
