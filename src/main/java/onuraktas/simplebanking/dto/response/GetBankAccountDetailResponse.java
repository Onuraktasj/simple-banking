package onuraktas.simplebanking.dto.response;

import lombok.*;
import onuraktas.simplebanking.dto.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
public class GetBankAccountDetailResponse {

    private String accountNumber;
    private String owner;
    private BigDecimal balance;
    private String createdDate;
    private List<Transaction> transactions;
}
