package app.security.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 6, message = "Product name must be above 6 characters")
    private String name;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String description;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String sku;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String categoryid;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String inventoryid;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String brandid;

    @NotNull(message = "description cannot be null")
    @Size(min = 6, message = "description must be above 6 characters")
    private String price;
}
