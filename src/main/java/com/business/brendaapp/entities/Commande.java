package com.business.brendaapp.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.business.brendaapp.enumeration.StatusCommande;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Document
public class Commande {
    @Id
    private String id;
    private String paiementId;
    private String productId; //
    private LocalDate dateCreation;
    private LocalDate dateModification;
    private LocalDate dateLivraison;
    private LocalTime heureLivraison;
    private String nomClient; //
    private String addresseLivraison;//
    private String numeroClient;//
    private StatusCommande statusCommande;
}
