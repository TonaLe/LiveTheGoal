package module.account.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Error dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {

    /**
     * The Id.
     */
    private String id;

    /**
     * The Message.
     */
    private String message;
}
