package dev.silvercapes.catalogservice.controllers;
import dev.silvercapes.catalogservice.dto.PagedResult;
import dev.silvercapes.catalogservice.dto.ProductResponseDTO;
import dev.silvercapes.catalogservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get the list of products")
    @GetMapping("")
    ResponseEntity<PagedResult<ProductResponseDTO>> getProducts(@RequestParam(name ="page", defaultValue = "1")int pageNum){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(pageNum));
    }

    @GetMapping("/{code}")
    ProductResponseDTO getProductByCode(@PathVariable String code){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByCode(code)).getBody();
    }
}
