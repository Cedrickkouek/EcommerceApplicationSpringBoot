package com.business.brendaapp.payloads.in;

import java.util.ArrayList;
import java.util.List;

import com.business.brendaapp.enumeration.Category;
import com.business.brendaapp.exception.InvalidCategoryException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateProductPayload {
    @NotBlank(message = "Le titre est requis")
    private String title;
    @NotNull(message = "Le prix est requis")
    private Double price;
    @NotBlank(message = "Le titre est requis")
    private String description;
    @NotBlank(message = "Le titre est requis")
    private String category;

    public void validateType() throws InvalidCategoryException
    {
        try {
                Category.valueOf(this.category);
            } catch (Exception e) {
                throw new InvalidCategoryException("La valeur du Type de message entrée est invalide veuillez choisir parmis les valeurs suivantes "+displayEnumValueCategory());
            }
    }

    public List<String> displayEnumValueCategory()
    {
        List<String> listType = new ArrayList<>();
        Category[] type = Category.values() ;
        for (Category statut0 : type) {
            listType.add(statut0.toString());
        }
        return listType;
    }
}
