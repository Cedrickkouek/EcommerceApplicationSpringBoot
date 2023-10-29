package com.business.brendaapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.business.brendaapp.entities.Commande;

public interface CommandeRepo extends MongoRepository<Commande, String>{
    
}
