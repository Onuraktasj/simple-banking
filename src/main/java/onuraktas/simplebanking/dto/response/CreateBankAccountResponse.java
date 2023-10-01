package onuraktas.simplebanking.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@SuperBuilder
public class CreateBankAccountResponse {
    private String status;
    private UUID approvalCode;
}
