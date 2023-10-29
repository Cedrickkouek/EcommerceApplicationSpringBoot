package com.business.brendaapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.business.brendaapp.enumeration.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Entity
@Document
public class Product{
    @Id
    private String id;
    private String title;
    private Double price;
    private Category category;
    private String description;
    private List<String> image =  new ArrayList<>();
}