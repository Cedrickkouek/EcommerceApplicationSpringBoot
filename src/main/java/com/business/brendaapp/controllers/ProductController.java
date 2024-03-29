package com.business.brendaapp.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.business.brendaapp.entities.Product;
import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.InvalidCategoryException;
import com.business.brendaapp.exception.ProductNotFoundException;
import com.business.brendaapp.payloads.in.UpdateProductPayload;
import com.business.brendaapp.services.ProductService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@OpenAPIDefinition
@AllArgsConstructor
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*")
@Slf4j
public class ProductController {

    private ProductService productService;

    @PostMapping(value = "/add_product", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> AddProduct(@Valid @RequestParam("createProductPayload") String createProductPayload, @RequestParam(value = "file") MultipartFile file) throws InvalidCategoryException, IOException, ConvertMultipart2FileException {
        var response = productService.createProduct(createProductPayload, file);
        return new ResponseEntity<Product>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getProduct/{idProduit}")
    public ResponseEntity<Product> getProduct(@PathVariable String idProduit) throws ProductNotFoundException
    {
        Product product = productService.findProductByid(idProduit);
        return new ResponseEntity<Product>(product, HttpStatus.FOUND);
    }
    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        var response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
    @PutMapping(value = "/editProduct", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> editProduct(@Valid @RequestBody UpdateProductPayload updateProductPayload) throws ProductNotFoundException, IOException, ConvertMultipart2FileException
    {
        Product product = productService.editProductByid(updateProductPayload);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/AddImageToProduct/{idProduct}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addImageToProduct(@Valid @RequestParam("createProductPayload") String createProductPayload, @RequestParam(value = "file") MultipartFile file) throws IOException, ConvertMultipart2FileException, ProductNotFoundException
    {
        Product product = productService.AddImageToProduct(createProductPayload, file);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }
}
