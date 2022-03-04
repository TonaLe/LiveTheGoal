package app.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Order dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    /**
     * The Username.
     */
    private String username;

    /**
     * The Items.
     */
    private List<ItemDto> items;

    /**
     * The Total.
     */
    private BigDecimal total;
}
