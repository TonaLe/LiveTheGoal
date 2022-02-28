package app.security.DTO;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingDto {
    private String username;
    private Long total;
}
