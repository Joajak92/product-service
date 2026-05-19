package se.iths.joakim.productservice.mapper;

import org.springframework.stereotype.Component;
import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductResponseDto;
import se.iths.joakim.productservice.model.Product;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRequestDto productRequestDto) {
        if (productRequestDto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(productRequestDto.name());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setStock(productRequestDto.stock());

        return product;
    }

    @Override
    public ProductResponseDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
