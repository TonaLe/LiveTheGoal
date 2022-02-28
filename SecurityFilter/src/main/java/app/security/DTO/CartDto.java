package app.security.DTO;

import lombok.*;

/**
 * The type Cart dto.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDto {

    /**
     * The Username.
     */
    private String username;

    /**
     * The Total.
     */
    private Long total;

    /**
     * The Sku.
     */
    private String sku;

    /**
     * The Quantity.
     */
    private Integer quantity;
}
