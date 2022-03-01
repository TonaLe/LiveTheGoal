package app.security.DAO;

import app.security.Entity.Brand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandDAO {
    Brand setBrand(final Brand brand);
    Brand findBrandByName(final String name);
    List<Brand> getAllBrand(final Pageable pageable);
}
