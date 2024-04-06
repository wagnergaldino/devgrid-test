package dev.codescreen.marketplace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.marketplace.controller.exception.ResourceNotFoundException;
import dev.codescreen.marketplace.dto.ProductDto;
import dev.codescreen.marketplace.dto.StoreDto;
import dev.codescreen.marketplace.service.ProductService;

@Controller
@RestController
public class ProductController {
	
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id)
                           .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping(value = "/store/{storeId}/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createStore(@PathVariable Long storeId, @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto, storeId);
    }

    @GetMapping(value = "/product/find/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findProductsByName(@PathVariable String name) {
        List<ProductDto> products = productService.findProductsByName(name);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return products;
    }
    
    @GetMapping(value = "/store/{storeId}/product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findProductsByStoreId(@PathVariable Long storeId) {
        List<ProductDto> products = productService.findProductsByStoreId(storeId);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return products;
    }
	
}
