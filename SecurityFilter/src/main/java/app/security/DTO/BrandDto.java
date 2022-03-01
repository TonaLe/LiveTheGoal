package app.security.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandDto {
    @NotNull(message = "Brand name cannot be null")
    @Size(min = 6, message = "Brand name must be above 6 characters")
    private String name;
}
