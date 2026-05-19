package se.iths.joakim.productservice.mapper;

import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductResponseDto;
import se.iths.joakim.productservice.model.Product;

public interface ProductMapper {

    Product toEntity(ProductRequestDto productRequestDto);

    ProductResponseDto toDto(Product product);
}
