package app.security.Repository;

import app.security.Entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findBrandByName(final String name);
    Page<Brand> findAll(final Pageable pageable);
}
