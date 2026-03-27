package dev.silvercapes.catalogservice.service;

import dev.silvercapes.catalogservice.config.ApplicationProperties;
import dev.silvercapes.catalogservice.dto.PagedResult;
import dev.silvercapes.catalogservice.dto.ProductResponseDTO;
import dev.silvercapes.catalogservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ApplicationProperties applicationProperties;

    public PagedResult<ProductResponseDTO> getProducts(int pageNum){
        Sort sort = Sort.by("name").ascending();
        pageNum = pageNum <=1 ? 0 : pageNum -1;
        Pageable pageable = (Pageable) PageRequest.of(pageNum, applicationProperties.pageSize(), sort);
        Page<ProductResponseDTO> productsPage = productRepository.findAll(pageable).map(product -> modelMapper.map(product, ProductResponseDTO.class));

        return PagedResult.<ProductResponseDTO>builder()
                .totalElements(productsPage.getTotalElements())
                .isFirst(productsPage.isFirst())
                .isLast(productsPage.isLast())
                .hasNext(productsPage.hasNext())
                .totalPages(productsPage.getTotalPages())
                .data(productsPage.getContent())
                .hasPrevious(productsPage.hasPrevious())
                .pageNumber(productsPage.getNumber() + 1)
                .build();
    }
}
