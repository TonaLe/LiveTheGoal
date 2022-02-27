package app.security.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

/**
 * The type Account dto.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

    /**
     * Category name.
     */
    @NotNull(message = "Category name cannot be null")
    @Size(min = 6, message = "Category name must be above 6 characters")
    private String name;
}
