package net.atos.etax.repository;

import net.atos.etax.domain.BankAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BankAccount entity.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {

    @Query("select bankAccount from BankAccount bankAccount where bankAccount.user.login = ?#{principal.username}")
    List<BankAccount> findByUserIsCurrentUser();

}
