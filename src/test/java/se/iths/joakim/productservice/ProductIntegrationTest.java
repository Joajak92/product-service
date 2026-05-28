package se.iths.joakim.productservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductStockRequest;
import se.iths.joakim.productservice.model.Product;
import se.iths.joakim.productservice.repository.ProductRepository;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDto request = new ProductRequestDto("Samsung TV", "Samsung smart-tv", 8999.0, 8);

        mockMvc.perform(post("/products")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Samsung TV"))
                .andExpect(jsonPath("$.description").value("Samsung smart-tv"))
                .andExpect(jsonPath("$.price").value(8999))
                .andExpect(jsonPath("$.stock").value(8));
    }

    @Test
    void shouldListProducts() throws Exception {
        Product product1 = new Product();
        product1.setName("Samsung TV");
        product1.setDescription("Samsung smart-tv");
        product1.setPrice(8999.0);
        product1.setStock(10);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("iPhone 17 pro");
        product2.setDescription("Senaste iPhone pro modellen");
        product2.setPrice(20000.0);
        product2.setStock(5);
        productRepository.save(product2);

        mockMvc.perform(get("/products")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Samsung TV"))
                .andExpect(jsonPath("$[1].name").value("iPhone 17 pro"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        Product product = new Product();
        product.setName("Samsung TV");
        product.setDescription("Samsung smart-tv");
        product.setPrice(8999.0);
        product.setStock(10);
        productRepository.save(product);

        mockMvc.perform(get("/products/{id}", product.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung TV"));
    }


    @Test
    void shouldDeleteProduct() throws Exception {
        Product product = new Product();
        product.setName("Samsung TV");
        product.setDescription("Samsung smart-tv");
        product.setPrice(8999.0);
        product.setStock(10);
        productRepository.save(product);

        mockMvc.perform(delete("/products/{id}", product.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isNoContent());

        assert productRepository.findById(product.getId()).isEmpty();
    }

    @Test
    void shouldDecreaseStock() throws Exception {
        Product product = new Product();
        product.setName("Samsung TV");
        product.setDescription("Samsung smart-tv");
        product.setPrice(8999.0);
        product.setStock(10);
        productRepository.save(product);

        ProductStockRequest requestedItem = new ProductStockRequest(product.getId(), 5);

        mockMvc.perform(post("/products/stock/decrease")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(requestedItem))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stock").value(5));
    }

    @Test
    void shouldThrowWhenStockNotEnough() throws Exception {
        Product product = new Product();
        product.setName("Samsung TV");
        product.setDescription("Samsung smart-tv");
        product.setPrice(8999.0);
        product.setStock(10);
        productRepository.save(product);

        ProductStockRequest requestedItem = new ProductStockRequest(product.getId(), 15);

        mockMvc.perform(post("/products/stock/decrease")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(requestedItem))))
                .andExpect(status().isBadRequest());
    }
}
