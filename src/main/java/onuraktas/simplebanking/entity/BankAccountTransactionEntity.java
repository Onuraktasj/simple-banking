package onuraktas.simplebanking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "bank_account_transaction")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BankAccountTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Basic
    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Basic
    @Column(name = "transaction_id")
    private UUID transactionId;
}
