package app.security.Service;

import app.security.DTO.BrandDto;
import app.security.DTO.ProductDto;

import java.util.List;

public interface BrandService {
    BrandDto loadBrandByName(final String name);
    List<BrandDto> getListBrand(final int limit, final int offset);
    BrandDto createBrand(final BrandDto brandDto);
    BrandDto updateBrand(final String name, final BrandDto brandDto);
}
