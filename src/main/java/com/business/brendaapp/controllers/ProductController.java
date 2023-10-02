package com.business.brendaapp.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.business.brendaapp.entities.Product;
import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.InvalidCategoryException;
import com.business.brendaapp.payloads.in.CreateProductPayload;
import com.business.brendaapp.services.ProductService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;

@RestController
@OpenAPIDefinition
@AllArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private ProductService productService;

    @PostMapping(value = "/add_product", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> AddProduct(@Valid @RequestParam("createProductPayload") String createProductPayload, @RequestParam(value = "file") MultipartFile file) throws InvalidCategoryException, IOException, ConvertMultipart2FileException {
    var response = productService.createProduct(createProductPayload, file);
    return new ResponseEntity<Product>(response, HttpStatus.CREATED);
}



    
}
