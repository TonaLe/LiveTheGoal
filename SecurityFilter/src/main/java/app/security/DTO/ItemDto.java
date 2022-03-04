package app.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Item dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    /**
     * The Sku.
     */
    private String sku;
    /**
     * The Quantity.
     */
    private Integer quantity;

}
