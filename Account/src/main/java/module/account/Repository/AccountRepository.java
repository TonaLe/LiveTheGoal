package module.account.Repository;

import module.account.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    //    @Query(value = "{'username':{$regex:?0}}")
    Account findAccountByUsername(String username);
}
