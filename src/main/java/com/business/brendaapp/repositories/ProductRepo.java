package com.business.brendaapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.business.brendaapp.entities.Product;

public interface ProductRepo extends MongoRepository<Product, String>{
    
}
