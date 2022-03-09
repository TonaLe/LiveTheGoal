package app.security.DTO;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private List<ProductDto> listProduct;
    private int totalPage;
}
