package com.business.brendaapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business.brendaapp.config.GlobalConfig;
import com.business.brendaapp.entities.Product;
import com.business.brendaapp.enumeration.Category;
import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.InvalidCategoryException;
import com.business.brendaapp.payloads.in.CreateProductPayload;
import com.business.brendaapp.payloads.out.ResponseFileService;
import com.business.brendaapp.repositories.ProductRepo;
import com.business.brendaapp.utils.Serializer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private CloudStorageServices s3Service;
    private final ProductRepo productRepository;

    public Product createProduct(String createProductPayload, MultipartFile file) throws InvalidCategoryException, IOException, ConvertMultipart2FileException
    {
        CreateProductPayload createProductPayload2 =  Serializer.serializeProduct(createProductPayload);
        createProductPayload2.validateType();
        Product product = Product.builder()
        .category(Category.valueOf(createProductPayload2.getCategory()))
        .description(createProductPayload2.getDescription())
        .price(createProductPayload2.getPrice())
        .title(createProductPayload2.getTitle())
        .build();
        ResponseFileService response = s3Service.saveFile(file, GlobalConfig.getPathUrl(Category.valueOf(createProductPayload2.getCategory())));
        product.setImage(response.getMessage());
        productRepository.save(product);
        return product;
    }
}
