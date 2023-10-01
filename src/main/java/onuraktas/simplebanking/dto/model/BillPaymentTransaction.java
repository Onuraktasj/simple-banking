package onuraktas.simplebanking.dto.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BillPaymentTransaction extends Transaction {

    private String payee;
}
