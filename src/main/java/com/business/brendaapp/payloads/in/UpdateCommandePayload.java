package com.business.brendaapp.payloads.in;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateCommandePayload {
    @NotBlank(message = "l'identifiant de le commande est requis")
    private String id;
    private String paiementId;
    private String productId;
    private String nomClient;
    private String addresseLivraison;
    private String numeroClient;
}
