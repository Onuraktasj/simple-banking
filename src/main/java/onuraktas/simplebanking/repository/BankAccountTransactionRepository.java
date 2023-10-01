package onuraktas.simplebanking.repository;

import onuraktas.simplebanking.entity.BankAccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransactionEntity, UUID> {
    List<BankAccountTransactionEntity> findByBankAccountId(UUID id);
}
