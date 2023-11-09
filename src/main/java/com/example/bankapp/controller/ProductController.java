package com.example.bankapp.controller;

import com.example.bankapp.dto.NewManagerForProductRequestDto;
import com.example.bankapp.dto.ProductCreationRequestDto;
import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.service.util.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ProductDto getProductById(@PathVariable("id") String id) {
        return productService.getProductById(UUID.fromString(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ProductDto createProduct(@Valid @RequestBody ProductCreationRequestDto creationRequestDto){
        return productService.createProduct(creationRequestDto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ProductDto changeProductManager(@PathVariable("id") String id, @Valid @RequestBody NewManagerForProductRequestDto newManagerForProductRequestDto){
        return productService.changeProductManager(UUID.fromString(id), newManagerForProductRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ProductDto deleteProduct(@PathVariable("id") String id){
        return productService.deleteProduct(UUID.fromString(id));
    }

    @GetMapping("/show-all-active-products")
    @PreAuthorize("hasRole('MANAGER')")
    public Set<ProductDto> getAllActiveProducts() {
        return productService.getAllActiveProducts();
    }
}

