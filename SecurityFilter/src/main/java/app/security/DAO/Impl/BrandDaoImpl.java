package app.security.DAO.Impl;

import app.security.DAO.BrandDAO;
import app.security.Entity.Brand;
import app.security.Repository.BrandRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandDaoImpl implements BrandDAO {
    private BrandRepository brandRepository;

    public BrandDaoImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand setBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrandByName(String name) {
        return brandRepository.findBrandByName(name);
    }

    @Override
    public List<Brand> getAllBrand(Pageable pageable) {
        return brandRepository.findAll(pageable).getContent();
    }
}
