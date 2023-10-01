package onuraktas.simplebanking.dto.response;

import lombok.*;
import onuraktas.simplebanking.enums.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Builder
public class ErrorResponse {

    @Builder.Default
    private String status = Status.NOK.getStatus();
    private String message;
}
