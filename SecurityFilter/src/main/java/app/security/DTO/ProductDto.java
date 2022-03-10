package app.security.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 6, message = "Product name must be above 6 characters")
    private String name;

    @NotNull(message = "Product description cannot be null")
    @Size(min = 6, message = "Product description must be above 6 characters")
    private String description;

    private String pic;

    @NotNull(message = "sku cannot be null")
    private String sku;

    @NotNull(message = "category cannot be null")
    private String category;

    @NotNull(message = "quantity cannot be null")
    @Size(min = 1, message = "quantity must be above 6 characters")
    private int quantity;

    @NotNull(message = "brandName cannot be null")
    @Size(min = 6, message = "brandName must be above 6 characters")
    private String brand;

    @NotNull(message = "price cannot be null")
    private float price;

    private Date createdDate;

    private int totalResult;

    private Boolean isDeleted;
}
