package com.business.brendaapp.payloads.in;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateCommandePayload {

    @NotBlank(message = "Le nom du client est requis")
    private String nomClient;
    @NotBlank(message = "Le numero du client est requis")
    private String numeroClient;
    @NotBlank(message = "L'addresse du client est requis")
    private String addresseClient;
    @NotBlank(message = "L'identifiant du produit est requis")
    private String produitId;

}
