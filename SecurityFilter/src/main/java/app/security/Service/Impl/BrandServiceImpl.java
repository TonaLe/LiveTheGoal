package app.security.Service.Impl;

import app.security.DAO.BrandDAO;
import app.security.DTO.BrandDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.Entity.Brand;
import app.security.Entity.Product;
import app.security.Service.BrandService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final BrandDAO brandDAO;

    @Autowired
    public BrandServiceImpl(BrandDAO brandDAO) {
        this.brandDAO = brandDAO;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public BrandDto loadBrandByName(String name) {
        var brand = brandDAO.findBrandByName(name);
        return convertEntityToDto(brand);
    }

    @Override
    public List<BrandDto> getListBrand(int limit, int offset) {
        Pageable pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        List<Brand> brandList = brandDAO.getAllBrand(pageable);

        return brandList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto createBrand(BrandDto brandDto) {
        var brand = modelMapper.map(brandDto, Brand.class);
        var newBrand = brandDAO.setBrand(brand);
        if (newBrand != null){
            return convertEntityToDto(newBrand);
        }
        return null;
    }

    @Override
    public BrandDto updateBrand(String name, BrandDto brandDto) {
        var existedBrand = brandDAO.findBrandByName(name);
        var newBrand = modelMapper.map(brandDto, Brand.class);
        if (existedBrand != null){
            existedBrand.setName(newBrand.getName());
            brandDAO.setBrand(existedBrand);
            return brandDto;
        }
        return null;
    }

    private BrandDto convertEntityToDto(final Brand brand) {
        BrandDto brandDto = modelMapper.map(brand, BrandDto.class);
        return brandDto;
    }
}
