package com.business.brendaapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business.brendaapp.config.GlobalConfig;
import com.business.brendaapp.entities.Product;
import com.business.brendaapp.enumeration.Category;
import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.InvalidCategoryException;
import com.business.brendaapp.exception.ProductNotFoundException;
import com.business.brendaapp.payloads.in.CreateProductPayload;
import com.business.brendaapp.payloads.in.UpdateProductPayload;
import com.business.brendaapp.payloads.out.ResponseFileService;
import com.business.brendaapp.repositories.ProductRepo;
import com.business.brendaapp.utils.Serializer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private CloudStorageServices s3Service;
    private List<String> images = new ArrayList<>();
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
        images.add(response.getMessage() );
        product.setImage(images);
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Product findProductByid(String idProduct) throws ProductNotFoundException
    {
        boolean checkProduct =  productRepository.existsById(idProduct);
        if(checkProduct)
        {
            return productRepository.findById(idProduct).get();
        }
        else
        {
            throw new ProductNotFoundException("Le produit don't l'id est "+idProduct+" n'existe pas!");
        }
    }

    public Product AddImageToProduct(String idProduct, MultipartFile file) throws IOException, ConvertMultipart2FileException, ProductNotFoundException
    {
        if(productRepository.existsById(idProduct))
        {
            Product product = productRepository.findById(idProduct).get();
            ResponseFileService response = s3Service.saveFile(file, GlobalConfig.getPathUrl(product.getCategory()));

            List<String> image = new ArrayList<>();
            image = product.getImage();
            image.add(response.getMessage() );
            product.setImage(image);
            productRepository.save(product);
            return product;
        }
        else
        {
            throw new ProductNotFoundException("Le produit don't l'id est "+idProduct+" n'existe pas!");
        }
    }

    public Product editProductByid(UpdateProductPayload updateProductPayload) throws ProductNotFoundException, IOException, ConvertMultipart2FileException
    {
        boolean checkProduct =  productRepository.existsById(updateProductPayload.getIdProduit());
        if(checkProduct)
        {
            Product product = productRepository.findById(updateProductPayload.getIdProduit()).get();
            if(updateProductPayload.getCategory().isEmpty())
            {
                if(updateProductPayload.getDescription().isEmpty())
                {
                    if(updateProductPayload.getPrice() == 0)
                    {
                        if(updateProductPayload.getTitle().isEmpty())
                        {
                    
                        }
                        else
                        {
                            product.setTitle(updateProductPayload.getTitle());
                        }
                    }
                    else
                    {
                        product.setPrice(updateProductPayload.getPrice());
                    }
                }
                else
                {
                    product.setDescription(updateProductPayload.getDescription());
                }
            }
            else
            {
                product.setCategory(Category.valueOf(updateProductPayload.getCategory()));
            }
            productRepository.save(product);
            return product;
        }
        else
        {
            throw new ProductNotFoundException("Le produit don't l'id est "+updateProductPayload.getIdProduit()+" n'existe pas!");
        }
    }
}
