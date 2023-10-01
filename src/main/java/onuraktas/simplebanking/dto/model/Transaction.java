package onuraktas.simplebanking.dto.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@SuperBuilder
public class Transaction {


    private LocalDateTime date;
    private BigDecimal amount;
    private String type;
    private UUID approvalCode;

}
